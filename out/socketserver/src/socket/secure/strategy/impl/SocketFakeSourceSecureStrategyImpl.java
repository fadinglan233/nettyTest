package socket.secure.strategy.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import socket.SocketServer;
import socket.exception.SocketException;
import socket.exception.fatal.SocketInvalidSourceException;
import socket.protocol.SocketMsg;
import socket.routing.item.SocketRoutingItem;


/**
 * 发送源是通讯地址和连接io地址是否匹配
 * <p>
 * Created by ZFly on 2017/4/25.
 */
@Component
public final class SocketFakeSourceSecureStrategyImpl extends SocketBaseSecureStrategyImpl {

    @Override
    public void check(SocketServer context, SocketMsg msg) throws SocketException {
        final SocketRoutingItem source = context.getRouting().getItem(msg.getFrom());

        if (!StringUtils.equals(source.getAddress(), "heartbeat") && !StringUtils.equals(source.getTerm().getIoTag(), msg.getIoTag()))
            throw new SocketInvalidSourceException("[" + msg.getFrom() + "] communication address miss match io address");

        doNext(context, msg);
    }
}
