package socket.secure.strategy.impl;

import org.springframework.stereotype.Component;
import socket.SocketServer;
import socket.exception.SocketException;
import socket.exception.fatal.SocketInvalidSourceException;
import socket.protocol.SocketMsg;

/**
 * 发送源是否已注册为正式客户端
 * <p>
 * Created by ZFly on 2017/4/25.
 */
@Component
public final class SocketContainsSourceSecureStrategyImpl extends SocketBaseSecureStrategyImpl {

    @Override
    public void check(SocketServer context, SocketMsg msg) throws SocketException {
        if (!context.getRouting().getFormalMap().contains(msg.getFrom()) && !context.getRouting().getDebugMap().contains(msg.getFrom()))
            throw new SocketInvalidSourceException("Source [" + msg.getFrom() + "] was never registered");
        doNext(context, msg);
    }

}
