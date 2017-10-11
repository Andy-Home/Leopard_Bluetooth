package com.andy.leopard_bluetooth.subscribe;

/**
 * 订阅模式——观察者
 * <p>
 * Created by andy on 17-10-10.
 */

public interface Observer {

    void update(Object obj, int code);
}
