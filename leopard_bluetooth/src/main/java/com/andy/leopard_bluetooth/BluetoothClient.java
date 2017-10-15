package com.andy.leopard_bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import com.andy.leopard_bluetooth.subscribe.Bluetooth;
import com.andy.leopard_bluetooth.subscribe.BluetoothReceiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 蓝牙工具类
 * <p>
 * Created by andy on 17-9-22.
 */

public class BluetoothClient extends Bluetooth {
    private final String TAG = getClass().getSimpleName();
    private UUID mUUID;
    private static BluetoothClient mBluetoothClient = null;

    private BluetoothClient() {
    }

    public static BluetoothClient getInstance() {
        if (mBluetoothClient == null) {
            mBluetoothClient = new BluetoothClient();
        }
        return mBluetoothClient;
    }

    /**
     * 初始化
     */
    public void init(Context context, UUID uuid) {
        super.setContext(context);
        this.mUUID = uuid;
    }

    private BluetoothSocket socket;
    private List<BluetoothDevice> deviceList = new ArrayList<>();

    public void connect(BluetoothDevice device, final ConnectListener listener) {

        if (!deviceList.contains(device)) {
            listener.failure("没有发现该蓝牙设备");
            return;
        }
        cancelDiscovery();  //减少资源占用,停止搜索
        try {
            socket = device.createRfcommSocketToServiceRecord(mUUID);
            socket.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Object obj, int code) {
        super.update(obj, code);
        switch (code) {
            case BluetoothReceiver.DEVICE_LIST_UPDATE:
                deviceList = (List<BluetoothDevice>) obj;
                if (mDeviceUpdateListener != null) {
                    mDeviceUpdateListener.update(deviceList);
                }
                break;
        }

    }

    private DeviceUpdateListener mDeviceUpdateListener;

    /**
     * 可见列表数据监听接口设置
     */
    public void setDeviceUpdateListener(DeviceUpdateListener listener) {
        mDeviceUpdateListener = listener;
    }

    public interface ConnectListener {
        void success(String result);

        void failure(String msg);
    }

    /**
     * 可见蓝牙列表数据更新监听回调接口
     */
    public interface DeviceUpdateListener {

        void update(List<BluetoothDevice> data);
    }
}
