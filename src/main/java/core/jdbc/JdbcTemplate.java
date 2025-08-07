package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate<T> {

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

    public List<T> query(String sql, PreparedStatementSetter pstmts, RowMapper<T> rm) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<T> objs = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            if (pstmts != null) {
                pstmts.setValues(pstmt);
            }
            rs = pstmt.executeQuery();

            T obj = null;
            while (rs.next()) {
                obj = rm.mapRow(rs);
                objs.add(obj);
            }
            return objs;
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

    public T queryForObject(String sql, PreparedStatementSetter pstmts, RowMapper<T> rm) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmts.setValues(pstmt);
            rs = pstmt.executeQuery();
            T obj = null;
            while (rs.next()) {
                obj = rm.mapRow(rs);
            }

            return obj;
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
}
