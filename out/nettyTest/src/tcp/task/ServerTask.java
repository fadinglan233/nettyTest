package tcp.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tcp.handler.MsgListener;
import tcp.handler.impl.*;
import tcp.message.MsgType;
import tcp.message.impl.MsgResponse;
import tcp.message.impl.SocketDefaultMsg;
import tcp.mysql.DeviceInfo;
import tcp.netty.ServerMain;
import tcp.util.common;


/**
 * Created by fadinglan on 2017/5/6.
 */
public class ServerTask {

    private static final Log logger = LogFactory.getLog(ServerTask.class);
    private static CommonMsgDispatcher dispatcher = CommonMsgDispatcher.INSTANCE;

    public static void taskSubmit(ChannelHandlerContext ctx, String msg){

        MsgListener listener;

        MsgResponse msgResponse = new MsgResponse(msg);
        MsgType msgType = msgResponse.checkMsgType();
        SocketDefaultMsg defaultMsg = msgResponse.getDefaultMsg();
        try {
            String deviceId = defaultMsg.getFrom();
        }catch (Exception e){
            defaultMsg.errorResponse();
            logger.error("msgType ERROR");
            return;
        }

        logger.debug("msgType is :" + msgType);

        try {
            listener = ListenerFactory.getListener(msgType);
            dispatcher.registerMsg(msgType, listener);
            dispatcher.fireMsg(ctx.channel(),defaultMsg,msgType);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("listener class create error");
//            common.replyMsg(ctx.channel(), defaultMsg.makeResponse());
        }



    }






}
