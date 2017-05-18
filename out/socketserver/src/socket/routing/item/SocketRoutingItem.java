package socket.routing.item;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import socket.SocketServer;
import socket.event.SocketEventsType;
import socket.exception.SocketException;
import socket.io.SocketIOTerm;

/**
 * 路由表对象
 * 每条对象对应一个实际的目标
 * <p>
 * Created by ZFly on 2017/4/23.
 */
@Component
@Scope("prototype")
public abstract class SocketRoutingItem {

    /**
     * 自身通讯地址
     */
    private String address;
    /**
     * 终端对象
     */
    private SocketIOTerm term;
    /**
     * 接受的协议类型
     */
    private String accept;
    /**
     * 终端类型（iOS, Android, Hardware）
     */
    private String deviceType = "Unknown";
    /**
     * 是否允许覆盖
     */
    private boolean cover = true;
    /**
     * 允许用户为客户端添加自定义Tag信息
     */
    private Object tag;

    private SocketServer context;

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? "Unknown" : deviceType;
    }

    public SocketIOTerm getTerm() {
        return term;
    }

    public void setTerm(SocketIOTerm term) {
        this.term = term;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public SocketServer getContext() {
        return context;
    }

    public void setContext(SocketServer context) {
        this.context = context;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public void close() throws SocketException {
        getContext().getEventsGroup().publishEvent(this, null, SocketEventsType.Disconnect);
        getTerm().close();
    }
}
