package tcp.handler.impl;

import io.netty.channel.Channel;
import tcp.handler.MsgListener;
import tcp.message.impl.SocketDefaultMsg;
import tcp.util.common;

/**
 * Created by fadinglan on 2017/5/16.
 */
public class HeartListener implements MsgListener {

    @Override
    public void handlerMsg(Channel ctx, SocketDefaultMsg msg) {

        common.replyMsg(ctx,msg.makeResponse());
    }
}
