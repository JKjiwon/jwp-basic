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

    public List<T> query(String sql, RowMapper<T> rm, PreparedStatementSetter pss) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(rm.mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            closeAll(con, pstmt, rs);
        }
    }


    public List<T> query(String sql, RowMapper<T> rm, Object... parameters) {
        return query(sql, rm, createPreparedStatementSetter(parameters));
    }

    public T queryForObject(String sql, RowMapper<T> rm, Object... parameters) {
        List<T> result = query(sql, rm, parameters);
        if (result == null) {
            return null;
        }
        return result.get(0);
    }

    private PreparedStatementSetter createPreparedStatementSetter(Object[] parameters) {
        return pstmt -> {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
        };
    }
}
