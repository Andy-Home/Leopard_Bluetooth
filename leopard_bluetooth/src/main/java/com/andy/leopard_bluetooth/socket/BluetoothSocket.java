package com.andy.leopard_bluetooth.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 蓝牙通讯,类似Scoket通讯
 * <p>
 * Created by andy on 17-10-16.
 */

public class BluetoothSocket {
    private final String TAG = getClass().getSimpleName();

    public BluetoothSocket() {

    }

    private android.bluetooth.BluetoothSocket mClientSocket;

    private void setSocket(android.bluetooth.BluetoothSocket socket) {
        mClientSocket = socket;
        try {
            sendStream = socket.getOutputStream();
            recieveStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Message> sendList = new ArrayList<>();

    private OutputStream sendStream;

    private void sendThread() {

    }

    private InputStream recieveStream;

    private void recieveThread() {

    }
}
