package com.andy.leopard_bluetooth.subscribe;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 与蓝牙操作相关的广播接收器
 * <p>
 * Created by andy on 17-9-22.
 */

public class BluetoothReceiver extends BroadcastReceiver implements Subject {
    private final String TAG = getClass().getSimpleName();
    private List<BluetoothDevice> deviceList = new ArrayList<>();

    private List<Observer> observerList = new ArrayList<>();

    /**
     * 查看底层代码，因为 BluetoothDevice 中的 ACTION_DISAPPEARED 隐藏的原因，自定义该常量
     */
    private static final String ACTION_DISAPPEARED = "android.bluetooth.device.action.DISAPPEARED";

    public static final int DEVICE_LIST_UPDATE = 1;     //可见设备更新
    public static final int BOND_BONDED = 2;             //设备配对成功
    public static final int BOND_BONDING = 3;            //设备正在配对
    public static final int BOND_NONE = 4;               //设备未配对
    public static final int STATE_ON = 5;               //蓝牙打开成功
    public static final int STATE_OFF = 6;              //蓝牙关闭成功


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(BluetoothDevice.ACTION_FOUND)) {  //找到蓝牙设备
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Log.d(TAG, "找到蓝牙设备" + device.getName());
            if (!deviceList.contains(device)) {
                deviceList.add(device);
                notify(deviceList, DEVICE_LIST_UPDATE);
            }
        } else if (action.equals(ACTION_DISAPPEARED)) {     //可见蓝牙设备消失
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (deviceList.contains(device)) {
                deviceList.remove(device);
                notify(deviceList, DEVICE_LIST_UPDATE);
            }
        } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {  //蓝牙配对状态改变
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String deviceName = device.getName();
            switch (device.getBondState()) {
                case BluetoothDevice.BOND_BONDED:
                    Log.i(TAG, deviceName + "设备配对成功");
                    notify(device, BOND_BONDED);
                    break;
                case BluetoothDevice.BOND_BONDING:
                    Log.i(TAG, deviceName + "正在配对");
                    notify(device, BOND_BONDING);
                    break;
                case BluetoothDevice.BOND_NONE:
                    Log.i(TAG, deviceName + "设备未配对");
                    notify(device, BOND_NONE);
                    break;
            }
        } else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_ON:
                    Log.d(TAG, "蓝牙打开");
                    notify(null, STATE_ON);
                    break;
                case BluetoothAdapter.STATE_OFF:
                    Log.d(TAG, "蓝牙关闭");
                    notify(null, STATE_OFF);
                    break;
            }
        }
    }

    @Override
    public void attach(Observer observer) {
        if (!observerList.contains(observer)) {
            observerList.add(observer);
        }
    }

    @Override
    public void detach(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notify(Object obj, int code) {
        for (Observer o : observerList) {
            o.update(obj, code);
        }
    }
}
