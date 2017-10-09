package com.andy.leopard_bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import java.io.File;
import java.util.UUID;

/**
 * 蓝牙相关操作管理接口
 * <p>
 * Created by andy on 17-9-22.
 */

public class LeopardManager {
    private static LeopardManager mLeopardManager = null;
    private BluetoothUtil mBluetoothUtil = BluetoothUtil.getInstance();

    private LeopardManager() {
    }

    public static LeopardManager getInstance() {
        if (mLeopardManager == null) {
            mLeopardManager = new LeopardManager();
        }
        return mLeopardManager;
    }

    public void init(Context context, UUID uuid) {
        mBluetoothUtil.init(context, uuid);

    }

    /**
     * 打开蓝牙
     */
    public boolean open() {
        return mBluetoothUtil.open();
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
     * 发送文件至通讯的蓝牙设备
     *
     * @param file 文件对象
     */
    public boolean sendFile(File file) {
        return false;
    }

    /**
     * 关闭蓝牙
     */
    public boolean close() {
        return mBluetoothUtil.close();
    }
}
