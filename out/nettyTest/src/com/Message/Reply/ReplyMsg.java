package com.Message.Reply;

import com.Message.BaseMessage.BaseMsg;
import com.Message.BaseMessage.MsgType;

/**
 * Created by fadinglan on 2017/5/4.
 */
public class ReplyMsg extends BaseMsg {

    public ReplyMsg(){
        super();
//        setType(MsgType.REPLY);
    }

    private ReplyBody body;


    public ReplyBody getBody() {
        return body;
    }

    public void setBody(ReplyBody body) {
        this.body = body;
    }
}
