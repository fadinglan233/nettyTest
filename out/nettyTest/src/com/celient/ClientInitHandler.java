package com.celient;

/**
 * Created by fadinglan on 2016/12/12.
 */
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protocol.Templates.SendMessage;
import com.protocol.Templates.SocketProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;

public class ClientInitHandler extends ChannelInboundHandlerAdapter {
    private static Log logger = LogFactory.getLog(ClientInitHandler.class);
    private ByteBuf message;
    private int deviceNum;
    private static final int win = 10;

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
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("HelloClientIntHandler.channelActive");
        final ArrayList<String> arrayList = dataIn();
//		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        new Runnable() {
            Double[] params = new Double[win];
            byte[] req = null;
            String [] range = null;

            @Override
            public void run() {
                SendMessage protocol = new SendMessage();
                protocol.setFrom(String.valueOf(deviceNum));
                protocol.setTo("server");
                protocol.setMsgType(1);
                // TODO Auto-generated method stub

                for(int i = 1; i < arrayList.size()/win; i += win) {

                    for(int j = 0; j < 10; j++) {
                       params[j] = Double.parseDouble(arrayList.get(i+j));
                    }

                    protocol.setParams(params);
                    if (i == 1){
                        protocol.setCmd(44);//起始标志
                    }else if (i == arrayList.size() - 1){
                        protocol.setCmd(40);//结束标识
                    }else {
                        protocol.setCmd(42);
                    }



//                    String params = JSON.toJSONString(req.toString());


//                    JSONArray pa = JSON.parseArray(params);

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