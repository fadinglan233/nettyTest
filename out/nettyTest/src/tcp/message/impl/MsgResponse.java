package tcp.message.impl;
import tcp.message.MsgType;
import tcp.message.SocketMsg;

/**
 * Created by fadinglan on 2017/5/8.
 */
public class MsgResponse {


    public static MsgType checkMsgType(SocketMsg msg){
        //mstType : 0 -> 开始  1 -> 注册  2 -> 数据    3 -> 结束    4 -> 心跳
        switch (msg.getMsgType()){
            case 0:
                return MsgType.START;
            case 1:
                return MsgType.REGISTER;
            case 2:
                return MsgType.DATA;
            case 3:
                return MsgType.END;
            case 4:
                return MsgType.HEART;
            default:
                return MsgType.ERROR;
        }

    }

}
