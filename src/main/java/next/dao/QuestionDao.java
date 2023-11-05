package next.dao;

import core.jdbc.*;
import next.model.Question;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class QuestionDao {

    private static final RowMapper<Question> QUESTION_ROW_MAPPER = rs ->
            new Question(
                    rs.getLong("questionId"),
                    rs.getString("writer"),
                    rs.getString("title"),
                    rs.getString("contents"),
                    rs.getTimestamp("createdDate").toLocalDateTime(),
                    rs.getInt("countOfAnswer")
            );

    public Question save(Question question) {
        if (question.getQuestionId() == null) {
            return insert(question);
        }
        return update(question);
    }

    public Question insert(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO QUESTIONS(writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)";

        PreparedStatementCreator psc = con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, question.getWriter());
            pstmt.setString(2, question.getTitle());
            pstmt.setString(3, question.getContents());
            pstmt.setTimestamp(4, convertLocalDateTimeToTimestamp(question.getCreatedDate()));
            pstmt.setInt(5, question.getCountOfAnswer());
            return pstmt;
        };
        KeyHolder holder = new KeyHolder();
        jdbcTemplate.update(psc, holder);
        return findByQuestionId(holder.getId());
    }

    public Question update(Question question) {
        if (question.getQuestionId() == null) {
            throw new IllegalArgumentException("questionId 가 없습니다.");
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE QUESTIONS SET writer=?, title=?, contents=?, createdDate=?, countOfAnswer=? WHERE questionId=?";

        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, question.getWriter());
            pstmt.setString(2, question.getTitle());
            pstmt.setString(3, question.getContents());
            pstmt.setTimestamp(4, convertLocalDateTimeToTimestamp(question.getCreatedDate()));
            pstmt.setInt(5, question.getCountOfAnswer());
            pstmt.setLong(6, question.getQuestionId());
        };

        jdbcTemplate.update(sql, pss);
        return findByQuestionId(question.getQuestionId());
    }

    public void delete(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "DELETE FROM QUESTIONS WHERE questionId=?";
        jdbcTemplate.update(sql, question.getQuestionId());
    }

    public Question findByQuestionId(Long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId=?";

        return jdbcTemplate.queryForObject(sql, QUESTION_ROW_MAPPER, questionId);
    }

    public List<Question> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS ORDER BY createdDate DESC";

        return jdbcTemplate.query(sql, QUESTION_ROW_MAPPER);
    }

    private Timestamp convertLocalDateTimeToTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
