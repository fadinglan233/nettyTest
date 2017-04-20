package com.protocol.Templates;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * Created by fadinglan on 2016/12/29.
 */
public class SendMessage implements Serializable{

    private static final long	serialVersionUID	= 1L;


        private String from = "server";

        private String to = "unknown";

        private Integer msgType = 1;

        private Integer cmd = null;

        private Object[] params = null;


        public SendMessage() {

        }

        public SendMessage(String from, String to, Integer msgType, Integer cmd, Object[] params ) {
            this.from = from;
            this.to = to;
            this.msgType = msgType;
            this.cmd = cmd;
            this.params = params;

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


        public int getMsgType() {
            return msgType;
        }


        public void setMsgType(int msgType) {
            this.msgType = msgType;
        }

        public Integer getCmd() {
            return cmd;
        }

        public void setCmd(Integer cmd) {
            this.cmd = cmd;
        }

        public Object[] getParams() {
            return params;
        }

        public void setParams(Object[] params) {
            this.params = params;
        }


}
