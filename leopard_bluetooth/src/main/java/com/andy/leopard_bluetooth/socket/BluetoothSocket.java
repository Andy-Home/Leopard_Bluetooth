package com.andy.leopard_bluetooth.socket;

import android.util.Log;

import com.andy.leopard_bluetooth.socket.message.FileMessage;
import com.andy.leopard_bluetooth.socket.message.Message;
import com.andy.leopard_bluetooth.socket.message.StringMessage;
import com.andy.leopard_bluetooth.socket.queue.MessageQueue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 蓝牙通讯,类似Scoket通讯
 * <p>
 * Created by andy on 17-10-16.
 */

public class BluetoothSocket {
    private final String TAG = getClass().getSimpleName();
    private android.bluetooth.BluetoothSocket mSocket;

    /**
     * 数据发送流
     */
    private OutputStream sendStream;

    /**
     * 数据接收流
     */
    private InputStream recieveStream;

    private boolean isClose = false;

    public BluetoothSocket(android.bluetooth.BluetoothSocket socket) {
        mSocket = socket;
        try {
            sendStream = socket.getOutputStream();
            recieveStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MessageQueue mQueue = new MessageQueue();

    public void send(Message msg) {
        mQueue.in(msg);
    }

    byte[] suffix = "&*&*&*&*&*&*&*&*&*&*&".getBytes();
    private Thread sendThread;

    private void sendThread() {
        sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isClose) {

                    while (mQueue.size() > 0) {
                        Message msg = mQueue.out();
                        if (msg instanceof FileMessage) {
                            try {
                                File file = ((FileMessage) msg).getFile();
                                String prefix = "message:file;fileSize=" + file.length() + ";fileName=" + file.getName() + ";";
                                sendStream.write(prefix.getBytes());
                                FileInputStream from = new FileInputStream(file);
                                byte[] content = null;
                                int len;
                                while ((len = from.read(content)) != -1 && !isClose) {
                                    sendStream.write(content, 0, len);
                                }
                                sendStream.write(suffix);
                                sendStream.flush();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (msg instanceof StringMessage) {
                            try {
                                String prefix = "message:string;id=" + msg.id + ";";
                                sendStream.write(prefix.getBytes());
                                sendStream.write(((StringMessage) msg).getMessage().getBytes("UTF-8"));
                                sendStream.write(suffix);
                                sendStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        sendThread.start();
    }

    private Thread receiveThread;

    private void receive() {
        receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isClose) {

                    try {
                        byte[] content = null;
                        int len;
                        while ((len = recieveStream.read(content)) != -1 && !isClose) {
                            String s = new String(content);
                            Log.d(TAG, "接收信息：" + s);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void close() {
        isClose = true;
        try {
            sendStream.close();
            recieveStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
