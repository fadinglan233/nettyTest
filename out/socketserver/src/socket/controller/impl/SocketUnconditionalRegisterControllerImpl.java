package socket.controller.impl;


import socket.controller.SocketController;
import socket.exception.SocketException;
import socket.protocol.SocketMsg;
import socket.routing.item.SocketRoutingItem;
import socket.routing.item.SocketRoutingTmpItem;
import socket.util.SocketPriority;

import java.util.List;

/**
 * 无条件注册控制器
 * <p>
 * Created by zfly on 2017/4/29.
 */
public enum SocketUnconditionalRegisterControllerImpl implements SocketController {

    INSTANCE;

    @Override
    public int priority() {
        return SocketPriority.HIGHEST;
    }

    @Override
    public boolean isResponse(SocketMsg msg) {
        return true;
    }

    @Override
    public boolean work(SocketRoutingItem source, SocketMsg request, List<SocketMsg> responses) throws SocketException {
        if (source instanceof SocketRoutingTmpItem) {
            source.setAddress(request.getFrom());
            source.setAccept(request.getVersion());
            source.setDeviceType("Unknown");
            ((SocketRoutingTmpItem) source).shiftToFormal();
        }
        return false;
    }
}
