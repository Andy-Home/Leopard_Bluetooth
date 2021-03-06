package com.andy.leopard_bluetooth.socket.message;

/**
 * 蓝牙通讯信息类
 * <p>
 * Created by andy on 17-10-16.
 */

public abstract class Message {
    final String TAG = getClass().getSimpleName();
    public long id = System.currentTimeMillis();

    public abstract String display();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            return ((Message) obj).id == id;
        }
        return false;
    }
}
