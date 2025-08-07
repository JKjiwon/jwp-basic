package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static core.jdbc.JdbcCloseUtil.closeAll;

public class JdbcTemplate<T> {

    public void update(String sql, Object... paramters) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            for (int i = 0; i < paramters.length; i++) {
                pstmt.setObject(i + 1, paramters[i]);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            closeAll(con, pstmt, null);
        }
    }

    public List<T> query(String sql, PreparedStatementSetter pstmts, RowMapper<T> rm) {
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
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            closeAll(con, pstmt, rs);
        }
    }

    public T queryForObject(String sql, PreparedStatementSetter pstmts, RowMapper<T> rm) {
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
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            closeAll(con, pstmt, rs);
        }
    }
}
