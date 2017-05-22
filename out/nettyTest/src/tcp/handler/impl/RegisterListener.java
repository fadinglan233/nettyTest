package tcp.handler.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tcp.handler.MsgListener;
import tcp.message.SocketMsg;

import io.netty.channel.Channel;
import tcp.message.impl.SocketDefaultMsg;
import tcp.mysql.DeviceInfo;
import tcp.netty.ServerMain;
import tcp.task.ServerTask;
import tcp.util.common;

/**
 * Created by fadinglan on 2017/5/10.
 */
public class RegisterListener implements MsgListener {

    private static final Log logger = LogFactory.getLog(RegisterListener.class);

    @Override
    public void handlerMsg(Channel ctx, SocketDefaultMsg msg) {

        String from = msg.getFrom();
        SocketMsg response;

        if (ServerMain.dataMap.containsKey(from)){
            response = msg.makeResponse();
        }else {
            if (DeviceInfo.queryDevice(from)){
                String[] result = DeviceInfo.querySleepData(from);
                if (result == null) ServerMain.dataMap.put(from, "");
                else ServerMain.dataMap.put(from,result[0]);

                //返回成功信息
                response = msg.makeResponse();
                logger.debug("HardwareAddress : [" + from + "] register successful! time is :" + ServerMain.dataMap.get(from));
            }else {
                //返回错误信息
                response = msg.errorResponse();
                logger.error("the hardWare are not ours");
                ctx.close();
            }

        }

        common.replyMsg(ctx, response);

    }
}
