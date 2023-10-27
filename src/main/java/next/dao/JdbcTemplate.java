package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter pstmts) throws DataAccessException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmts.setValues(pstmt);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
        }
    }

    public <T> List<T> query(String sql, RowMapper<T> rm) throws DataAccessException {
        return query(sql, null, rm);
    }

    public <T> List<T> query(String sql, PreparedStatementSetter pstmts, RowMapper<T> rm) throws DataAccessException {
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
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
        }
    }

    public <T> T queryForObject(String sql, PreparedStatementSetter pstmts, RowMapper<T> rm) throws DataAccessException {
        List<T> objects = query(sql, pstmts, rm);
        if (objects.isEmpty()) {
            return null;
        }
        return objects.get(0);
    }
}
