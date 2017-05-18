package socket.exception.normal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import socket.exception.SocketException;
import socket.protocol.SocketMsg;


/**
 * 普通异常
 * 一般指协议已被正常解析
 * 可回溯发送源的异常
 *
 * Created by ZFly on 2017/4/22.
 */
public abstract class SocketNormalException extends SocketException {

    private static final Log logger = LogFactory.getLog(SocketNormalException.class);

    /**
     * 造成异常的源数据
     */
    private SocketMsg originalMsg;

    public SocketNormalException(String msg) {
        super(msg);
        logger.warn(getMessage());
    }

    public SocketMsg getOriginalMsg() {
        return originalMsg;
    }

    public SocketNormalException setOriginalMsg(SocketMsg originalMsg) {
        this.originalMsg = originalMsg;
        return this;
    }
}
