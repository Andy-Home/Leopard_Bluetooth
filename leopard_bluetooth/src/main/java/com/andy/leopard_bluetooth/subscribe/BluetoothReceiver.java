package com.andy.leopard_bluetooth.subscribe;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * 与蓝牙操作相关的广播接收器
 * <p>
 * Created by andy on 17-9-22.
 */

public class BluetoothReceiver extends BroadcastReceiver implements Subject {
    private List<BluetoothDevice> deviceList = new ArrayList<>();

    private List<Observer> observerList = new ArrayList<>();

    /**
     * 查看底层代码，因为 BluetoothDevice 中的 ACTION_DISAPPEARED 隐藏的原因，自定义该常量
     */
    private static final String ACTION_DISAPPEARED = "android.bluetooth.device.action.DISAPPEARED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(BluetoothDevice.ACTION_FOUND)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (!deviceList.contains(device)) {
                deviceList.add(device);
                notify(deviceList);
            }
        } else if (action.equals(ACTION_DISAPPEARED)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (deviceList.contains(device)) {
                deviceList.remove(device);
                notify(deviceList);
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
    public void notify(Object obj) {
        for (Observer o : observerList) {
            o.update(obj);
        }
    }
}
