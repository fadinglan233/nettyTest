package tcp.handler.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tcp.handler.MsgListener;
import tcp.message.SocketMsg;

import io.netty.channel.Channel;
import tcp.message.impl.SocketDefaultMsg;
import tcp.mysql.DeviceInfo;
import tcp.netty.ServerMain;
import tcp.task.DataHandler;
import tcp.util.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by fadinglan on 2017/5/10.
 */
public class StartListener implements MsgListener {
    private static final Log logger = LogFactory.getLog(StartListener.class);

    @Override
    public void handlerMsg(Channel ctx, SocketMsg msg) {

        String deviceId = msg.getFrom();
        SocketMsg response;
        if (ServerMain.dataMap.containsKey(deviceId)){
            String time = ServerMain.dataMap.get(deviceId);
            if (time.equals("") || !common.isInTime(time)) {
                ServerMain.dataMap.put(deviceId, common.getCurrentDate());
                ServerMain.idMap.put(deviceId,-3);
                response = msg.makeResponse();
                DeviceInfo.saveDevice(deviceId);
                //将消息加入临时表
                logger.debug(deviceId + " device start");
            }else{
                response = msg.makeResponse();
            }
        }else {
            response = msg.errorResponse();
            logger.error("the data has not register");
            ctx.close();
        }

        common.replyMsg(ctx, response);
    }


}
