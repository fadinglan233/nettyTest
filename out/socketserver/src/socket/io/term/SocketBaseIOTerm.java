package socket.io.term;


import socket.io.SocketIOTerm;

/**
 * 终端辅助基类
 *
 * Created by ZFly on 2017/4/22.
 */
public abstract class SocketBaseIOTerm implements SocketIOTerm {

    protected String ioTag = "0";
    protected String connectType = "TCP";
//    protected final SocketServer context;

//    public SocketBaseIOTerm(SocketServer context) {
//        this.context = context;
//    }

//    @Override
//    public SocketServer getContext() {
//        return context;
//    }

//    @Override
//    public void setContext(SocketServer context) {
//    }

    @Override
    public String getIoTag() {
        return ioTag;
    }

    @Override
    public void setIoTag(String ioTag) {
        this.ioTag = ioTag;
    }

    @Override
    public String getConnectType() {
        return connectType;
    }

    @Override
    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }

}
