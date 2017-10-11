package com.andy.leopard_bluetooth.subscribe;

/**
 * 订阅模式——被观察者
 * <p>
 * Created by andy on 17-10-10.
 */

public interface Subject {

    /**
     * 增加订阅者
     *
     * @param observer
     */
    void attach(Observer observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    void detach(Observer observer);

    /**
     * 通知订阅者更新消息
     */
    void notify(Object obj, int code);
}
