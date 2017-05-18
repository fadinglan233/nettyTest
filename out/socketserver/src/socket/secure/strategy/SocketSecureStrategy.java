package socket.secure.strategy;

import socket.SocketServer;
import socket.exception.SocketException;
import socket.protocol.SocketMsg;

/**
 * 安全策略接口
 * <p>
 * Created by ZFly on 2017/4/25.
 */
public interface SocketSecureStrategy {

    void check(SocketServer context, SocketMsg msg) throws SocketException;

    void setNext(SocketSecureStrategy next);

}
