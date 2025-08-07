package core.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcCloseUtil {
    public final static Logger log = LoggerFactory.getLogger(JdbcCloseUtil.class);

    public static void closeAll(Connection con, PreparedStatement pstmt, ResultSet rs) {
        close(rs);
        close(pstmt);
        close(con);
    }

    private static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("{}", e.getMessage());
            }
        }
    }

    private static void close(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.error("{}", e.getMessage());
            }
        }
    }

    private static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.error("{}", e.getMessage());
            }
        }
    }
}
