package com.andy.leopard_bluetooth.subscribe;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

/**
 * 蓝牙类
 * <p>
 * Created by Administrator on 2017/10/15.
 */

public abstract class Bluetooth implements Observer {
    private final String TAG = getClass().getSimpleName();
    private BluetoothAdapter adapter;
    private Context mContext;
    private BluetoothReceiver mReceiver;

    public Bluetooth() {
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            Log.i(TAG, "系统不支持蓝牙");
        }
        mReceiver = new BluetoothReceiver();
        mReceiver.attach(this);
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    /**
     * 打开蓝牙，并注册广播接收信号
     */
    public boolean open() {
        boolean flag = true;
        if (!adapter.isEnabled()) {
            flag = adapter.enable();
        } else {
            startDiscovery();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);//搜索发现设备
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//状态改变
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);//行动扫描模式改变了
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//动作状态发生了变化
        mContext.registerReceiver(mReceiver, filter);
        return flag;
    }

    /**
     * 关闭蓝牙，释放注册
     */
    public boolean close() {
        mContext.unregisterReceiver(mReceiver);
        mReceiver.detach(this);
        if (adapter.isEnabled()) {
            return adapter.disable();
        }
        return true;
    }

    /**
     * 系统是否支持蓝牙
     */
    public boolean isSupportBluetooth() {
        return adapter != null;
    }

    /**
     * 开始搜索可见蓝牙设备
     */
    public void startDiscovery() {
        Log.d(TAG, "开始搜索");
        adapter.startDiscovery();
    }

    /**
     * 停止搜索可见蓝牙设备
     */
    public void cancelDiscovery() {
        adapter.cancelDiscovery();
    }


    @Override
    public void update(Object obj, int code) {
        switch (code) {
            case BluetoothReceiver.STATE_ON:
                startDiscovery();
                break;
        }
    }
}
