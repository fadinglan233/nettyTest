package com.Message.Reply;

/**
 * Created by fadinglan on 2017/5/4.
 */
public class ReplyClientBody extends ReplyBody {

    private String from = "server";
    private String to = "unknow";
    private Integer msgType = null;
    private Integer flag = 0;

    public ReplyClientBody(String from, String to, Integer msgType, Integer flag){
        this.from = from;
        this.to = to;
        this.msgType = msgType;
        this.flag = flag;
    }

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

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
