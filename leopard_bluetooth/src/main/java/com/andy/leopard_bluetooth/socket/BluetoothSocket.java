package com.andy.leopard_bluetooth.socket;

import com.andy.leopard_bluetooth.socket.message.FileMessage;
import com.andy.leopard_bluetooth.socket.message.Message;
import com.andy.leopard_bluetooth.socket.message.StringMessage;
import com.andy.leopard_bluetooth.socket.queue.MessageQueue;

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


    private void sendThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isClose) {

                    while (mQueue.size() > 0) {
                        Message msg = mQueue.out();
                        if (msg instanceof FileMessage) {
                            try {
                                FileInputStream from = new FileInputStream(((FileMessage) msg).getFile());
                                byte[] content = null;
                                int len;
                                while ((len = from.read(content)) != -1) {
                                    sendStream.write(content, 0, len);
                                }
                                sendStream.flush();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (msg instanceof StringMessage) {
                            try {
                                sendStream.write(((StringMessage) msg).getMessage().getBytes("UTF-8"));
                                sendStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }

    private void recieve() {

    }

    private void close() {
        try {
            sendStream.close();
            recieveStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
