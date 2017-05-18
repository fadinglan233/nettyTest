package socket.secure.strategy.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import socket.SocketServer;
import socket.exception.SocketException;
import socket.exception.fatal.SocketKeepWordsException;
import socket.protocol.SocketMsg;

/**
 * 是否使用了系统保留关键字
 * <p>
 * Created by ZFly on 2017/4/25.
 */
@Component
public final class SocketTargetKeepWordsSecureStrategyImpl extends SocketBaseSecureStrategyImpl {

    @Override
    public void check(SocketServer context, SocketMsg msg) throws SocketException {
        if (StringUtils.equals("server", msg.getTo()))
            throw new SocketKeepWordsException("Target can not be [" + msg.getTo() + "]");

        doNext(context, msg);
    }
}
