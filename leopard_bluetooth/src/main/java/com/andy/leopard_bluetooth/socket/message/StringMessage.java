package com.andy.leopard_bluetooth.socket.message;

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
    public String display() {
        if (message != null) {
            return "id:" + id + " ---- " + message;
        }
        return "空消息";
    }
}
