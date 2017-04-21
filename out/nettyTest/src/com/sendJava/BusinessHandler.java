package com.sendJava;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.db.DeviceRegister;
import com.db.SleepDataSave;
import com.protocol.Templates.SocketProtocol;
import com.protocol.Templates.rePlay;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;

/**
 * Created by fadinglan on 2016/12/10.
 */

    public class BusinessHandler extends ChannelHandlerAdapter {


    private ArrayList<SocketProtocol> sleepDataListClone = new ArrayList<>();
    private ArrayList<SocketProtocol> sleepDataList = new ArrayList<>();
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            SocketRegistry.register("Tmp_" + ctx.channel().id(), ctx.channel());

        }
        @Override

        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            final String packet = (String)msg;
            new Runnable(){
                public void run() {
                    JSONObject json;
                    try {
                        json = JSON.parseObject(packet);
                        if (json.get("msgType").equals(0))
                            replyHeartbeat(ctx.channel());

                        else {
                            Integer cmd = json.getInteger("cmd");
                            switch (cmd){
                                case 64:
//                                    rePlay reRegister = new rePlay("server","hardware",1,1);
//                                    sendMsg(ctx.channel(),reRegister);
                                    deviceRegister(ctx.channel(), json);
                                    break;
                                case 32:
                                    SocketProtocol sleepData = saveSleepData(json);
                                    sleepDataHandle(sleepData);
                                    break;
                                default:break;
                            }

                        }
                    }catch (JSONException e){
                        System.out.println("传入消息JSON格式错误");
                        e.printStackTrace();

                    }
                }

            }.run();



        }


    public SocketProtocol saveSleepData (JSONObject json){

        SocketProtocol protocol = new SocketProtocol();
        protocol.setCmd((int) json.get("cmd"));
        protocol.setMsgType((int) json.get("msgType"));
        protocol.setTo((String) json.get("to"));
        protocol.setFrom((String) json.get("from"));
        protocol.setParams((JSONArray) json.get("params"));

        sleepDataList.add(protocol);
        return protocol;

    }

    public void sleepDataHandle(SocketProtocol protocol){

        System.out.println(protocol.getFrom() + "Thread" + Thread.currentThread().getName() + "ing====");

        String from = protocol.getFrom();
        String data = protocol.getParams().toJSONString();

//        sleepDataMap.put(from, data);

        if (sleepDataList.size() == 15){
            sleepDataListClone =  (ArrayList<SocketProtocol>) sleepDataList.clone();
            sleepDataList.clear();
            SleepDataSave.insertInDB(sleepDataListClone);

        }


    }




    public void deviceRegister(Channel ctx, JSONObject json){
        try {
            String deviceId = (String) json.get("from");
            rePlay reRegister = new rePlay();
            reRegister.setFrom((String)json.get("to"));
            reRegister.setTo((String)json.get("from"));
            reRegister.setMsgType(1);
            if (DeviceRegister.registerDevice(deviceId)){
                reRegister.setFlag(1);

            }else {
                reRegister.setFlag(0);

            }
            sendMsg(ctx,reRegister);
        }catch (NumberFormatException e){
            System.out.println("格式转换错误");
            e.printStackTrace();
        }
    }

    /**
     * 发送数据
     *
     * @param re    协议包
     */
    public void sendMsg (Channel ctx, rePlay re){

        JSONObject json = (JSONObject) JSON.toJSON(re);
        String data = json.toJSONString();
        writeAndFlush(ctx, data);

    }

    // 回复心跳包
    private void replyHeartbeat (Channel ctx){
        SocketProtocol reHeart= new SocketProtocol("server","hardware",0);
        JSONObject reJson = (JSONObject) JSON.toJSON(reHeart);
        String data = reJson.toJSONString();
        writeAndFlush(ctx, data);

    }

    // 执行写操作
    private void writeAndFlush (Channel ctx, String data){

        ByteBuf byteBuf = Unpooled.copiedBuffer((data).getBytes());
        ctx.writeAndFlush(byteBuf);

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
