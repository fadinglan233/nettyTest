package tcp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fadinglan on 2017/5/8.
 */
public class common {

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
                if (Math.abs(maptime - nowTime) < 3600000)
                    return true;
                else
                    return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }

    }
}
