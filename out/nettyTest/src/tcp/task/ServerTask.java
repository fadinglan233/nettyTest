package tcp.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tcp.message.*;
import tcp.mysql.DeviceInfo;
import tcp.netty.ServerMain;
import tcp.util.common;


/**
 * Created by fadinglan on 2017/5/6.
 */
public class ServerTask {

    private static final Log logger = LogFactory.getLog(ServerTask.class);

    public static void taskSubmit(ChannelHandlerContext ctx, String msg){

        MsgResponse response = new MsgResponse(msg);
        MsgType msgType = response.checkMsgType();
        ProtocalMsg protocalMsg = response.getProtocalMsg();
        try {
            String from = protocalMsg.getFrom();
            if (msgType == MsgType.REGISTER){

            }
//        if (ServerMain.dataMap.containsKey(from)) {
            switch (msgType){
                case REGISTER:
                    if (DeviceInfo.queryDevice(from)){
                        //返回成功信息
                        ServerMain.dataMap.put(from, "");
                        replyMsg(ctx.channel(), from, protocalMsg.getMsgType());
                        logger.debug("HardwareAddress is : " + from);
                    }else {
                        ctx.channel().close();
                        //返回错误信息
                        errorMsg(ctx.channel(), from, 122);
                        logger.error("there are not this hardware");
                        return;
                    }
                    break;
                case START:
                    ServerMain.dataMap.put(from, common.getCurrentDate());
//                    if (DeviceInfo.saveDevice(from)){
                        replyMsg(ctx.channel(), from, protocalMsg.getMsgType());
                        logger.debug(from + " device start");
//                    }else
//                        errorMsg(ctx.channel(),from ,122);
                    break;
                case END:
                    ServerMain.dataMap.remove(from);
                    replyMsg(ctx.channel(), from, protocalMsg.getMsgType());
                    logger.debug(from + " device end");
                    break;
                case DATA:
                    replyMsg(ctx.channel(), from, protocalMsg.getMsgType());
                    logger.debug("data handling");
//                    DataTask.sleepDataHandle(json);
                    break;
                case ERROR:
                    errorMsg(ctx.channel(), "unknow",31);
                    logger.error("message type error");
                    ctx.channel().close();
                    break;

            }
        }catch (NullPointerException e){
            errorMsg(ctx.channel(), "unknow",31);
            logger.error("there are no this HardWare");
            ctx.channel().close();
        }


//        }else {
//            logger.error("device has not register");
//            ctx.channel().close();
//        }



//         if (StringUtils.startsWith(msg, "I")) {
//                String macAddress = msg.substring(7, 19);
//                if (DeviceInfo.queryDevice(macAddress)){
//                    //返回成功信息
//                    ServerMain.dataMap.put(macAddress, "");
//                    replyMsg(ctx.channel(), macAddress);
//                    logger.debug("HardwareAddress is : " + macAddress);
//                }else {
//                    ctx.channel().close();
//                    //返回错误信息
//                    errorMsg(ctx.channel(), macAddress, 63);
//                    logger.error("there are not this hardware");
//                    return;
//                }
//        }else {
//            errorMsg(ctx.channel(), "unknow", 31);
//            logger.error("there are no adapt type");
//            ctx.channel().close();
//        }


    }

    public static void replyMsg(Channel ctx, String message, int msgType){

        SocketDefaultMsg msg = new SocketDefaultMsg();
        msg.setFrom("server");
        msg.setTo(message);
        msg.setFlag(1);
        msg.setMsgType(msgType);

        JSONObject j = (JSONObject) JSON.toJSON(msg);
        String data = j.toJSONString() + "\r\n";
        logger.debug("reply message is " + data + "\n");
        writeAndFlush(ctx, data);

    }

    public static void errorMsg(Channel ctx, String message, int errorCode){

        ErrorMsg msg = new ErrorMsg();
        msg.setFrom("server");
        msg.setTo(message);
        msg.setFlag(0);
        msg.setMsgType(1);
        msg.setErrCode(errorCode);

        JSONObject j = (JSONObject) JSON.toJSON(msg);
        String data = j.toJSONString();
        writeAndFlush(ctx, data);

    }

    // 执行写操作
    private static void writeAndFlush (Channel ctx, String data){
        ByteBuf byteBuf = Unpooled.copiedBuffer((data).getBytes());
        ctx.writeAndFlush(byteBuf);
    }


}
