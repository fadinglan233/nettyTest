package socket.secure.strategy.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import socket.SocketServer;
import socket.exception.SocketException;
import socket.protocol.SocketMsg;
import socket.secure.strategy.SocketSecureStrategy;

/**
 *
 * Created by ZFly on 2017/5/1.
 */
public abstract class SocketBaseSecureStrategyImpl implements SocketSecureStrategy{

    protected Log logger = LogFactory.getLog(this.getClass());

    protected SocketSecureStrategy next;

    protected void doNext(SocketServer context, SocketMsg msg) throws SocketException {
        if (null != next) {
            next.check(context, msg);
        }
    }

    public void setNext(SocketSecureStrategy next) {
        if (null == this.next) {
            this.next = next;
        } else {
            next.setNext(this.next);
            this.next = next;
        }
    }
}
