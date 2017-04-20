package com.celient;

/**
 * Created by fadinglan on 2016/12/12.
 */
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
    public void connect(String host, int port, int num) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final int number = num;
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    //编码

                    //
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new ClientInitHandler(number));
                }
            });

            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        final ExecutorService executor = Executors.newCachedThreadPool();

        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                for(int i = 35; i < 41; i++){
                    int tempnum = i;
                    executor.submit(new TestData(tempnum));

                }

            }
        }, 0, 100, TimeUnit.HOURS);
    }
}
