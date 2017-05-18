package socket.controller.impl;


import socket.controller.SocketController;
import socket.exception.SocketException;
import socket.protocol.SocketMsg;
import socket.routing.item.SocketRoutingItem;

import java.util.List;

/**
 * 回声控制器
 * <p>
 * Created by ZFly on 2017/4/29.
 */
public enum SocketEchoControllerImpl implements SocketController {

    INSTANCE;

    @Override
    public boolean isResponse(SocketMsg msg) {
        return true;
    }

    @Override
    public boolean work(SocketRoutingItem source, SocketMsg request, List<SocketMsg> responses) throws SocketException {
        final SocketMsg echo = request.makeResponse();
        echo.setBody(request.getBody());
        responses.add(echo);
        return true;
    }
}
