package com.Message.Data;

import com.Message.BaseMessage.BaseMsg;
import com.Message.BaseMessage.MsgType;

/**
 * Created by fadinglan on 2017/5/4.
 */
public class DataMsg extends BaseMsg {

    public DataMsg(){
        super();
//        setType(MsgType.DATA);
    }

    private DataMsg dataMsg;


    public DataMsg getDataMsg() {
        return dataMsg;
    }

    public void setDataMsg(DataMsg dataMsg) {
        this.dataMsg = dataMsg;
    }
}
