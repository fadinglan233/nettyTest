package tcp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fadinglan on 2017/5/8.
 */
public class common {

    public static String getCurrentDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String registerTime = df.format(new Date());
        return registerTime;
    }
}
