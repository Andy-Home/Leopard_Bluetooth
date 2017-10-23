package com.andy.leopard_bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.andy.leopard_bluetooth.socket.message.Message;
import com.andy.leopard_bluetooth.subscribe.Bluetooth;

import java.io.File;
import java.util.UUID;

/**
 * 蓝牙相关操作管理接口
 * <p>
 * Created by andy on 17-9-22.
 */

public class LeopardManager {
    private static LeopardManager mLeopardManager = null;
    private BluetoothClient mBluetoothClient = BluetoothClient.getInstance();

    private LeopardManager() {
    }

    public static LeopardManager getInstance() {
        if (mLeopardManager == null) {
            mLeopardManager = new LeopardManager();
        }
        return mLeopardManager;
    }

    public void init(Context context, UUID uuid) {
        mBluetoothClient.init(context, uuid);

    }

    /**
     * 打开蓝牙
     */
    public boolean open() {
        return mBluetoothClient.open();
    }

    /**
     * 设置通讯的远程蓝牙设备
     *
     * @param device 远程蓝牙设备对象
     */
    public boolean setDevice(BluetoothDevice device) {
        return false;
    }

    /**
     * 设备绑定
     *
     * @param device   远程蓝牙设备对象
     * @param listener 监听回调 {@link com.andy.leopard_bluetooth.subscribe.Bluetooth.BondListener}
     */
    public void bond(BluetoothDevice device, Bluetooth.BondListener listener) {
        mBluetoothClient.bond(device, listener);
    }

    public void connect(BluetoothDevice device, BluetoothClient.ConnectListener listener) {
        mBluetoothClient.connect(device, listener);
    }

    /**
     * 发送文件至通讯的蓝牙设备
     *
     * @param file 文件对象
     */
    public boolean sendFile(File file) {
        return false;
    }

    public void sendMessage(Message msg) {
        mBluetoothClient.send(msg);
    }

    /**
     * 关闭蓝牙
     */
    public boolean close() {
        return mBluetoothClient.close();
    }

    public void setDeviceUpdateListener(BluetoothClient.DeviceUpdateListener listener) {
        mBluetoothClient.setDeviceUpdateListener(listener);
    }
}
