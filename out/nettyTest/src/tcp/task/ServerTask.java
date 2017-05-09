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
                if (DeviceInfo.queryDevice(from)){
                    //返回成功信息
                    ServerMain.dataMap.put(from, "");
                    replyMsg(ctx.channel(), from, protocalMsg.getMsgType());
                    logger.debug("HardwareAddress : [" + from + "] register successful!");
                    return;
                }else {
//                    ctx.channel().close();
                    //返回错误信息
                    errorMsg(ctx.channel(), from, protocalMsg.getMsgType());
                    logger.error("the hardWare are not ours");
                    return;
                }
            }else {
                if (ServerMain.dataMap.containsKey(from)) {
                    switch (msgType){
                        case START:
                            if (!common.isInTime(ServerMain.dataMap.get(from))){
                                ServerMain.dataMap.put(from, common.getCurrentDate());
                                replyMsg(ctx.channel(), from, protocalMsg.getMsgType());
                                if (DeviceInfo.saveDevice(from)){
                                    //将消息加入临时表
                                    logger.debug(from + " device start");
                                }else {
                                    errorMsg(ctx.channel(), from, protocalMsg.getMsgType());
//                                    ctx.channel().close();
                                }
                            }else
                                replyMsg(ctx.channel(), from, protocalMsg.getMsgType());
                            break;
                        case END:
                            if (checkDataStart(ctx.channel(),from, protocalMsg.getMsgType()) || DeviceInfo.querySleepData(from) != null){
                                logger.debug(from + " device end from " + ServerMain.dataMap.get(from) +
                                        " to " + common.getCurrentDate() + "\n");
                                replyMsg(ctx.channel(), from, protocalMsg.getMsgType());
                                DeviceInfo.deleteDevice(from);
                                ServerMain.dataMap.put(from, "");
                            }else {
                                errorMsg(ctx.channel(), from, protocalMsg.getMsgType());
//                                ctx.close();
                            }
                            break;
                        case DATA:
                            if (checkDataStart(ctx.channel(),from, protocalMsg.getMsgType()) || DeviceInfo.querySleepData(from) != null){
                                replyMsg(ctx.channel(), from, protocalMsg.getMsgType());
                                if (DataTask.sleepDataHandle(protocalMsg)){
                                    logger.debug("data handling");
                                }else {
                                    errorMsg(ctx.channel(), from, protocalMsg.getMsgType());
//                                    ctx.channel().close();
                                }
                            }else {
                                errorMsg(ctx.channel(), from, protocalMsg.getMsgType());
//                                ctx.close();
                            }
                            break;
                        case ERROR:
                            errorMsg(ctx.channel(), "unknow",4);
                            logger.error("message type error");
                            ctx.channel().close();
                            break;

                    }

                }else {
                    ctx.channel().close();
                    logger.error("device has no register");
                }
            }

        }catch (NullPointerException e){
            errorMsg(ctx.channel(), "unknow",4);
            logger.error("there are no adapt msgType to HardWare");
            ctx.channel().close();
        }





    }

    public static boolean checkDataStart(Channel ctx, String deviceId, int msgType){


        if (ServerMain.dataMap.get(deviceId).length() < 3) {
            return false;
        }
        return true;
    }


    public static void replyMsg(Channel ctx, String message, int msgType){

        SocketDefaultMsg msg = new SocketDefaultMsg();
        msg.setFrom("server");
        msg.setTo(message);
        msg.setFlag(1);
        msg.setMsgType(msgType);

        JSONObject j = (JSONObject) JSON.toJSON(msg);
        String data = j.toJSONString() + "\r\n";
        logger.debug("reply message is [" + data + "]\n");
        writeAndFlush(ctx, data);

    }

    public static void errorMsg(Channel ctx, String message, int errorCode){

        ErrorMsg msg = new ErrorMsg();
        msg.setFrom("server");
        msg.setTo(message);
        msg.setFlag(0);
        msg.setMsgType(errorCode);
        msg.setErrCode(errorCode * 100 +22);

        JSONObject j = (JSONObject) JSON.toJSON(msg);
        String data = j.toJSONString();
        logger.error("handle device : + " + message  + "is error : [" + data + "]\n");
        writeAndFlush(ctx, data);

    }

    // 执行写操作
    private static void writeAndFlush (Channel ctx, String data){
        ByteBuf byteBuf = Unpooled.copiedBuffer((data).getBytes());
        ctx.writeAndFlush(byteBuf);
    }


}
