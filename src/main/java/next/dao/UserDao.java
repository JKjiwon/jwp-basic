package next.dao;

import next.model.User;

import java.util.List;

public class UserDao {

    public void insert(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter pstmts = pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        };

        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, pstmts);
    }

    public void update(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter pstmts = pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        };

        String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userId=?";
        jdbcTemplate.update(sql, pstmts);
    }

    public List<User> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        RowMapper<User> rm = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));

        String sql = "SELECT userId, password, name, email FROM USERS";
        return jdbcTemplate.query(sql, rm);
    }

    public User findByUserId(String userId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter pstmts = pstmt -> pstmt.setString(1, userId);
        RowMapper<User> rm = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));

        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return jdbcTemplate.queryForObject(sql, pstmts, rm);
    }

}
