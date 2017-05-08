package tcp.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by fadinglan on 2017/5/8.
 */
public class MsgResponse {

    private String msg;
    private ProtocalMsg protocalMsg;
    private MsgType msgType;

    public MsgResponse(String msg){
        this.msg = msg;
    }

    public MsgType checkMsgType(){


        if (StringUtils.startsWith(msg, "I") && msg.length() == 22){
            ProtocalMsg protocalMsg = new ProtocalMsg();
            protocalMsg.setFrom(msg.substring(7, 19));
            protocalMsg.setMsgType(1);
            setProtocalMsg(protocalMsg);
            return MsgType.REGISTER;
        }
        JSONObject json;
        try {
            ProtocalMsg protocalMsg = new ProtocalMsg();
            json = JSONObject.parseObject(msg);

            MsgType msgType;
            protocalMsg.setFrom(json.getString("from"));
            protocalMsg.setTo(json.getString("to"));
            protocalMsg.setMsgType(json.getIntValue("msgType"));

            //mstType : 0 -> 开始  1 -> 注册  2 -> 数据    3 -> 结束
            switch (json.getIntValue("msgType")){
                case 0:
                    msgType =  MsgType.START;
                    break;
                case 2:
                    protocalMsg.setParams(json.getJSONArray("params"));
                    msgType =  MsgType.DATA;
                    break;
                case 3:
                    msgType =  MsgType.END;
                    break;
                default:
                    msgType =  MsgType.ERROR;
                    break;
            }
            setProtocalMsg(protocalMsg);
            return msgType;
        }catch (JSONException e){
            return MsgType.ERROR;
        }catch (NullPointerException e1){
            return MsgType.ERROR;
        }catch (ClassCastException e2){
            return MsgType.ERROR;
        }
    }

    public  ProtocalMsg getProtocalMsg() {
        return protocalMsg;
    }

    public  void setProtocalMsg(ProtocalMsg protocalMsg) {
        this.protocalMsg = protocalMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
}
