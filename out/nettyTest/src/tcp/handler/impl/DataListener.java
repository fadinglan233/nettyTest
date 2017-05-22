package tcp.handler.impl;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tcp.handler.MsgListener;
import tcp.message.SocketMsg;

import io.netty.channel.Channel;
import tcp.message.impl.SocketDefaultMsg;
import tcp.mysql.DeviceInfo;
import tcp.netty.ServerMain;
import tcp.task.*;
import tcp.util.common;

import java.util.*;

/**
 * Created by fadinglan on 2017/5/10.
 */
public class DataListener implements MsgListener {

    private static final Log logger = LogFactory.getLog(DataListener.class);
    @Override
    public void handlerMsg(Channel ctx, SocketDefaultMsg msg) {

        try {
            String deviceId = msg.getFrom();
            Integer msgId = msg.getMsgID();
            Integer lastId = null;

            if (ServerMain.idMap.containsKey(deviceId))
                lastId = ServerMain.idMap.get(deviceId);
            else if (DeviceInfo.querySleepData(deviceId) != null){
                ServerMain.idMap.put(deviceId,-3);
                lastId = -3;
            }
            if (lastId != null){
                if (lastId == -3 || lastId != msgId){
                    ServerMain.idMap.put(deviceId,msgId);
                    ServerMain.dateQueue.offer(msg);
                    logger.debug("data Queue Size is " + ServerMain.dateQueue.size());
                }else if (Math.abs(lastId - msgId) > 20){
                    SocketMsg response = msg.pinMsg();
                    common.replyMsg(ctx, response);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("data msg id error");
        }
    }

    public void compensateData(int lastId, int currentId, String deviceId){
        int length = Math.abs(currentId - lastId);
        for (int i = 0; i < length; i ++){
            SocketDefaultMsg newMsg = new SocketDefaultMsg();
            Map<String, int[]> map = new HashMap<>();
            int[] data = new int[60];

            map.put("rate",data);
            newMsg.setFrom(deviceId);

            JSONArray jsonArray = new JSONArray();
            jsonArray.add(map);
            newMsg.setParams(jsonArray);
//            ServerMain.dateQueue.offer(newMsg);
        }

    }

}
