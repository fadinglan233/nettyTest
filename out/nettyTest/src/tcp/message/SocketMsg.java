package tcp.message;

import com.alibaba.fastjson.JSONArray;
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

    int getMsgID();

    void setMsgID(int msgID);

    JSONArray getParams();

    void setParams(JSONArray params);
    SocketMsg makeResponse();
    SocketMsg errorResponse();
    SocketMsg pinMsg();
}
