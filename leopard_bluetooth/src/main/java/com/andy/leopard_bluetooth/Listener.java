package com.andy.leopard_bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * 回调接口类
 * <p>
 * Created by andy on 17-9-22.
 */

public class Listener {

    public interface baseListener {
        void success(int code, String result);

        void failure(int code, String msg);
    }

    public interface findDeviceListener {
        void success(BluetoothDevice device);
    }
}
