package com.andy.leopard_bluetooth;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.andy.leopard_bluetooth.subscribe.Bluetooth;
import com.andy.leopard_bluetooth.subscribe.BluetoothReceiver;

import java.io.IOException;
import java.util.UUID;

/**
 * 蓝牙服务端
 * <p>
 * Created by andy on 17-10-11.
 */

public class BluetoothServer extends Bluetooth {
    private final String TAG = getClass().getSimpleName();
    private UUID mUUID;

    private BluetoothServerSocket mServerSocket;
    private BluetoothSocket socket;

    public void init(Context context, UUID uuid) {
        super.setContext(context);
        mUUID = uuid;
    }

    private void connect() {
        try {
            mServerSocket = adapter.listenUsingRfcommWithServiceRecord("andy-server", mUUID);

            socket = mServerSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object obj, int code) {
        super.update(obj, code);
        switch (code) {
            case BluetoothReceiver.DEVICE_CONNECT:
                Log.d(TAG, "蓝牙通讯建立连接");
                break;
            case BluetoothReceiver.DEVICE_DISCONNECT:
                Log.d(TAG, "蓝牙通讯连接断开");
                break;
        }
    }
}
