package socket.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import socket.SocketServer;
import socket.exception.SocketException;
import socket.exception.fatal.SocketFatalException;
import socket.exception.normal.SocketNormalException;
import socket.io.term.SocketDefaultIOTerm;
import socket.routing.item.SocketRoutingItem;

/**
 * Netty TCP服务函数
 * <p>
 * Created by ZFly on 2017/4/25.
 */
public class SocketTCPHandler extends ChannelInboundHandlerAdapter {

    private static final Log logger = LogFactory.getLog(SocketTCPHandler.class);
    private final SocketServer context;

    public SocketTCPHandler(SocketServer context) {
        this.context = context;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress().toString() + "has connected");
        try {
            context.getRouting().register(
                    new SocketDefaultIOTerm() {{
                        setChannel(ctx.channel());
                        setConnectType("TCP");
                        setIoTag(ctx.channel().remoteAddress().toString());
                    }});
        } catch (SocketNormalException e) {
            final ByteBuf byteBuf = Unpooled.copiedBuffer((e.getMessage() + context.getConfig().getEOTs().get(0)).getBytes());
            System.out.println(byteBuf.toString());
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        context.getRouting().unRegister(
                new SocketDefaultIOTerm() {{
                    setIoTag(ctx.channel().remoteAddress().toString());
                }});
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg.toString());
        try {
            context.getScheduler().submit((String) msg, ctx.channel().remoteAddress().toString(), "TCP");
        } catch (SocketFatalException e) {
            final ByteBuf byteBuf = Unpooled.copiedBuffer((e.getMessage() + context.getConfig().getEOTs().get(0)).getBytes());
            ctx.writeAndFlush(byteBuf);
            final SocketRoutingItem item = context.getRouting().getItem(ctx.channel().remoteAddress().toString());
            if (item != null)
                item.close();
            else
                ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof SocketException) {
            final ByteBuf byteBuf = Unpooled.copiedBuffer((cause.getMessage() + context.getConfig().getEOTs().get(0)).getBytes());
            ctx.writeAndFlush(byteBuf);
            logger.error(cause.getMessage());
        } else {
            logger.error(cause.getClass().getSimpleName() + ": ", cause);
        }
        final SocketRoutingItem item = context.getRouting().getItem(ctx.channel().remoteAddress().toString());
        if (item != null)
            item.close();
        else
            ctx.close();
    }
}
