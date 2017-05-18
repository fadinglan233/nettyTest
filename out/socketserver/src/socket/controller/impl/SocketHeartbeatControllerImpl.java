package socket.controller.impl;

import org.apache.commons.lang.StringUtils;
import socket.SocketServer;
import socket.controller.SocketController;
import socket.exception.SocketException;
import socket.exception.fatal.SocketKeepWordsException;
import socket.io.term.SocketDefaultIOTerm;
import socket.protocol.SocketMsg;
import socket.routing.item.SocketRoutingFormalItem;
import socket.routing.item.SocketRoutingItem;
import socket.secure.strategy.impl.SocketBaseSecureStrategyImpl;
import socket.util.SocketPriority;

import java.util.List;

/**
 *
 * Created by ZFly on 2017/5/1.
 */
public enum SocketHeartbeatControllerImpl implements SocketController {

    INSTANCE;

    SocketHeartbeatControllerImpl() {

    }

    private boolean unInit = true;

    @Override
    public int priority() {
        return SocketPriority.HIGH;
    }

    @Override
    public boolean isResponse(SocketMsg msg) {
        return msg.getMsgType() == 0;
    }

    @Override
    public boolean work(SocketRoutingItem source, SocketMsg request, List<SocketMsg> responses) throws SocketException {
        if (unInit) {
            // 注册heartbeat用户
            final SocketRoutingFormalItem heartbeatItem = new SocketRoutingFormalItem();
            heartbeatItem.setTerm(new SocketDefaultIOTerm());
            heartbeatItem.setContext(source.getContext());
            heartbeatItem.setAddress("heartbeat");
            heartbeatItem.setCover(false);
            heartbeatItem.addAuthTarget("*");
            source.getContext().getRouting().getFormalMap().add(heartbeatItem);
            // 添加安全策略
            source.getContext().getScheduler().getOnReceiveSecureStrategy().setNext(new SocketBaseSecureStrategyImpl() {
                @Override
                public void check(SocketServer context, SocketMsg msg) throws SocketException {
                    if (StringUtils.equals("heartbeat", msg.getFrom()))
                        throw new SocketKeepWordsException("Source can not be [heartbeat]");
                    doNext(context, msg);
                }
            });
            unInit = false;
        }

        final SocketMsg response = request.makeResponse();
        response.setBody(request.getBody());
        responses.add(response);
        return true;
    }
}
