package socket.exception.fatal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import socket.exception.SocketException;

/**
 * 致命异常
 * 一般指协议无法被解析
 * 不可回溯发送源的异常
 * <p>
 * Created by ZFly on 2017/4/22.
 */
public abstract class SocketFatalException extends SocketException {

    private static final Log logger = LogFactory.getLog(SocketFatalException.class);

    public SocketFatalException(String msg) {
        super(msg);
        logger.error(getMessage());
    }
}
