package socket;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import socket.controller.SocketController;
import socket.controller.SocketControllersGroup;
import socket.event.SocketEventListenersGroup;
import socket.protocol.SocketProtocolFamily;
import socket.routing.SocketRouting;
import socket.schedule.SocketScheduler;
import socket.secure.delegate.SocketSecureDelegatesGroup;

import javax.annotation.Resource;

/**
 * WTFSocket服务器
 * <p>
 * Created by ZFly on 2017/4/25.
 */
public final class SocketServer {

    /**
     * Spring 上下文
     */
    private ApplicationContext spring = new ClassPathXmlApplicationContext("spring.socket.xml");

    /**
     * 消息调度组件
     * 根据消息的头信息将消息投递到指定的目的地
     */
    @Resource()
    private SocketScheduler scheduler;

    /**
     * 路由组件
     * 查询和记录连接的地址
     */
    @Resource
    private SocketRouting routing;

    /**
     * 协议族组件
     * IO层收到数据后选择合适的解析器将数据解析为标准消息格式
     */
    @Resource
    private SocketProtocolFamily protocolFamily;

    /**
     * 安全组件
     * 可用添加一些安全策略，如发送数据的授权许可等
     */
    @Resource
    private SocketSecureDelegatesGroup secureDelegatesGroup;

    /**
     * 事件组组件
     * 包含了一些服务器的监听事件
     */
    @Resource
    private SocketEventListenersGroup eventsGroup;

    /**
     * 服务器配置
     */
    private SocketConfig config;

    public SocketServer() {
        spring.getAutowireCapableBeanFactory().autowireBean(this);
        scheduler.setContext(this);
        routing.setContext(this);
    }

    public void run(SocketConfig config) {
        this.config = config;
        scheduler.run();
    }

    public SocketServer addController(SocketController controller) {
        if (scheduler.getHandler() instanceof SocketControllersGroup) {
            ((SocketControllersGroup) scheduler.getHandler()).addController(controller);
        }
        return this;
    }

    public SocketScheduler getScheduler() {
        return scheduler;
    }

    public SocketProtocolFamily getProtocolFamily() {
        return protocolFamily;
    }

    public SocketSecureDelegatesGroup getSecureDelegatesGroup() {
        return secureDelegatesGroup;
    }

    public SocketRouting getRouting() {
        return routing;
    }

    public SocketEventListenersGroup getEventsGroup() {
        return eventsGroup;
    }

    public SocketConfig getConfig() {
        return config;
    }

    public void setScheduler(SocketScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setProtocolFamily(SocketProtocolFamily protocolFamily) {
        this.protocolFamily = protocolFamily;
    }

    public void setSecureDelegatesGroup(SocketSecureDelegatesGroup secureDelegatesGroup) {
        this.secureDelegatesGroup = secureDelegatesGroup;
    }

    public void setRouting(SocketRouting routing) {
        this.routing = routing;
    }

    public void setEventsGroup(SocketEventListenersGroup eventsGroup) {
        this.eventsGroup = eventsGroup;
    }

    public SocketConfig setConfig() {
        return config;
    }


    public ApplicationContext getSpring() {
        return spring;
    }
}
