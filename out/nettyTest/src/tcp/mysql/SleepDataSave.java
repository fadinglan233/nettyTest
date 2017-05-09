//package tcp.mysql;
//
//import tcp.model.SleepData;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
///**
// * Created by fadinglan on 2017/4/20.
// */
//public class SleepDataSave {
//
//
//    public static void insertInDB(String deviceId, String sleepData){
//
//        System.out.println("insert in DB ========");
//
//        Connection con = DBPool.getConnection();
//        try {
//            con.setAutoCommit(false);
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//
////        SleepData sleepData = new SleepData();
////
////        for (SleepData data : protocolList){
////            sleepData.setHeartRate(sleepData.getHeartRate() + data.getHeartRate());
////        }
////
////        SleepData sleepDataQuery = queryDB(sleepData.getDeviceId(), con);
////
////
////        sleepData.setHeartRate(sleepData.getHeartRate() + sleepDataQuery.getHeartRate());
//        updateSleepData(sleepData, deviceId, con);
//
//
//
//
//
//    }
//
//    private static SleepData queryDB(String deviceId, Connection con){
//
//        String sql = "select * from sleep_data where vc_device_id = '" + deviceId + "'";
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        SleepData sleepData = new SleepData();
//        try {
//            ps = con.prepareStatement(sql);
//            rs = ps.executeQuery();
//            con.commit();
//            while (rs.next()){
//
//                sleepData.setDeviceId(rs.getString("deviceId"));
//                sleepData.setHeartRate(rs.getString("heartRate"));
//            }
//        }catch (SQLException e){
//            try {
//                System.out.println("查询数据库异常===进行事务回滚");
//                con.rollback();
//            }catch (SQLException e1){
//                e1.printStackTrace();
//            }
//            e.printStackTrace();
//        }finally {
//            if (rs != null){
//                try {
//                    rs.close();
//                }catch (SQLException e){
//                    e.printStackTrace();
//                }
//            }
//
//            if (ps != null){
//                try {
//                    ps.close();
//                }catch (SQLException e){
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//        return sleepData;
//    }
//
//    /**
//     * 保存睡眠数据
//     * @param data
//     * @param con
//     */
//    public static void saveSleepData(SleepData data, Connection con){
//
//        String sql = "insert into sleep_data(vc_device_id,mt_heart_rate,t_start_time) value(?,?,?)";
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            ps = con.prepareStatement(sql);
//            ps.setString(1, data.getDeviceId());
//            ps.setString(2, data.getHeartRate());
//            ps.setString(3, data.getStartTime());
//            ps.executeUpdate();
//            con.commit();
//        }catch (SQLException e){
//            try {
//                System.out.println("插入数据库异常====事务回滚");
//                con.rollback();
//            }catch (SQLException e1){
//                e1.printStackTrace();
//            }
//            e.printStackTrace();
//        }finally {
//            if (ps != null){
//                try {
//                    ps.close();
//                }catch (SQLException e){
//                    e.printStackTrace();
//                }
//            }
//            if (con != null){
//                try {
//                    con.close();
//                }catch (SQLException e){
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    public static void updateSleepData(String data, String deviceId, Connection con){
//        String sql = "update sleep_data set mt_heart_rate ='" + data + "' WHERE vc_device_id = '" + deviceId + "'";
//        PreparedStatement ps = null;
//        Connection        con      = null;
//
//        try {
//
//            ps = con.prepareStatement(sql);
//            ps.executeUpdate();
//            con.commit();
//        }catch (SQLException e){
//            try {
//                System.out.println("插入数据库异常=========");
//                con.rollback();
//            }catch (SQLException e1){
//                e1.printStackTrace();
//            }
//            e.printStackTrace();
//        }finally {
//            if (ps != null){
//                try {
//                    ps.close();
//                }catch (SQLException e){
//                    e.printStackTrace();
//                }
//            }
//
//            if (con != null){
//                try {
//                    con.close();
//                }catch (SQLException e){
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
