package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementCreator;
import core.jdbc.RowMapper;
import next.model.Question;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    public Question findByQuestionId(Long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId=?";

        return jdbcTemplate.queryForObject(sql, QUESTION_ROW_MAPPER, questionId);
    }

    private Timestamp convertLocalDateTimeToTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
