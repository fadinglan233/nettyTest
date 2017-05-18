package socket.controller.impl;


import socket.controller.SocketController;
import socket.exception.SocketException;
import socket.protocol.SocketMsg;
import socket.routing.item.SocketRoutingItem;
import socket.util.SocketPriority;

import java.util.List;

/**
 * 消息转发控制器
 * <p>
 * Created by zfly on 2017/4/29.
 */
public enum SocketMsgForwardingControllerImpl implements SocketController {

    INSTANCE;

    @Override
    public int priority() {
        return SocketPriority.LOWEST;
    }

    @Override
    public boolean isResponse(SocketMsg msg) {
        return true;
    }

    @Override
    public boolean work(SocketRoutingItem source, SocketMsg request, List<SocketMsg> responses) throws SocketException {
        if (responses.isEmpty()) {
            responses.add(request);
        }
        return true;
    }
}
