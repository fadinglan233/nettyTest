package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fadinglan on 2017/4/21.
 */
public class DeviceRegister {

    public static boolean registerDevice(String deviceId){

        Connection con = DBPool.getConnection();

        boolean result = false;
        try {
            con.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
        }

        if (queryDevice(deviceId, con)){
            saveDevice(deviceId, con);
            return true;
        }
//            updateDevice()

        return false;
    }

    public static boolean queryDevice(String deviceId, Connection con){

        String sql = "select * from device where deviceId = '" + deviceId + "'";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            con.commit();
            if (rs.next())
                return true;
        }catch (SQLException e){
            try {
                System.out.println("查询设备表异常");
                con.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }

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
        return false;
    }

    public static void saveDevice(String deviceId, Connection con){

        PreparedStatement ps = null;
        String sql = "insert into device (deviceId,regTime) values(?,?)";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String registerTime = df.format(new Date());
        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, Long.valueOf(deviceId));
            ps.setString(2, registerTime);
        }catch (SQLException e){
            try {
                System.out.println("插入数据库异常");
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
