package com.celient;

/**
 * Created by fadinglan on 2016/12/12.
 */
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocol.Templates.SocketProtocol;
import com.protocol.Templates.rePlay;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientInitHandler extends ChannelInboundHandlerAdapter {
    private static Log logger = LogFactory.getLog(ClientInitHandler.class);
    private ByteBuf message;
    private int deviceNum;

    public ClientInitHandler(int num) {
        super();
        this.deviceNum = num;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        // TODO Auto-generated method stub
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("HelloClientIntHandler.channelActive");
        final ArrayList<String> arrayList = dataIn();
//		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        new Runnable() {
            String reqStr = null;
            byte[] req = null;

            @Override
            public void run() {
                // TODO Auto-generated method stub
//                for (int j = 1; j < arrayList.size(); j++) {
//                    System.out.println("arrayList.size():" + arrayList.size());
//                    if (j == 1) {
//                        reqStr = "{[237,252][" + deviceNum
//                                + "][2][ 2016-3-29 12:36:21]["
//                                + arrayList.get(j)
//                                + "][20][10][0.000000][10]}236,239";
//                    } else if (j == arrayList.size() - 1) {
//                        reqStr = "{[237,252][" + deviceNum
//                                + "][2][ 2016-3-29 12:36:21]["
//                                + arrayList.get(j)
//                                + "][20][4][0.000000][10]}236,239";
//                    } else {
//                        reqStr = "{[237,252][" + deviceNum
//                                + "][2][ 2016-3-29 12:36:21]["
//                                + arrayList.get(j)
//                                + "][20][2][0.000000][10]}236,239";
//                    }
//                    req = reqStr.getBytes();
                for(int j = 0; j < 10; j++) {
                    reqStr = arrayList.get(j) + " ";
                    req = reqStr.getBytes();
                }

                    SocketProtocol protocol = new SocketProtocol();
                    JSONObject json;

//                    String params = JSON.toJSONString(req.toString());


//                    JSONArray pa = JSON.parseArray(params);
                    protocol.setFrom("fading");
                    protocol.setTo("server");
                    protocol.setMsgType(1);
                    protocol.setCmd(44);
//                    protocol.setParams(pa);

                    JSONObject Rejson = (JSONObject) JSON.toJSON(protocol);
                    String reString = Rejson.toJSONString();
                    ByteBuf byteBuf = Unpooled.copiedBuffer(reString.getBytes());
                    ctx.writeAndFlush(byteBuf);
                    ctx.flush();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }



            }
        }.run();
    }


    /**
     *
     * @return 返回数据arrayList
     */
    public static ArrayList<String> dataIn() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File("/Users/fadinglan/Downloads/20161014.txt");
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStrRed = new InputStreamReader(inputStream);
            BufferedReader bufRed = new BufferedReader(inputStrRed);

            // 读取一行
            String strLine = null;
            while ((strLine=bufRed.readLine())!= null) {
                arrayList.add(strLine);
            }
            inputStream.close();
            inputStrRed.close();
            bufRed.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
        return arrayList;
    }


}