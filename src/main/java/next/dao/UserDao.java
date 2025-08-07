package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.User;

import java.util.List;

public class UserDao {

    private final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

    public void insert(User user) {
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) {
        jdbcTemplate.update("UPDATE USERS SET password=?, name=?, email=? WHERE userId=?", user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }


    public List<User> findAll() {
        RowMapper<User> rm = rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"));
        return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS", null, rm);
    }

    public User findByUserId(String userId) {
        PreparedStatementSetter pstmts = pstmt -> pstmt.setString(1, userId);
        RowMapper<User> rm = rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"));
        return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", pstmts, rm);
    }
}
