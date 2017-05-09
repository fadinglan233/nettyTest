package tcp.netty;

import io.netty.channel.Channel;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fadinglan on 2017/5/5.
 */
public class ServerMain {

    //线程安全map，处理服务器hold住客户端连接的channel
    public static ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    //线程安全map，存储传输数据开始的时间
    public static ConcurrentHashMap<String, String> dataMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        int port = 8000;
        if (args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                System.out.println("know exception on server: " + e.getMessage());
            }
        }

        try {
            new ServerConfig().bind(port);
        }catch (IOException e1){
            System.out.println("");
        }

    }
}


