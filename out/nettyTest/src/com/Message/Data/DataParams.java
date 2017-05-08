package com.Message.Data;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * Created by fadinglan on 2017/5/4.
 */
public class DataParams implements Serializable{

    private static final long serialVersionUID = 1L;
    private String from = "unknow";
    private String to = "server";

    private Integer msgType = 1;

    private Integer cmd = null;

    private JSONArray params = null;


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }



    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    public JSONArray getParams() {
        return params;
    }

    public void setParams(JSONArray params) {
        this.params = params;
    }

    public Integer getCmd() {
        return cmd;
    }

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }
}
