package socket.schedule;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import socket.SocketServer;
import socket.controller.SocketControllers;
import socket.controller.SocketControllersGroup;
import socket.event.SocketEventsType;
import socket.exception.SocketException;
import socket.exception.fatal.SocketFatalException;
import socket.exception.fatal.SocketInvalidSourceException;
import socket.exception.fatal.SocketProtocolUnsupportedException;
import socket.exception.normal.SocketInvalidTargetException;
import socket.exception.normal.SocketNormalException;
import socket.exception.normal.SocketPermissionDeniedException;
import socket.io.SocketIOBooter;
import socket.protocol.SocketMsg;
import socket.routing.item.SocketRoutingItem;
import socket.routing.item.SocketRoutingTmpItem;
import socket.secure.strategy.SocketSecureStrategy;
import socket.util.SocketLogUtils;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 调度器
 * <p>
 * Created by ZFly on 2017/4/25.
 */
@Component
@Scope("prototype")
public class SocketScheduler {

    private SocketServer context;

    /**
     * 消息处理接口
     * 默认使用SocketControllersGroup
     */
    @Resource(name = "socket.controllersGroup")
    private SocketHandler handler;

    /**
     * 接收到消息时进行的安全检查
     */
    @Resource(name = "socket.secure.onReceive")
    private SocketSecureStrategy onReceiveSecureStrategy;

    /**
     * 发送消息前进行的安全检查
     */
    @Resource(name = "socket.secure.beforeSend")
    private SocketSecureStrategy beforeSendSecureStrategy;

    /**
     * io层启动器
     * 默认使用NettyBooter
     */
    @Resource(name = "socket.nettyBooter")
    private SocketIOBooter ioBooter;

    /**
     * 向服务器提交一个数据包
     * 一般是有io层发起
     *
     * @param packet      数据包
     * @param ioTag       提交数据包的io的标记
     * @param connectType 提交数据的io的连接类型
     * @throws SocketFatalException 致命异常
     */
    public void submit(String packet, String ioTag, String connectType) throws SocketException {
        try {
            final SocketMsg msg = context.getProtocolFamily().parse(packet);
            msg.setConnectType(connectType);
            msg.setIoTag(ioTag);

            final SocketRoutingItem item = context.getRouting().getItem(ioTag);
            context.getEventsGroup().publishEvent(item, msg, SocketEventsType.OnReceiveData);

            if (null != onReceiveSecureStrategy)
                onReceiveSecureStrategy.check(context, msg);

            if (context.getConfig().isUseDebug())
                SocketLogUtils.received(context, packet, msg);

            final List<SocketMsg> responses = new LinkedList<>();
            if (handler != null)
                handler.handle(item, msg, responses);

            sendMsg(responses);
        } catch (SocketNormalException e) {
            if (e.getOriginalMsg() != null) {
                final SocketMsg errResponse = e.getOriginalMsg().makeResponse();
                errResponse.setFrom("server");
                errResponse.setState(e.getErrCode());
                errResponse.setBody(new JSONObject() {{
                    put("cause", e.getMessage());
                }});

                final String data = context.getProtocolFamily().parse(errResponse);
                if (context.getRouting().getFormalMap().contains(errResponse.getTo())) {
                    context.getRouting().getFormalMap().getItem(errResponse.getTo()).getTerm().write(data + context.getConfig().getFirstEOT());
                }

                if (context.getConfig().isUseDebug())
                    SocketLogUtils.exception(context, data, errResponse);
            } else {
                context.getRouting().getItem(ioTag).getTerm().write(e.getMessage() + context.getConfig().getFirstEOT());
            }
        }
    }

    /**
     * 发送消息
     *
     * @param msg 消息对象
     * @throws SocketInvalidSourceException       无效的消息源
     * @throws SocketInvalidTargetException       无效的消息目标
     * @throws SocketProtocolUnsupportedException 不被支持的协议
     * @throws SocketPermissionDeniedException    无发送权限
     */
    public void sendMsg(SocketMsg msg) throws SocketException {

        if (null != beforeSendSecureStrategy)
            beforeSendSecureStrategy.check(context, msg);

        final SocketRoutingItem target = context.getRouting().getItem(msg.getTo());
        msg.setVersion(target.getAccept());

        final String data = context.getProtocolFamily().parse(msg);

        context.getEventsGroup().publishEvent(target, msg, SocketEventsType.BeforeSendData);

        if (context.getConfig().isUseDebug())
            SocketLogUtils.forwarded(context, data, msg);

        target.getTerm().write(data + context.getConfig().getFirstEOT());
    }

    /**
     * 发送一组消息
     *
     * @param msgs 消息对象数组
     * @throws SocketInvalidSourceException       无效的消息源
     * @throws SocketInvalidTargetException       无效的消息目标
     * @throws SocketProtocolUnsupportedException 不被支持的协议
     * @throws SocketPermissionDeniedException    无发送权限
     */
    public void sendMsg(List<SocketMsg> msgs) throws SocketException {
        for (SocketMsg protocol : msgs) {
            sendMsg(protocol);
        }
    }

    /**
     * 添加处理器
     *
     * @param handler 处理器
     */
    public void setHandler(SocketHandler handler) {
        this.handler = handler;
    }

    /**
     * 获取处理器
     *
     * @return 处理器
     */
    public SocketHandler getHandler() {
        return handler;
    }

    /**
     * 启动框架调度器
     */
    public void run() {
        assert context.getConfig() != null;

        // 如果可能，使用spring扫描加载控制器
        if (context.getSpring().getResource(context.getConfig().getSpringPath()).exists() && handler instanceof SocketControllersGroup)
            ((SocketControllersGroup) handler).addControllerFromSpringBeans(context);

        // 如果需要，加载消息转发控制器
        if (context.getConfig().isUseMsgForward() && handler instanceof SocketControllersGroup)
            ((SocketControllersGroup) handler).addController(SocketControllers.msgForwardingController());

        // 启动io层
        ioBooter.work(new HashMap<String, Object>() {{
            put("tcpPort", context.getConfig().getTcpPort());
            put("keepAlive", context.getConfig().isKeepAlive());
            put("context", context);
        }});

        // 如果需要开启临时用户清理任务
        if (context.getConfig().isCleanEmptyConnect()) {
            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                    () -> context.getRouting().getTmpMap().values().stream()
                            .filter(SocketRoutingTmpItem::isExpires)
                            .forEach(item -> {
                                item.getTerm().close();
                                context.getRouting().getTmpMap().remove(item);
                            }),
                    1, 1, TimeUnit.MINUTES);
        }
    }

    public void setContext(SocketServer context) {
        this.context = context;
    }

    public void setOnReceiveSecureStrategy(SocketSecureStrategy onReceiveSecureStrategy) {
        this.onReceiveSecureStrategy = onReceiveSecureStrategy;
    }

    public SocketSecureStrategy getOnReceiveSecureStrategy() {
        return onReceiveSecureStrategy;
    }

    public void setBeforeSendSecureStrategy(SocketSecureStrategy beforeSendSecureStrategy) {
        this.beforeSendSecureStrategy = beforeSendSecureStrategy;
    }

    public SocketSecureStrategy getBeforeSendSecureStrategy() {
        return beforeSendSecureStrategy;
    }

    public void setIoBooter() {
        setIoBooter();
    }

    public void setIoBooter(SocketIOBooter ioBooter) {
        if (ioBooter != null)
            this.ioBooter = ioBooter;
    }
}
