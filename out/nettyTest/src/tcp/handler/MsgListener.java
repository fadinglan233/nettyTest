package tcp.handler;

import tcp.message.SocketMsg;

import io.netty.channel.Channel;
import tcp.message.impl.SocketDefaultMsg;

/**
 * Created by fadinglan on 2017/5/10.
 */
public interface MsgListener {

    public void handlerMsg(Channel ctx, SocketMsg msg);
}
