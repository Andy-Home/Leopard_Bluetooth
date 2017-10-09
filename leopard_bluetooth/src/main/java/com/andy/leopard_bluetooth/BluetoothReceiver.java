package com.andy.leopard_bluetooth;

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

public class BluetoothReceiver extends BroadcastReceiver {
    private List<BluetoothDevice> deviceList = new ArrayList<>();
    private String targetAddress = null;
    private BluetoothUtil.FindDeviceListener mListener = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(BluetoothDevice.ACTION_FOUND)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (!deviceList.contains(device)) {
                deviceList.add(device);

                if (targetAddress != null) {
                    if (targetAddress.equals(device.getAddress())) {
                        mListener.success(device);
                        //当查找到信息时，清空信息内容
                        targetAddress = null;
                        mListener = null;
                    }
                }
            }


        }
    }

    public void getBluetoothDevice(String address, BluetoothUtil.FindDeviceListener listener) {
        boolean flag = true;
        //查找当前获取的远程蓝牙设备列表中是否存在目标蓝牙设备，查找到通过回调函数反馈结果
        for (BluetoothDevice device : deviceList) {
            if (device.getAddress().equals(address)) {
                listener.success(device);
                flag = false;
            }
        }
        //未在列表中找到设备，等待查找线程查找设备
        if (flag) {
            targetAddress = address;
            mListener = listener;
        }
    }
}
