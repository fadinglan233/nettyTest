package socket.protocol;

import socket.exception.fatal.SocketProtocolBrokenException;
import socket.util.SocketPriority;

/**
 * 协议解析器接口
 * <p>
 * Created by ZFly on 2017/4/21.
 */
public interface SocketProtocolParser {

    /**
     * 优先级
     *
     * @return 优先级
     */
    default int getPriority() {
        return SocketPriority.MEDIUM;
    }

    /**
     * 是否负责该字符串数据
     *
     * @param data 原始数据
     *
     * @return 是否负责
     */
    boolean isResponse(String data);

    /**
     * 是否负责该消息对象
     *
     * @param msg 消息对象
     *
     * @return 是否负责
     */
    boolean isResponse(SocketMsg msg);

     /**
     * 从字符串数据中解析数据生产消息对象
     *
     * @param data 原始数据
     *
     * @return 消息对象
     */
    SocketMsg parse(String data) throws SocketProtocolBrokenException;

    /**
     * 将消息对象打包成字符串数据
     *
     * @param msg 消息对象
     *
     * @return 打包后的字符串
     */
    String parse(SocketMsg msg);
}
