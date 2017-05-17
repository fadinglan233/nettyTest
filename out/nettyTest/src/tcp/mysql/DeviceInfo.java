package tcp.mysql;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tcp.netty.ServerMain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fadinglan on 2017/5/6.
 */
public class DeviceInfo {

    private static final Log logger = LogFactory.getLog(DeviceInfo.class);


    public static boolean queryDevice(String deviceId){

        Connection        con      = null;
        PreparedStatement ps        = null;
        ResultSet         rs        = null;
        String[] result = new String[2];

        String sql = "select * from device_info where vc_device_id = '" + deviceId + "'";

        try {
            con = DBPool.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            con.commit();
            if (rs.next()){
                return true;
            }

        }catch (SQLException e){
            logger.error("check device sql error" , e);
        }finally {
            DBPool.release(con,ps,rs);
        }
        return false;
    }

    public static String[] querySleepData(String deviceId){

        Connection        con      = null;
        PreparedStatement ps        = null;
        ResultSet         rs        = null;

        String[] sleepData = new String[3];

        String sql = "select * from sleep_data where vc_device_id = '" + deviceId + "'";

        try {
            con = DBPool.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            con.commit();
            if (rs.next()){
                sleepData[0] = rs.getString("t_start_time");
                sleepData[1] = rs.getString("mt_heart_rate");
                sleepData[2] = rs.getString("t_end_time");
                return sleepData;
            }

        }catch (SQLException e){
            logger.error("check device sql error" , e);
        }finally {
            DBPool.release(con,ps,rs);
        }
        return null;
    }
    public static boolean saveDevice(String deviceId){

        Connection        con      = null;
        PreparedStatement ps        = null;

        String sql = "insert into sleep_data (vc_device_id,t_start_time) values(?,?)";
        try {
            con = DBPool.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);
            ps.setString(1, deviceId);
            ps.setString(2, ServerMain.dataMap.get(deviceId));
            ps.executeUpdate();
            con.commit();
            return true;
        }catch (SQLException e){
            logger.error("save error" , e);
        }finally {
            DBPool.release(con,ps,null);
        }
        return false;

    }


    public static boolean updateDevice(String deviceId, String sleepData){

        Connection        con      = null;
        PreparedStatement ps        = null;
        ResultSet         rs        = null;

        String sql = "update sleep_data set mt_heart_rate ='" + sleepData + "' WHERE vc_device_id = '" + deviceId + "'";
        try {
            con = DBPool.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            con.commit();
            return true;
        }catch (SQLException e){
            logger.error("update error" , e);
        }finally {
            DBPool.release(con, ps, null);
        }
        return false;

    }

    public static boolean updateDate(String deviceId , String endTime, String startTime){

        Connection        con      = null;
        PreparedStatement ps        = null;
        ResultSet         rs        = null;

        String sql = "update sleep_data set t_end_time ='" + endTime + "' WHERE vc_device_id = '" + deviceId + "' AND t_start_time = '" + startTime + "'";
        try {
            con = DBPool.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            con.commit();
            return true;
        }catch (SQLException e){
            logger.error("update error" , e);
        }finally {
            DBPool.release(con, ps, null);
        }
        return false;

    }



    public static boolean deleteDevice(String deviceId, String startTime){

        Connection        con      = null;
        PreparedStatement ps        = null;
        ResultSet         rs        = null;

//        String sql = "DELETE from sleep_data where vc_device_id = '" + deviceId + "' and t_start_time = '"
//                + ServerMain.dataMap.get(deviceId) + "''";
        String sql = "DELETE from sleep_data where vc_device_id = '" + deviceId + "' AND t_start_time = '" + startTime + "'";

        try {
            con = DBPool.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            con.commit();
            logger.debug("delete sleepData for device :[" + deviceId + "]");
            return true;
        }catch (SQLException e){
            logger.error("delete error" , e);
        }finally {
            DBPool.release(con, ps, null);
        }
        return false;

    }

}
