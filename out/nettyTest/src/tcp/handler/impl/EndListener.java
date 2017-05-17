package tcp.handler.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tcp.handler.MsgListener;
import tcp.message.SocketMsg;

import io.netty.channel.Channel;
import tcp.message.impl.SocketDefaultMsg;
import tcp.mysql.DeviceInfo;
import tcp.netty.ServerMain;
import tcp.util.common;

//import static tcp.handler.impl.DataListener.idList;

/**
 * Created by fadinglan on 2017/5/10.
 */
public class EndListener implements MsgListener{

    private static final Log logger = LogFactory.getLog(EndListener.class);
    @Override
    public void handlerMsg(Channel ctx, SocketDefaultMsg msg) {

        SocketMsg responseMsg = null;
        try {
            String deviceId = msg.getFrom();
            if (ServerMain.dataMap.containsKey(deviceId) || DeviceInfo.querySleepData(deviceId) != null) {
                if (common.checkDataStart(deviceId) || DeviceInfo.querySleepData(deviceId) != null) {
                logger.debug(deviceId + " device end from " + ServerMain.dataMap.get(deviceId) +
                        " to " + common.getCurrentDate() + "\n");

                responseMsg = msg.makeResponse();
                DeviceInfo.updateDate(deviceId,common.getCurrentDate(),ServerMain.dataMap.get(deviceId));
                DeviceInfo.deleteDevice(deviceId, ServerMain.dataMap.get(deviceId));
                ServerMain.dataMap.put(deviceId, common.getCurrentDate());
                ServerMain.idMap.remove(deviceId);
                }else {
                    responseMsg = msg.errorResponse();
                    logger.error(deviceId + "END ERROR cause has not start");
                }
            }else {
                responseMsg = msg.errorResponse();
                logger.error(deviceId + "END ERROR cause has not register");
            }
        }catch (Exception e){
            responseMsg = msg.errorResponse();
            logger.error("msgType ERROR");

        }
        common.replyMsg(ctx, responseMsg);
    }


}
