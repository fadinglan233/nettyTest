package tcp.message;

/**
 * Created by fadinglan on 2017/5/8.
 */
public enum  MsgType {
    /**
     * 硬件注册信息
     */
    REGISTER,

    /**
     * 睡眠开始标识
     */
    START,

    /**
     * 睡眠结束标识
     */
    END,

    /**
     * 睡眠数据上传
     */
    DATA,

    /**
     * 错误标识
     */
    ERROR

}
