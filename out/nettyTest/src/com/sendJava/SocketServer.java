//package com.sendJava;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.JSONObject;
//import com.db.DBPool;
//import com.db.SleepDataSave;
//import com.protocol.Templates.SocketProtocol;
//import com.protocol.Templates.rePlay;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.*;
//import model.SleepData;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//
///**
// * Created by fadinglan on 2016/12/10.
// */
//public class SocketServer {
//    private static final String EOT = "\r\n";
//
//    private static ArrayList<SocketProtocol> sleepDataListClone = new ArrayList<>();
//    private static ArrayList<SocketProtocol> sleepDataList = new ArrayList<>();
//
//    /**
//     * 提交一个数据到框架
//     *
//     * @param ctx    收到数据的连接
//     * @param packet 数据包
//     */
//    public static void submit(ChannelHandlerContext ctx, String packet) {
//
//
//        JSONObject json;
//        try {
//            json = JSON.parseObject(packet);
//            if (json.get("msgType").equals(0))
//                replyHeartbeat(ctx.channel());
//
//            else {
//                Integer cmd = json.getInteger("cmd");
//                switch (cmd){
//                    case 64:
//                        rePlay reRegister = new rePlay("server","hardware",1,1);
//                        sendMsg(ctx.channel(),reRegister);
//                        break;
//                    case 32:
//                        SocketProtocol sleepData = saveSleepData(json);
//                        sleepDataHandle(sleepData);
//                        break;
//                    default:break;
//                }
//
//            }
//        }catch (JSONException e){
//            System.out.println("传入消息JSON格式错误");
//            e.printStackTrace();
//
//        }
//
//    }
//
//
//    public static SocketProtocol saveSleepData (JSONObject json){
//
//        SocketProtocol protocol = new SocketProtocol();
//        protocol.setCmd((int) json.get("cmd"));
//        protocol.setMsgType((int) json.get("msgType"));
//        protocol.setTo((String) json.get("to"));
//        protocol.setFrom((String) json.get("from"));
//        protocol.setParams((JSONArray) json.get("params"));
//
//        sleepDataList.add(protocol);
//        return protocol;
//
//    }
//
//    public static void sleepDataHandle(SocketProtocol protocol){
//
//        System.out.println(protocol.getFrom() + "Thread" + Thread.currentThread().getName() + "ing====");
//
//        String from = protocol.getFrom();
//        String data = protocol.getParams().toJSONString();
//
////        sleepDataMap.put(from, data);
//
//        if (sleepDataList.size() == 15){
//            sleepDataListClone =  (ArrayList<SocketProtocol>) sleepDataList.clone();
//            sleepDataList.clear();
//            SleepDataSave.insertInDB(sleepDataListClone);
//
//        }
//
//
//    }
//
//    /**
//     * 发送数据
//     *
//     * @param re    协议包
//     */
//    public static void sendMsg (Channel ctx, rePlay re){
//
//        JSONObject json = (JSONObject) JSON.toJSON(re);
//        String data = json.toJSONString();
//        writeAndFlush(ctx, data);
//
//    }
//
//    // 回复心跳包
//    private static void replyHeartbeat (Channel ctx){
//        SocketProtocol reHeart= new SocketProtocol("server","hardware",0);
//        JSONObject reJson = (JSONObject) JSON.toJSON(reHeart);
//        String data = reJson.toJSONString();
//        writeAndFlush(ctx, data);
//
//    }
//
//    // 执行写操作
//    private static void writeAndFlush (Channel ctx, String data){
//
//        ByteBuf byteBuf = Unpooled.copiedBuffer((data).getBytes());
//        ctx.writeAndFlush(byteBuf);
//
//    }
//
//
//
//
//
//}