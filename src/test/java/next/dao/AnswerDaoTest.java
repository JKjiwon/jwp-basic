package next.dao;

import core.jdbc.ConnectionManager;
import next.model.Answer;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AnswerDaoTest {

    @BeforeEach
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @DisplayName("답변을 저장하면 answerId가 부여된다.")
    @Test
    public void save() {
        // given
        Answer answer = new Answer("testId", "testContents", LocalDateTime.now(), 1L);
        // when
        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.save(answer);

        // then
        assertThat(savedAnswer.getAnswerId()).isNotNull();
    }

    @DisplayName("답변을 업데이트 할 수 있다.")
    @Test
    public void update() {
        // given
        Answer answer = new Answer("testId", "testContents", LocalDateTime.now(), 1L);
        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.save(answer);

        // when
        savedAnswer.update("updated testContents");
        Answer updateQuestion = answerDao.save(savedAnswer);

        // then
        assertThat(updateQuestion.getContents()).isEqualTo("updated testContents");
    }

    @DisplayName("답변을 answerId로 조회 할 수 있다.")
    @Test
    public void findByQuestionId() {
        // given
        Answer answer = new Answer("testId", "testContents", LocalDateTime.now(), 1L);
        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.save(answer);

        // when
        Answer foundAnswer = answerDao.findByAnswerId(savedAnswer.getAnswerId());

        // then
        assertThat(foundAnswer.getWriter()).isEqualTo("testId");
        assertThat(foundAnswer.getContents()).isEqualTo("testContents");
        assertThat(foundAnswer.getQuestionId()).isEqualTo(1);
    }

    @DisplayName("답변을 questionId로 조회 할 수 있다.")
    @Test
    public void findAllByQuestionId() {
        // given
        Answer answer1 = new Answer("testId1", "testContents1", LocalDateTime.of(2023, 11, 5, 10, 1), 1L);
        Answer answer2 = new Answer("testId2", "testContents2", LocalDateTime.of(2023, 11, 5, 10, 2), 1L);
        Answer answer3 = new Answer("testId3", "testContents3", LocalDateTime.of(2023, 11, 5, 10, 3), 2L);

        AnswerDao answerDao = new AnswerDao();
        answerDao.save(answer1);
        answerDao.save(answer2);
        answerDao.save(answer3);

        // when
        List<Answer> answers = answerDao.findAllByQuestionId(1L);

        // then
        assertThat(answers).hasSize(2)
                .extracting("writer", "contents")
                .containsExactly(
                        Tuple.tuple("testId2", "testContents2"),
                        Tuple.tuple("testId1", "testContents1")
                );
    }

    @DisplayName("답변을 삭제 할 수 있다.")
    @Test
    public void delete() {
        // given
        Answer answer = new Answer("testId", "testContents", LocalDateTime.now(), 1L);
        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.save(answer);

        // when
        answerDao.delete(savedAnswer);

        // then
        assertThat(answerDao.findByAnswerId(savedAnswer.getAnswerId())).isNull();
    }
}