package socket.secure.strategy.impl;

import org.springframework.stereotype.Component;
import socket.SocketServer;
import socket.exception.SocketException;
import socket.exception.normal.SocketInvalidTargetException;
import socket.protocol.SocketMsg;


/**
 * 发送目标是否已注册为正式客户端
 * <p>
 * Created by ZFly on 2017/4/25.
 */
@Component
public final class SocketContainsTargetSecureStrategyImpl extends SocketBaseSecureStrategyImpl {

    @Override
    public void check(SocketServer context, SocketMsg msg) throws SocketException {
        if (!context.getRouting().getFormalMap().contains(msg.getTo()) && !context.getRouting().getDebugMap().contains(msg.getTo()))
            throw new SocketInvalidTargetException("Target [" + msg.getTo() + "] was never registered").setOriginalMsg(msg);
        doNext(context, msg);
    }
}
