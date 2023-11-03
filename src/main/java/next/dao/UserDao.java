package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };

        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)");
    }

    public void update(User user) throws SQLException {

        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };

        jdbcTemplate.update("UPDATE USERS SET password=?, name=?, email=? WHERE userId=?");
    }

    public List<User> findAll() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
            }

        };

        RowMapper rowMapper = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));

        String sql = "SELECT userId, password, name, email FROM USERS";
        return (List<User>) jdbcTemplate.query(sql, rowMapper);
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };

        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        RowMapper rowMapper = rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"));
        return (User) jdbcTemplate.queryForObject(sql, rowMapper);
    }
}
