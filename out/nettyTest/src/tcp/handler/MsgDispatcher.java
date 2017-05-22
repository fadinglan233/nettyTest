package tcp.handler;

import tcp.message.MsgType;
import tcp.message.SocketMsg;
import tcp.message.impl.SocketDefaultMsg;

import io.netty.channel.Channel;

/**
 * Created by fadinglan on 2017/5/10.
 */
public interface MsgDispatcher {

    /**
     * 注册事件
     * @param msgType
     * @param listener
     */
    public void registerMsg(MsgType msgType, MsgListener listener);


    /**
     * 消息分发
     * @param msg
     */
    public void fireMsg(Channel ctx, SocketDefaultMsg msg, MsgType msgType);

    /**
     * 移除事件
     * @param listener
     * @param msgType
     */
    public void removeEventListener(MsgListener listener, MsgType msgType);

}
