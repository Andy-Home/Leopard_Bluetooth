package com.andy.leopard_bluetooth.socket.queue;

import com.andy.leopard_bluetooth.socket.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息队列类
 * <p>
 * Created by andy on 17-10-20.
 */

public class MessageQueue {
    private List<Message> msgList = null;

    public MessageQueue() {
        msgList = new ArrayList<>();
    }

    /**
     * 插入消息至队列
     */
    public void in(Message msg) {
        msgList.add(msg);
    }

    /**
     * 获取队列消息
     */
    public Message out() {
        Message msg = null;
        if (msgList.size() != 0) {
            msg = msgList.get(0);
            msgList.remove(0);
        }
        return msg;
    }

    /**
     * 移除消息队列中的对应消息
     */
    public boolean remove(Message msg) {
        if (msgList.contains(msg)) {
            return msgList.remove(msg);
        }
        return false;
    }

    /**
     * 队列消息数
     */
    public int size() {
        return msgList.size();
    }
}
