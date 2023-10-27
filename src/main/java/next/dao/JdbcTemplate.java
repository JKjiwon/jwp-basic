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
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmts.setValues(pstmt);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(String sql, Object... parameters) throws DataAccessException {
        update(sql, createPreparedStatementSetter(parameters));
    }

    public <T> List<T> query(String sql, RowMapper<T> rm, Object... parameters) throws DataAccessException {
        return query(sql, rm, createPreparedStatementSetter(parameters));
    }

    public <T> List<T> query(String sql, RowMapper<T> rm, PreparedStatementSetter pstmts) throws DataAccessException {
        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmts.setValues(pstmt);

            rs = pstmt.executeQuery();

            List<T> objects = new ArrayList<>();
            while (rs.next()) {
                objects.add(rm.mapRow(rs));
            }

            return objects;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        }
    }

    public <T> T queryForObject(String sql, RowMapper<T> rm, PreparedStatementSetter pstmts) throws DataAccessException {
        List<T> objects = query(sql, rm, pstmts);
        if (objects.isEmpty()) {
            return null;
        }
        return objects.get(0);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rm, Object... parameters) throws DataAccessException {
        List<T> objects = query(sql, rm, createPreparedStatementSetter(parameters));
        if (objects.isEmpty()) {
            return null;
        }
        return objects.get(0);
    }

    private PreparedStatementSetter createPreparedStatementSetter(Object[] parameters) {
        return pstmt -> {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
        };
    }
}
