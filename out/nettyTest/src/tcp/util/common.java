package tcp.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tcp.netty.ServerMain;
import tcp.task.ServerTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fadinglan on 2017/5/8.
 */
public class common {

    private static final Log logger = LogFactory.getLog(common.class);

    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String registerTime = df.format(new Date());
        return registerTime;
    }

    public static boolean isInTime(String sourceTime) {

        String curTime = getCurrentDate();

        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":") || sourceTime.length() < 3) {
            return false;
        }

        String[] args = sourceTime.split(" ");
        String[] currents = curTime.split(" ");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {

            if (args[1].equals("00:00:00"))     args[1] = "24:00:00";
            if (currents[1].equals("00:00:00"))     currents[1] = "24:00:00";

            long nowTime = sdf.parse(currents[1]).getTime();

            long maptime = sdf.parse(args[1]).getTime();


            if (args[0].equals(currents[0]))   return true;
            else {
                if (Math.abs(maptime - nowTime) < 1800000)
                    return true;
                else
                    return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }

    }

    public static void replyMsg(Channel ctx, Object obj){
        if (obj == null)    return;
        JSONObject j = (JSONObject) JSON.toJSON(obj);
        String data = j.toJSONString() + "\r\n";
        logger.debug("reply message is [" + data + "]");
        writeAndFlush(ctx, data);
    }

    // 执行写操作
    private static void writeAndFlush (Channel ctx, String data){
        ByteBuf byteBuf = Unpooled.copiedBuffer((data).getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    public static boolean checkDataStart(String deviceId){

        if (ServerMain.dataMap.get(deviceId).length() < 3) {
            return false;
        }
        return true;
    }
}
