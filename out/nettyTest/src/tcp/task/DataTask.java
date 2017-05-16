package tcp.task;

import com.alibaba.fastjson.JSONArray;
import tcp.message.impl.SocketDefaultMsg;
import tcp.model.SleepData;
import tcp.mysql.DeviceInfo;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by fadinglan on 2017/5/6.
 */
public class DataTask {

    private static ArrayList<SleepData> sleepDataListClone = new ArrayList<>();
    private static ArrayList<SleepData> sleepDataList = new ArrayList<>();

    public static void sleepDataHandle(SocketDefaultMsg protocalMsg){

        JSONArray params = protocalMsg.getParams();
        Object object = params.get(0);
        Map<String, JSONArray> sleepMap = (Map<String,JSONArray>) object;

        Object[] ob = sleepMap.get("rate").toArray();
        String data = "";
        for (Object o:ob) {
            data = data + o + " ";
        }

        String dataBefore = DeviceInfo.querySleepData(protocalMsg.getFrom())[1];
        if (dataBefore == null){
            DeviceInfo.updateDevice(protocalMsg.getFrom(), "" + data + " ");
        }else
            DeviceInfo.updateDevice(protocalMsg.getFrom(),  data);

//        SleepDataSave.insertInDB(protocalMsg.getFrom(), data);

//        saveSleepData(protocalMsg);
//
//        if (sleepDataList.size() == 15){
//            sleepDataListClone =  (ArrayList<SleepData>) sleepDataList.clone();
//            sleepDataList.clear();
//            SleepDataSave.insertInDB(protocalMsg.getFrom(), sleepDataListClone);
//
//        }

    }

//    public static SleepData saveSleepData (ProtocalMsg json){
//
//        SleepData sleepData = new SleepData();
//
//        JSONArray params = json.getParams();
//        Object object = (Object)params.get(0);
//        Map<String, JSONArray> sleepMap = (Map<String,JSONArray>) object;
//        Object[] ob = sleepMap.get("rate").toArray();
//
//        String data = "";
//        for (Object o:ob) {
//            data = data + o + " ";
//        }
//
//        sleepData.setDeviceId(json.getFrom());
//        sleepData.setHeartRate(data);
//        sleepDataList.add(sleepData);
//        return sleepData;
//
//    }
}
