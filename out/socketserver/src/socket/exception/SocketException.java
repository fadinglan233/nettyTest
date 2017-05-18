package socket.exception;

/**
 * 服务器异常
 * <p>
 * Created by ZFly on 2017/4/22.
 */
public abstract class SocketException extends Exception {


    public SocketException(String msg) {
        super(msg);
    }

    public abstract int getErrCode();

}
