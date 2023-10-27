package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter pstmts) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmts.setValues(pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public <T> List<T> query(String sql, RowMapper<T> rm) throws SQLException {
        return query(sql, null, rm);
    }

    public <T> List<T> query(String sql, PreparedStatementSetter pstmts, RowMapper<T> rm) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);

            if (pstmts != null) {
                pstmts.setValues(pstmt);
            }

            rs = pstmt.executeQuery();

            List<T> objects = new ArrayList<>();
            while (rs.next()) {
                objects.add(rm.mapRow(rs));
            }

            return objects;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public <T> T queryForObject(String sql, PreparedStatementSetter pstmts, RowMapper<T> rm) throws SQLException {
        List<T> objects = query(sql, pstmts, rm);
        if (objects.isEmpty()) {
            return null;
        }
        return objects.get(0);
    }
}
