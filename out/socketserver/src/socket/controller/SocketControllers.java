package socket.controller;


import socket.controller.impl.*;

/**
 * 提供常用的控制器
 * <p>
 * Created by ZFly on 2017/4/29.
 */
public final class SocketControllers {

    private SocketControllers() {
    }

    /**
     * 消息转发控制器
     * 优先级为 SocketPriority.LOWEST
     * 不消费请求
     *
     * @return 控制器单例
     */
    public static SocketController msgForwardingController() {
        return SocketMsgForwardingControllerImpl.INSTANCE;
    }

    /**
     * 无条件的注册控制器
     * 即不做任何授权检查即将发消息的临时客户端注册为正式客户端
     * 优先级为 SocketPriority.HIGHEST
     * 不消费请求
     *
     * @return 控制器单例
     */
    public static SocketController unconditionalRegisterController() {
        return SocketUnconditionalRegisterControllerImpl.INSTANCE;
    }

    /**
     * 调试客户端注册控制器
     * 将通讯地址以 Debug_ 开头的临时客户端注册为调试控制端
     * 优先级为 SocketPriority.HIGHEST
     * 不消费请求
     *
     * @return 控制器单例
     */
    public static SocketController debugRegisterController() {
        return SocketDebugRegisterControllerImpl.INSTANCE;
    }

    /**
     * 回声控制器
     * 将消息的 From 和 To 对调后返回客户端
     * 优先级为 SocketPriority.MEDIUM
     * 消费请求
     *
     * @return 控制器单例
     */
    public static SocketController echoController() {
        return SocketEchoControllerImpl.INSTANCE;
    }

    /**
     * 心跳包控制器
     * 仅在消息的目标地址为heartbeat时响应
     * 优先级为 SocketPriority.HIGH
     * 消费请求
     *
     * @return 控制器单例
     */
    public static SocketController heartbeatController() {
        return SocketHeartbeatControllerImpl.INSTANCE;
    }

}
