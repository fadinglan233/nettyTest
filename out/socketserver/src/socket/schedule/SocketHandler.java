package socket.schedule;

import socket.exception.SocketException;
import socket.protocol.SocketMsg;
import socket.routing.item.SocketRoutingItem;

import java.util.List;

/**
 * 调度器服务函数接口
 * <p>
 * Created by ZFly on 2017/4/25.
 */
public interface SocketHandler {

    void handle(SocketRoutingItem source, SocketMsg request, List<SocketMsg> responses) throws SocketException;

}
