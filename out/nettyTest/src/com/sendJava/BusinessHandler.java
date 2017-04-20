package com.sendJava;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.protocol.Templates.SocketProtocol;
import com.protocol.Templates.rePlay;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by fadinglan on 2016/12/10.
 */

    public class BusinessHandler extends ChannelHandlerAdapter {

        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            SocketRegistry.register("Tmp_" + ctx.channel().id(), ctx.channel());

        }
        @Override

        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            new Runnable(){
                public void run() {
                    SocketServer.submit(ctx,(String)msg);
                }

            }.run();



        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception{

            super.channelInactive(ctx);
        }


        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        //发生异常时 关闭ChannelHandlerContext ，释放资源
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);

            Channel incoming = ctx.channel();
            if (!incoming.isActive())
                System.out.println("SimpleClient:" + incoming.remoteAddress() + "异常");

            cause.printStackTrace();

            ctx.close();
        }

}
