package tcp.message.impl;

import tcp.message.SocketMsg;

/**
 * Created by fadinglan on 2017/5/10.
 */
public class ErrorResponse implements SocketMsg {

    private String from = "server";
    private String to = "unknow";
    private int msgType = 1;
    private int flag = 1;
    private int errorCode = 0;
    private int state = 1;

    public SocketMsg makeResponse (){
        return null;
    }


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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        this.state = state;
    }
}
