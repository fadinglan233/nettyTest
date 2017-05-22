package tcp.task;

import com.alibaba.fastjson.JSONArray;
import tcp.message.impl.SocketDefaultMsg;
import tcp.mysql.DeviceInfo;
import tcp.netty.ServerMain;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by fadinglan on 2017/5/10.
 */
public class DataHandler implements Runnable {

    private final BlockingQueue<SocketDefaultMsg> blockingQueue;

    public DataHandler(BlockingQueue<SocketDefaultMsg> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {

        System.out.println("saveing");
        while (true){

             if (blockingQueue != null && !blockingQueue.isEmpty()){
                SocketDefaultMsg msg = ServerMain.dateQueue.poll();
                JSONArray params = msg.getParams();
                Object object = params.get(0);
                Map<String, JSONArray> sleepMap = (Map<String,JSONArray>) object;

                try {

                    Object[] ob = sleepMap.get("rate").toArray();
                    String data = "";
                    for (Object o:ob) {
                        data = data + o + " ";
                    }
                    String dataBefore = "";
                    if(DeviceInfo.querySleepData(msg.getFrom())[1] != null){
                        dataBefore = DeviceInfo.querySleepData(msg.getFrom())[1];
                    }

//                    System.out.println("update device is : " + msg.getFrom() + "sleepData is : " + dataBefore + data);

                    DeviceInfo.updateDevice(msg.getFrom(),  dataBefore + data);
                }catch (ClassCastException e){
                    System.err.println("rate type error");
                }

            }
        }

    }
}
