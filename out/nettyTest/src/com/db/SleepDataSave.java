package com.db;

import com.protocol.Templates.SocketProtocol;
import model.SleepData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by fadinglan on 2017/4/20.
 */
public class SleepDataSave {


    public static void insertInDB(List<SocketProtocol> protocolList){

        System.out.println("insert in DB ========");

        SocketProtocol protocol = new SocketProtocol();

        Connection con = DBPool.getConnection();
        try {
            con.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
        }

        SleepData sleepData = new SleepData();

        String startTime = "";
        sleepData.setDeviceId(Long.valueOf(protocolList.get(0).getFrom()));
        sleepData.setHeartRate("");


        for (SocketProtocol sp : protocolList){
            Object[] ob =  sp.getParams().toArray();
            String heartRate = "";
            for (Object o:ob) {
                heartRate = heartRate + " " + o;
            }
            sleepData.setHeartRate(sleepData.getHeartRate() + " " + heartRate);
        }
        SleepData sleepDataQuery = queryDB(sleepData.getDeviceId(), con);

        if (sleepDataQuery.getDeviceId() != sleepData.getDeviceId()){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            startTime = df.format(new Date());
            sleepData.setStartTime(startTime);
            saveSleepData(sleepData, con);
        }else {
            sleepData.setHeartRate(sleepData.getHeartRate() + " " + sleepDataQuery.getHeartRate());
            updateSleepData(sleepData, con);

        }




    }

    private static SleepData queryDB(Long deviceId, Connection con){

        String sql = "select * from sleep_data where deviceId = '" + deviceId + "'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        SleepData sleepData = new SleepData();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            con.commit();
            while (rs.next()){

                sleepData.setDeviceId(rs.getLong("deviceId"));
                sleepData.setHeartRate(rs.getString("heartRate"));
            }
        }catch (SQLException e){
            try {
                System.out.println("查询数据库异常===进行事务回滚");
                con.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            if (rs != null){
                try {
                    rs.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if (ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }

        return sleepData;
    }

    /**
     * 保存睡眠数据
     * @param data
     * @param con
     */
    public static void saveSleepData(SleepData data, Connection con){

        String sql = "insert into sleep_data(deviceId,heartRate,startTime) value(?,?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, data.getDeviceId());
            ps.setString(2, data.getHeartRate());
            ps.setString(3, data.getStartTime());
            ps.executeUpdate();
            con.commit();
        }catch (SQLException e){
            try {
                System.out.println("插入数据库异常====事务回滚");
                con.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            if (ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }


    public static void updateSleepData(SleepData data, Connection con){
        String sql = "update sleep_data set heartRate ='" + data.getHeartRate() + "'";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            con.commit();
        }catch (SQLException e){
            try {
                System.out.println("插入数据库异常=========");
                con.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            if (ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
