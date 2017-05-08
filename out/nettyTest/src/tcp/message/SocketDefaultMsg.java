package tcp.message;

/**
 * Created by fadinglan on 2017/5/4.
 */
public class SocketDefaultMsg implements SocketMsg {

    private String from = "server";
    private String to = "unknown";
    private int msgType = 1;
    private int flag = 1;



    @Override
    public SocketMsg makeResponse() {
        SocketDefaultMsg response = new SocketDefaultMsg();
        response.setFrom(getTo());
        response.setTo(getFrom());
        response.setMsgType(getMsgType());
        return response;
    }

    @Override
    public int getFlag() {
        return flag;
    }

    @Override
    public void setFlag(int flag) {
        this.flag = flag;
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
}
