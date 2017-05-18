package socket.controller.impl;

import org.apache.commons.lang.StringUtils;
import socket.controller.SocketController;
import socket.exception.SocketException;
import socket.protocol.SocketMsg;
import socket.routing.item.SocketRoutingItem;
import socket.routing.item.SocketRoutingTmpItem;
import socket.util.SocketPriority;


import java.util.List;

/**
 * 调试客户端注册控制器
 * <p>
 * Created by ZFly on 2017/4/29.
 */
public enum SocketDebugRegisterControllerImpl implements SocketController {

    INSTANCE;

    @Override
    public int priority() {
        return SocketPriority.HIGHEST;
    }

    @Override
    public boolean isResponse(SocketMsg msg) {
        return StringUtils.startsWith(msg.getFrom(), "Debug_");
    }

    @Override
    public boolean work(SocketRoutingItem source, SocketMsg request, List<SocketMsg> responses) throws SocketException {
        if (source != null && source instanceof SocketRoutingTmpItem) {
            source.setAddress(request.getFrom());
            source.setAccept(request.getVersion());
            source.setDeviceType("Debug");
            ((SocketRoutingTmpItem) source).shiftToDebug();
        }
        return false;
    }
}
