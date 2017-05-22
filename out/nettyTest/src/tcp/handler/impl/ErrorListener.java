package tcp.handler.impl;

import tcp.handler.MsgListener;
import tcp.message.SocketMsg;

import io.netty.channel.Channel;
import tcp.message.impl.SocketDefaultMsg;
import tcp.util.common;

/**
 * Created by fadinglan on 2017/5/10.
 */
public class ErrorListener implements MsgListener {
    @Override
    public void handlerMsg(Channel ctx, SocketMsg msg) {

        common.replyMsg(ctx,new SocketDefaultMsg().errorResponse());
//        errorMsg(ctx.channel(), "unknow",4);
//                            logger.error("message type error");
//                            ctx.channel().close();
    }
}
