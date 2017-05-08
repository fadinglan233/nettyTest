package tcp.message;

/**
 * Created by fadinglan on 2017/5/4.
 */
public interface SocketMsg {

    String getFrom();

    void setFrom(String from);

    String getTo();

    void setTo(String to);

    int getMsgType();

    void setMsgType(int msgType);

    void setFlag(int flag);
    int getFlag();

    SocketMsg makeResponse();
}
