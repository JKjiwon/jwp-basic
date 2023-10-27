package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectJdbcTemplate {

    @SuppressWarnings("rawtypes")
    public List query(String sql) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            setValues(pstmt);

            rs = pstmt.executeQuery();

            List<Object> objects = new ArrayList<>();
            while (rs.next()) {
                objects.add(mapRow(rs));
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

    @SuppressWarnings("rawtypes")
    public Object queryForObject(String sql) throws SQLException {
        List objects = query(sql);
        if (objects.isEmpty()) {
            return null;
        }
        return objects.get(0);
    }

    protected abstract void setValues(PreparedStatement pstmt) throws SQLException;

    protected abstract Object mapRow(ResultSet rs) throws SQLException;
}
