package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter pss) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pss.setValues(pstmt);

            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @SuppressWarnings("ThrowFromFinallyBlock")
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pss) throws DataAccessException {
        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pss.setValues(pstmt);
            rs = pstmt.executeQuery();

            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rowMapper.mapRow(rs));
            }

            return result;
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
        }
    }

    public void update(String sql, Object... parameters) throws DataAccessException {
        PreparedStatementSetter pss = pstmt -> {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
        };
        update(sql, pss);
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) throws DataAccessException {
        PreparedStatementSetter pss = pstmt -> {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
        };
        return query(sql, rowMapper, pss);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pss) throws DataAccessException {
        List<T> result = query(sql, rowMapper, pss);

        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... parameters) throws DataAccessException {
        PreparedStatementSetter pss = pstmt -> {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
        };

        return queryForObject(sql, rowMapper, pss);
    }
}
