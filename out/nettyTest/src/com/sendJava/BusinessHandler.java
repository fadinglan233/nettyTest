package com.sendJava;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.db.DBPool;
import com.protocol.Templates.SocketProtocol;
import com.protocol.Templates.rePlay;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.string.StringDecoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fadinglan on 2016/12/10.
 */

    public class BusinessHandler extends ChannelInboundHandlerAdapter {

        public void channelActive(ChannelHandlerContext ctx) throws Exception {

//        //通知已经连接至客户端
            String re = "connect successful";
            System.out.print(re);
//            ByteBuf byteBuf = Unpooled.buffer(re.getBytes().length);
//            byteBuf.writeBytes(re.getBytes());
//            ctx.writeAndFlush(byteBuf);

        }
        @Override

        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            SocketServer.submit(ctx, (String) msg);

        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        }

}
