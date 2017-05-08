package tcp.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import tcp.model.SleepData;
import tcp.mysql.SleepDataSave;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by fadinglan on 2017/5/6.
 */
public class DataTask {

    private static ArrayList<SleepData> sleepDataListClone = new ArrayList<>();
    private static ArrayList<SleepData> sleepDataList = new ArrayList<>();

    public static void sleepDataHandle(JSONObject jsonObject){

        saveSleepData(jsonObject);

        if (sleepDataList.size() == 15){
            sleepDataListClone =  (ArrayList<SleepData>) sleepDataList.clone();
            sleepDataList.clear();
            SleepDataSave.insertInDB(jsonObject.getString("from"), sleepDataListClone);

        }

    }

    public static SleepData saveSleepData (JSONObject json){

        SleepData sleepData = new SleepData();

        JSONArray params = (JSONArray) json.get("params");
        Object object = (Object)params.get(0);
        Map<String, JSONArray> sleepMap = (Map<String,JSONArray>) object;
        Object[] ob = sleepMap.get("rate").toArray();

        String data = "";
        for (Object o:ob) {
            data = data + o + " ";
        }

        sleepData.setDeviceId((String) json.get("from"));
        sleepData.setHeartRate(data);
        sleepDataList.add(sleepData);
        return sleepData;

    }
}
