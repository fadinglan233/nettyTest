package tcp.message;

import tcp.message.impl.SocketDefaultMsg;

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

    int getState();

    void setState(int state);

    SocketMsg makeResponse();
}
