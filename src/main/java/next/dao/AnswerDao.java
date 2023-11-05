package next.dao;

import core.jdbc.*;
import next.model.Answer;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/*
    answerId 			bigint				auto_increment,
	writer				varchar(30)			NOT NULL,
	contents			varchar(5000)		NOT NULL,
	createdDate			timestamp			NOT NULL,
	questionId			bigint				NOT NULL,
 */

public class AnswerDao {

    private static final RowMapper<Answer> ANSWER_ROW_MAPPER = rs ->
            new Answer(
                    rs.getLong("answerId"),
                    rs.getString("writer"),
                    rs.getString("contents"),
                    rs.getTimestamp("createdDate").toLocalDateTime(),
                    rs.getLong("questionId")
            );

    public Answer save(Answer answer) {
        if (answer.getAnswerId() == null) {
            return insert(answer);
        }
        return update(answer);
    }

    public Answer insert(Answer answer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO ANSWERS(writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";

        PreparedStatementCreator psc = con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, answer.getWriter());
            pstmt.setString(2, answer.getContents());
            pstmt.setTimestamp(3, convertLocalDateTimeToTimestamp(answer.getCreatedDate()));
            pstmt.setLong(4, answer.getQuestionId());
            return pstmt;
        };

        KeyHolder holder = new KeyHolder();
        jdbcTemplate.update(psc, holder);
        return findByAnswerId(holder.getId());
    }

    public Answer update(Answer answer) {
        if (answer.getQuestionId() == null) {
            throw new IllegalArgumentException("answerId 가 없습니다.");
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE ANSWERS SET writer=?, contents=?, createdDate=?, questionId=? WHERE answerId=?";

        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, answer.getWriter());
            pstmt.setString(2, answer.getContents());
            pstmt.setTimestamp(3, convertLocalDateTimeToTimestamp(answer.getCreatedDate()));
            pstmt.setLong(4, answer.getQuestionId());
            pstmt.setLong(5, answer.getAnswerId());
        };

        jdbcTemplate.update(sql, pss);
        return findByAnswerId(answer.getQuestionId());
    }

    public Answer findByAnswerId(Long answerId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId=?";

        return jdbcTemplate.queryForObject(sql, ANSWER_ROW_MAPPER, answerId);
    }

    public List<Answer> findAllByQuestionId(Long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE questionId=? ORDER BY createdDate DESC";

        return jdbcTemplate.query(sql, ANSWER_ROW_MAPPER, questionId);
    }

    private Timestamp convertLocalDateTimeToTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
