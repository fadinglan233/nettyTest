package com.Message.BaseMessage;

import java.io.Serializable;

/**
 * Created by fadinglan on 2017/5/4.
 */
public abstract class BaseMsg implements Serializable{

    private static final long serialVersionUID = 1L;
//    private MsgType type;
    private String from;
    private String to;
    private int msgType;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
    //必须唯一，否者会出现channel调用混乱


}
