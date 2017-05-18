package socket.secure.delegate;

import socket.protocol.SocketMsg;

/**
 * 安全代理
 * <p>
 * Created by ZFly on 2017/4/25.
 */
@FunctionalInterface
public interface SocketSecureDelegate {

    Object work(SocketMsg msg);
}
