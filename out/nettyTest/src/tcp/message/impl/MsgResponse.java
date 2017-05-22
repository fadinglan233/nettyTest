package tcp.message.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import tcp.message.MsgType;

/**
 * Created by fadinglan on 2017/5/8.
 */
public class MsgResponse {

    private String msg;
    private SocketDefaultMsg defaultMsg;

    public MsgResponse(String msg){
        this.msg = msg;
    }

    public MsgType checkMsgType(){

        SocketDefaultMsg socketDefaultMsg = new SocketDefaultMsg();
        if (StringUtils.startsWith(msg, "I") && msg.length() == 22){
            System.out.println("macdress is : " + msg.substring(7, 19));
            socketDefaultMsg.setFrom(msg.substring(7, 19));
            socketDefaultMsg.setMsgType(1);
            setDefaultMsg(socketDefaultMsg);

            return MsgType.REGISTER;
        }
        JSONObject json;
//        if (StringUtils.startsWith("{", msg) && StringUtils.endsWith("}",msg)){
            try {
                json = JSONObject.parseObject(msg);

                socketDefaultMsg.setFrom(json.getString("from"));
                socketDefaultMsg.setTo(json.getString("to"));
                socketDefaultMsg.setMsgType(json.getIntValue("msgType"));

                MsgType msgType;

                //mstType : 0 -> 开始  1 -> 注册  2 -> 数据    3 -> 结束
                switch (json.getIntValue("msgType")){
                    case 0:
                        msgType =  MsgType.START;
                        break;
                    case 2:
                        msgType =  MsgType.DATA;
                        socketDefaultMsg.setMsgID(json.getInteger("msgID"));
                        socketDefaultMsg.setParams(json.getJSONArray("params"));
                        break;
                    case 3:
                        msgType =  MsgType.END;
                        break;
                    case 4:
                        msgType = MsgType.HEART;
                        break;
                    default:
                        msgType =  MsgType.ERROR;
                        break;
                }
                setDefaultMsg(socketDefaultMsg);
                return msgType;
            }catch (JSONException e){
                return MsgType.ERROR;
            }catch (NullPointerException e1){
                e1.printStackTrace();
                return MsgType.ERROR;
            }catch (ClassCastException e2){
                e2.printStackTrace();
                return MsgType.ERROR;
            }
//        }

//        return MsgType.ERROR;


    }

    public SocketDefaultMsg getDefaultMsg(){
        return this.defaultMsg;
    }

    public void setDefaultMsg(SocketDefaultMsg defaultMsg) {
        this.defaultMsg = defaultMsg;
    }
}
