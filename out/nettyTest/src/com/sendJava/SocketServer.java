package com.sendJava;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.db.DBPool;
import com.protocol.Templates.SocketProtocol;
import com.protocol.Templates.rePlay;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by fadinglan on 2016/12/10.
 */
public class SocketServer {
    private static final String EOT = "\r\n";
    private SocketProtocol sleepdata_real = new SocketProtocol();
    private SocketProtocol sleepdata_cache = new SocketProtocol();

    private static String heartStr = new String();


    /**
     * 提交一个数据到框架
     *
     * @param ctx    收到数据的连接
     * @param packet 数据包
     */
    public static void submit(ChannelHandlerContext ctx, String packet) {


        JSONObject json;
        json = JSON.parseObject(packet);

        if (json.get("msgType").equals(0))
            replyHeartbeat(ctx.channel());

        else {
            if (json.getString("params").contains("deviceType")) {
                rePlay reRegister = new rePlay("server","hardware",1,1);
                sendMsg(ctx.channel(),reRegister);

            }else {
                SocketProtocol sleepData = saveSleepData(json);
                sleepDataHandle(sleepData);

            }
        }
    }


    public static SocketProtocol saveSleepData (JSONObject json){

        SocketProtocol protocol = new SocketProtocol();
        protocol.setCmd((int) json.get("cmd"));
        protocol.setMsgType((int) json.get("msgType"));
        protocol.setTo((String) json.get("to"));
        protocol.setFrom((String) json.get("from"));
        protocol.setParams((JSONArray) json.get("params"));

        return protocol;

    }

    public static void sleepDataHandle(SocketProtocol protocol){
        Object[] data = protocol.getParams().toArray();
        String deviceId = protocol.getFrom();
        String heart = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String startTime = df.format(new Date());

        if (heartStr == null || heartStr.isEmpty()) {
            heartStr = heartStr + heart;
        }
        else {
            heartStr = heartStr + heart;
        }

        for(Object s : data){
            heartStr += s + " ";
        }
        Map<String,String> fullStr = new HashMap<>();
        fullStr.put("heartRate",heartStr);
        fullStr.put("startTime",startTime);
        saveAlarmData(deviceId,fullStr);

    }


    /**
     * 发送数据
     *
     * @param re    协议包
     */
    public static void sendMsg (Channel ctx, rePlay re){

        JSONObject json = (JSONObject) JSON.toJSON(re);
        String data = json.toJSONString();
        writeAndFlush(ctx, data);

    }


    // 回复心跳包
    private static void replyHeartbeat (Channel ctx){
        SocketProtocol reHeart= new SocketProtocol("server","hardware",0);
        JSONObject reJson = (JSONObject) JSON.toJSON(reHeart);
        String data = reJson.toJSONString();
        writeAndFlush(ctx, data);

    }

    // 执行写操作
    private static void writeAndFlush (Channel ctx, String data){

        ByteBuf byteBuf = Unpooled.copiedBuffer((data).getBytes());
        ctx.writeAndFlush(byteBuf);

    }


    public static String convertToString (SocketProtocol protocol){

        return JSON.toJSONString(protocol);

    }

    public void save_real(SocketProtocol protocol){
        sleepdata_real.setParams(protocol.getParams());
        sleepdata_real.setFrom(protocol.getFrom());
    }


    public SocketProtocol saveToCache(String body) {

        JSONObject json;
        json = JSON.parseObject(body);
        SocketProtocol protocol = new SocketProtocol();
        protocol.setParams((JSONArray) json.get("params"));
        protocol.setCmd((int) json.get("cmd"));
        protocol.setMsgType((int) json.get("msgType"));
        protocol.setTo((String) json.get("to"));
        protocol.setTo((String) json.get("from"));
        return protocol;

    }


    /**
     *
     * @param fullKey
     * @param fullMap
     */
    public  static void saveAlarmData(String fullKey,Map fullMap){
        System.out.println("saveAlarmData");
        Connection con = DBPool.getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        if(query(fullKey,con)){
            update(fullKey,fullMap,con);

        }else{
            save(fullKey,fullMap,con);
        }

    }


    /**
     *
     * @param fullKey
     * @param con
     * @return
     * @throws NumberFormatException
     */
    public  static boolean query(String fullKey, Connection con) throws NumberFormatException {
        String querysql = "select * from sleep_data where deviceId = " + fullKey + "";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(querysql);
            rs = ps.executeQuery();
            con.commit();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            try {
                System.out.println("查询数据库异常。。。");
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // 关闭存储查询结果的ResultSet对象
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // 关闭负责执行SQL命令的prepareStatement对象
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        return false;
    }



    /**
     *
     * @param fullKey
     * @param fullMap
     * @param con
     * @throws NumberFormatException
     */
    private static void save(String fullKey, Map fullMap, Connection con) throws NumberFormatException {
        PreparedStatement ps = null;
        String save_sql = "insert into sleep_data (deviceId,heartRate,startTime) values(?,?,?)";
        try {
            ps = con.prepareStatement(save_sql);

            ps.setLong(1, Long.valueOf(fullKey));

            ps.setString(2, fullMap.get("heartRate").toString());
            ps.setString(3, fullMap.get("startTime").toString());

            ps.executeUpdate();

            con.commit();

        } catch (SQLException e) {
            try {
                System.out.println("插入数据库异常，正在进行回滚..");
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // 关闭负责执行SQL命令的prepareStatement对象
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            // 将Connection连接对象还给数据库连接池
            if (con != null ) {
                try {
                    con.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }
    }

    /**
     *
     * @param fullKey
     * @param fullMap
     * @param con
     */
    private static void update(String fullKey,Map fullMap, Connection con) {

        String update_sql ="update sleep_data set heartRate='"+fullMap.get("heartRate").toString()+"',moveFlag='"+1+"'"+" where deviceId='"+fullKey+"'";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(update_sql);
            ps.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            try {
                System.out.println("插入数据库异常，正在进行回滚..");
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {

            // 关闭负责执行SQL命令的prepareStatement对象
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // 将Connection连接对象还给数据库连接池
            if (con != null ) {
                try {
                    con.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }

    }



}