package com.protocol.Templates;

import java.util.Date;

/**
 * Created by fadinglan on 2016/12/10.
 */
public class rePlay {
    private String from = "server";
    private String to = "hardware";
    private Integer flag = null;
    private Integer MsgType = null;
    private Integer errCode = null;



    public rePlay (String from, String to, Integer MsgType,  Integer flag) {
        this.from = from;
        this.to = to;
        this.MsgType = MsgType;
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
        return MsgType;
    }

    public void setMsgType(Integer msgType) {
        MsgType = msgType;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

}
