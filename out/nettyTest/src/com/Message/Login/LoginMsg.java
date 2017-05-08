package com.Message.Login;

import com.Message.BaseMessage.BaseMsg;
import com.Message.BaseMessage.MsgType;

/**
 * Created by fadinglan on 2017/5/4.
 */
public class LoginMsg extends BaseMsg {

    private String macAddress;


    public LoginMsg(){
        super();
//        setType(MsgType.LOGIN);
    }

    public String getMacAddress(){
        return macAddress;
    }

    public void setMacAddress(String macAddress){
        this.macAddress = macAddress;
    }
}
