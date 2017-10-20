package com.andy.leopard_bluetooth.socket.message;

import android.util.Log;

/**
 * String类型消息
 * <p>
 * Created by andy on 17-10-20.
 */

public class StringMessage extends Message {

    private String message;

    public StringMessage(String msg) {
        this.message = msg;
    }

    public void setMessage(String msg) {
        message = msg;
    }

    public String getMessage() {
        return message;
    }

    @Override
    void display() {
        if (message != null) {
            Log.d(TAG, "id:" + id + " ---- " + message);
        }
    }
}
