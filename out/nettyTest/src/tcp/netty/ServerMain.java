package tcp.netty;

import io.netty.channel.Channel;
import tcp.message.impl.SocketDefaultMsg;
import tcp.task.DataHandler;
//import tcp.task.DataHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by fadinglan on 2017/5/5.
 */
public class ServerMain {

    //线程安全map，处理服务器hold住客户端连接的channel
    public static ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    public static BlockingQueue<SocketDefaultMsg> dateQueue = new LinkedBlockingDeque<>(100);

    public static List<Integer> idList = new ArrayList<>();
    //线程安全map，存储传输数据开始的时间
    public static ConcurrentHashMap<String, String> dataMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String,Integer> idMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        int port = 8000;
        DataHandler dataHandler = new DataHandler(dateQueue);
        if (args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                System.out.println("know exception on server: " + e.getMessage());
            }
        }

        try {
            new Thread(dataHandler).start();
            new ServerConfig().bind(port);
        }catch (IOException e1){
            System.out.println("");
        }
    }
}


