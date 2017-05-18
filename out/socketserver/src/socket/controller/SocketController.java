package socket.controller;

import socket.exception.SocketException;
import socket.protocol.SocketMsg;
import socket.routing.item.SocketRoutingItem;
import socket.util.SocketPriority;

import java.util.List;

/**
 * 服务器功能接口
 * <p>
 * Created by ZFly on 2017/4/21.
 */
public interface SocketController {

    /**
     * 控制器优先级
     * 数值越低优先级越高，默认的优先级为 SocketPriority.MEDIUM
     *
     * @return 优先级数值
     */
    default int priority() {
        return SocketPriority.MEDIUM;
    }

    /**
     * 是否响应该消息
     *
     * @param msg 消息对象
     *
     * @return 是否响应
     */
    boolean isResponse(SocketMsg msg);

    /**
     * 控制器工作函数
     * 当控制器的isResponse()方法为true时调用
     * 当控制器工作完成后如果返回true表示该请求被消费，请求传递终止
     * 否则请求继续向下传递
     *
     * @param source    消息发送源
     * @param request   请求消息
     * @param responses 回复消息列表
     *
     * @return 请求是否被消费
     *
     * @throws SocketException 异常消息
     */
    boolean work(SocketRoutingItem source, SocketMsg request, List<SocketMsg> responses) throws SocketException;

}
