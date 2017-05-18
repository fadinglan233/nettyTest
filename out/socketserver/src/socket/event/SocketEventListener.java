package socket.event;

import socket.exception.SocketException;
import socket.routing.item.SocketRoutingItem;

/**
 * 事件监听者接口
 * <p>
 * Created by ZFly on 2017/4/25.
 */
@FunctionalInterface
public interface SocketEventListener {
    void eventOccurred(SocketRoutingItem item, Object info) throws SocketException;
}
