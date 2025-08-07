package next.dao;

import next.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    public void insert(User user) throws SQLException {
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<User>() {
            @Override
            protected User mapRow(ResultSet rs) throws SQLException {
                return null;
            }

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
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<User>() {
            @Override
            protected User mapRow(ResultSet rs) throws SQLException {
                return null;
            }

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
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<User>() {
            @Override
            protected User mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {

            }
        };
        return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS");
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<User>() {
            @Override
            protected User mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            @Override
            protected void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        };
        return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?");
    }
}
