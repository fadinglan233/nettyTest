package tcp.message;

import com.alibaba.fastjson.JSONArray;

/**
 * Created by fadinglan on 2017/5/8.
 */
public class ProtocalMsg  {

    private String from = "unknow";
    private String to = "server";
    private Integer msgType = null;
    private JSONArray params = null;

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
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public JSONArray getParams() {
        return params;
    }

    public void setParams(JSONArray params) {
        this.params = params;
    }
}
