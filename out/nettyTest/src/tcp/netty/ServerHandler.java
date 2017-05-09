package tcp.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tcp.register.ChannelRegister;
import tcp.task.ServerTask;

import java.net.InetSocketAddress;

/**
 * Created by fadinglan on 2017/5/5.
 */
public class ServerHandler extends ChannelHandlerAdapter {
    private static final Log logger = LogFactory.getLog(ServerHandler.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        //注册通道
        if (channel != null){
            ChannelRegister.addChannel(ctx,channel);
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        String hostValue = getHostValue(ctx);
        String data = (String)msg;
        logger.debug("Server receive message from: [" + ctx.channel().remoteAddress() + "] message is : " + msg + "\n");

        //如果注册表中没有此客户端，强制下线
        if (!ServerMain.channelMap.containsKey(hostValue)){
            ServerMain.channelMap.remove(hostValue);
            ctx.channel().close();
        }else {
            ServerTask.taskSubmit(ctx, (String)msg);
        }

    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        String hostValue = getHostValue(ctx);
        //移除无用连接
        try {
            ServerMain.channelMap.remove(hostValue);
        }catch (Exception e2){

        }
        logger.error(ctx.channel().remoteAddress() + "has disconnected");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        logger.error(ctx.channel().remoteAddress() + "has closed!");
    }

    public String getHostValue(ChannelHandlerContext ctx){
        String host = ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress().getHostAddress();
        int port = ((InetSocketAddress) ctx.channel().remoteAddress()).getPort();

        return host + ":" + port;
    }
}
