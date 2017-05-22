package tcp.task;

import io.netty.channel.ChannelHandlerContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tcp.exception.SocketException;
import tcp.exception.normal.SocketNormalException;
import tcp.handler.CommonMsgDispatcher;
import tcp.handler.ListenerFactory;
import tcp.handler.MsgListener;
import tcp.message.MsgType;
import tcp.message.SocketMsg;
import tcp.message.impl.MsgResponse;
import tcp.message.impl.SocketDefaultMsg;


/**
 * Created by fadinglan on 2017/5/6.
 */
public class ServerTask {

    private static final Log logger = LogFactory.getLog(ServerTask.class);
    private static CommonMsgDispatcher dispatcher = CommonMsgDispatcher.INSTANCE;

    public static void taskSubmit(ChannelHandlerContext ctx, String msg)throws SocketException{


        final SocketMsg data = BeforeCheck.checkState(msg);

        MsgListener listener;
        MsgType msgType = MsgResponse.checkMsgType(data);
        logger.debug("msgType is :" + msgType);
        try {
            listener = ListenerFactory.getListener(msgType);
            dispatcher.registerMsg(msgType, listener);
            dispatcher.fireMsg(ctx.channel(),data,msgType);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("listener class create error");
        }









    }






}
