package socket.exception.normal;

/**
 * 无效的目标异常
 *
 * Created by ZFly on 2017/4/22.
 */
public class SocketInvalidTargetException extends SocketNormalException {

    public SocketInvalidTargetException(String msg) {
        super(msg);
    }

    public int getErrCode() {
        return 16;
    }

}
