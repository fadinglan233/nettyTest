package tcp.register;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import tcp.netty.ServerMain;

import java.net.InetSocketAddress;

/**
 * Created by fadinglan on 2017/5/5.
 */
public class ChannelRegister {

    public static void addChannel(ChannelHandlerContext ctx, Channel channel){
        String host = ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress().getHostAddress();
        int port = ((InetSocketAddress) ctx.channel().remoteAddress()).getPort();
        //将IP和host组装起来，放入map中，用于管理多个Client
        ServerMain.channelMap.put(host + ":" + port, channel);

//        System.out.println("connect client is: " );
        System.out.println("current register map is" + ServerMain.channelMap);
    }
}
