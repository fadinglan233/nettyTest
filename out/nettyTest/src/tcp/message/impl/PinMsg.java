package tcp.message.impl;

import com.alibaba.fastjson.JSONArray;
import tcp.message.SocketMsg;

/**
 * Created by fadinglan on 2017/5/16.
 */
public class PinMsg implements SocketMsg {

    private String from = "server";
    private String to = "unknow";
    private int msgType = 1;
    private int state = 1;

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public int getMsgType() {
        return msgType;
    }

    @Override
    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public int getMsgID() {
        return 0;
    }

    @Override
    public void setMsgID(int msgID) {

    }

    @Override
    public JSONArray getParams() {
        return null;
    }

    @Override
    public void setParams(JSONArray params) {

    }

    @Override
    public SocketMsg makeResponse() {
        return null;
    }

    @Override
    public SocketMsg errorResponse() {
        return null;
    }

    @Override
    public SocketMsg pinMsg() {
        return null;
    }
}
