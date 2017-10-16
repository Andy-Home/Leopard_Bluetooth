package com.andy.leopard_bluetooth;

import android.bluetooth.BluetoothServerSocket;

import com.andy.leopard_bluetooth.subscribe.Bluetooth;

/**
 * 蓝牙服务端
 * <p>
 * Created by andy on 17-10-11.
 */

public class BluetoothServer extends Bluetooth {

    private BluetoothServerSocket socket;
    @Override
    public void update(Object obj, int code) {
        super.update(obj, code);
    }
}
