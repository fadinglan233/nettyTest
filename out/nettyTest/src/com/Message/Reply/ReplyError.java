package com.Message.Reply;

/**
 * Created by fadinglan on 2017/5/4.
 */
public class ReplyError extends  ReplyMsg{

    public ReplyError (){
//        super();
    }

    private Integer errorCode;


    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
