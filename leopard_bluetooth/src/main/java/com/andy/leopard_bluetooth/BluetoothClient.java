package com.andy.leopard_bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Build;
import android.util.Log;

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

    /**
     * 根据地址连接对应蓝牙设备
     *
     * @param address  蓝牙地址{@link BluetoothDevice#getAddress()}
     * @param listener 监听回调接口
     * @param code     作为返回数据时的请求标识
     */
    public void connectDevice(String address, final ConnectListener listener, final int code) {
        BluetoothDevice device = null;
        //通过
        for (BluetoothDevice d : deviceList) {
            if (d.getAddress().equals(address)) {
                device = d;
            }
        }
        if (device == null) {
            listener.failure("没有发现该蓝牙设备");
            return;
        }
        cancelDiscovery();  //减少资源占用,停止搜索

        //检查设备状态,判断设备是否配对
        int status = device.getBondState();
        switch (status) {
            case BluetoothDevice.BOND_BONDED:
                connect(device, listener);
                break;
            case BluetoothDevice.BOND_BONDING:
                Log.i(TAG, "正在配对");
                break;
            case BluetoothDevice.BOND_NONE:
                Log.i(TAG, "设备未配对,正在进行配对操作");
                //当前设备未与目标设备配对时,先配对
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        device.createBond();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void connect(BluetoothDevice device, final ConnectListener listener) {
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
            case BluetoothReceiver.BOND_BONDED:
                if (mBondListener != null) {
                    mBondListener.bonded((BluetoothDevice) obj);
                }
                break;
            case BluetoothReceiver.BOND_NONE:
                if (mBondListener != null) {
                    mBondListener.none((BluetoothDevice) obj);
                }
                break;
            case BluetoothReceiver.BOND_BONDING:
                if (mBondListener != null) {
                    mBondListener.bonding((BluetoothDevice) obj);
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

    private BondListener mBondListener;

    /**
     * 设置配对监听接口
     */
    public void setBondListener(BondListener listener) {
        mBondListener = listener;
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

    public interface BondListener {
        void bonded(BluetoothDevice device);

        void bonding(BluetoothDevice device);

        void none(BluetoothDevice device);
    }
}
