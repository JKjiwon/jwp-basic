package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

    public void insert(User user) throws SQLException {
        PreparedStatementSetter pstmts = pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        };
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", pstmts);
    }

    public void update(User user) throws SQLException {
        PreparedStatementSetter pstmts = pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        };
        jdbcTemplate.update("UPDATE USERS SET password=?, name=?, email=? WHERE userId=?", pstmts);
    }


    public List<User> findAll() throws SQLException {
        RowMapper<User> rm = rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"));
        return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS", null, rm);
    }

    public User findByUserId(String userId) throws SQLException {
        PreparedStatementSetter pstmts = pstmt -> pstmt.setString(1, userId);
        RowMapper<User> rm = rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"));
        return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", pstmts, rm);
    }
}
