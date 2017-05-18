package socket.secure.strategy.impl;

import org.springframework.stereotype.Component;
import socket.SocketServer;
import socket.exception.SocketException;
import socket.exception.normal.SocketPermissionDeniedException;
import socket.protocol.SocketMsg;
import socket.routing.item.SocketRoutingFormalItem;
import socket.secure.delegate.SocketSecureDelegate;
import socket.secure.delegate.SocketSecureDelegateType;

/**
 * 消息是否有权限进行发送
 * <p>
 * Created by ZFly on 2017/4/22.
 */
@Component
public final class SocketSendPermissionSecureStrategyImpl extends SocketBaseSecureStrategyImpl {

    @Override
    public void check(SocketServer context, SocketMsg msg) throws SocketException {
        final SocketRoutingFormalItem source = (SocketRoutingFormalItem) context.getRouting().getItem(msg.getFrom());
        final SocketSecureDelegate authDelegate = context.getSecureDelegatesGroup().getDelegate(SocketSecureDelegateType.SEND_PERMISSION);
        // 权限校验
        if (!source.isAuthTarget(msg.getTo())) {
            if (authDelegate == null || (boolean) authDelegate.work(msg)) {
                source.addAuthTarget(msg.getTo());
            } else {
                throw new SocketPermissionDeniedException("[" + msg.getFrom() + "] has no permission to send message to [" + msg.getTo() + "]").setOriginalMsg(msg);
            }
        }

        doNext(context, msg);
    }
}
