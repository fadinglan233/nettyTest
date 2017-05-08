package tcp.mysql;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by fadinglan on 2017/5/6.
 */
public class JdbcPool {

    private static final Log logger = LogFactory.getLog(JdbcPool.class);

    /**
     * 在 java 中，编写数据库连接池需实现 java.sql.DataSource 接口
     * DBCP 连接池就是 java.sql.DataSource 接口的一个具体实现
     */
    private static DataSource dataSource  = null;

    static {
        try {

            Properties properties = new Properties();
            properties.load(new InputStreamReader(JdbcPool.class.getResourceAsStream("dbcpconfig.properties"), "UTF-8"));
            dataSource = BasicDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            logger.error("load db properties error",e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void release(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }

        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
