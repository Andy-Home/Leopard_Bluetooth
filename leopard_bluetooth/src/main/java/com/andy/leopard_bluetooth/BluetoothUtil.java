package com.andy.leopard_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * 蓝牙工具类
 *
 * Created by andy on 17-9-22.
 */

public class BluetoothUtil {
    private final String TAG = getClass().getSimpleName();
    private UUID mUUID;

    private static BluetoothUtil mBluetoothUtil = null;
    private BluetoothAdapter adapter;
    private Context mContext;
    private BluetoothReceiver mReceiver;

    private BluetoothUtil() {
    }

    public static BluetoothUtil getInstance() {
        if (mBluetoothUtil == null) {
            mBluetoothUtil = new BluetoothUtil();
        }
        return mBluetoothUtil;
    }

    public void init(Context context, UUID uuid) {
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            Log.i(TAG, "系统不支持蓝牙");
        }
        this.mContext = context;
        this.mUUID = uuid;
        mReceiver = new BluetoothReceiver();
    }

    public boolean isSupportBluetooth() {
        return adapter != null;
    }

    public boolean open() {
        if (adapter.enable()) {
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            mContext.registerReceiver(mReceiver, filter);
        }
        return adapter.enable();
    }

    public boolean close() {
        mContext.unregisterReceiver(mReceiver);
        return adapter.disable();
    }

    private BluetoothSocket socket;

    /**
     * 根据地址连接对应蓝牙设备
     *
     * @param address  蓝牙地址{@link BluetoothDevice#getAddress()}
     * @param listener 监听回调接口
     * @param code     作为返回数据时的请求标识
     */
    public void connectDevice(String address, final Listener.BaseListener listener, final int code) {
        //通过
        mReceiver.getBluetoothDevice(address, new FindDeviceListener() {
            @Override
            public void success(BluetoothDevice device) {
                try {
                    socket = device.createRfcommSocketToServiceRecord(mUUID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //回调接口
    public interface FindDeviceListener {
        void success(BluetoothDevice device);
    }
}
