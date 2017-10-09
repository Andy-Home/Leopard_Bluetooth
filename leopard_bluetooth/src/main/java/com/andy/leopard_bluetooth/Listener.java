package com.andy.leopard_bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * 回调接口类
 * <p>
 * Created by andy on 17-9-22.
 */

public class Listener {

    public interface BaseListener {
        void success(int code, String result);

        void failure(int code, String msg);
    }

    public interface FindDeviceListener {
        void success(BluetoothDevice device);
    }
}
