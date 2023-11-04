package next.dao;

import core.jdbc.ConnectionManager;
import next.model.Question;
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

public class QuestionDaoTest {

    @BeforeEach
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @DisplayName("질문을 저장하면 questionId가 부여된다.")
    @Test
    public void save() {
        // given
        Question question = new Question("김지원", "JWP", "How to study jwp", LocalDateTime.now());

        // when
        QuestionDao questionDao = new QuestionDao();
        Question foundQuestion = questionDao.save(question);

        // then
        assertThat(foundQuestion.getQuestionId()).isNotNull();
    }

    @DisplayName("질문을 업데이트 할 수 있다.")
    @Test
    public void update() {
        // given
        Question question = new Question("김지원", "JWP", "How to study jwp", LocalDateTime.now());
        QuestionDao questionDao = new QuestionDao();
        Question savedQuestion = questionDao.save(question);


        // when
        Question updateQuestionDto = updateQuestionDto("JWP_v2", "How to study jwp well");
        savedQuestion.update(updateQuestionDto);
        Question updateQuestion = questionDao.save(savedQuestion);

        // then
        assertThat(updateQuestion.getTitle()).isEqualTo("JWP_v2");
        assertThat(updateQuestion.getContents()).isEqualTo("How to study jwp well");
    }

    @DisplayName("질문을 questionId로 조회 할 수 있다.")
    @Test
    public void findByQuestionId() {
        // given
        Question question = new Question("김지원", "JWP", "How to study jwp", LocalDateTime.now());
        QuestionDao questionDao = new QuestionDao();
        Question savedQuestion = questionDao.save(question);

        // when
        Question foundQuestion = questionDao.findByQuestionId(savedQuestion.getQuestionId());

        // then
        assertThat(foundQuestion.getWriter()).isEqualTo("김지원");
        assertThat(foundQuestion.getTitle()).isEqualTo("JWP");
        assertThat(foundQuestion.getContents()).isEqualTo("How to study jwp");
        assertThat(foundQuestion.getCountOfAnswer()).isEqualTo(0);
    }


    @DisplayName("질문을 다수 조회 할 수 있다.")
    @Test
    public void findAll() {
        Question question1 = new Question("김지원1", "JWP1", "How to study jwp1", LocalDateTime.now());
        Question question2 = new Question("김지원2", "JWP2", "How to study jwp2", LocalDateTime.now());
        Question question3 = new Question("김지원3", "JWP3", "How to study jwp3", LocalDateTime.now());

        QuestionDao questionDao = new QuestionDao();
        questionDao.save(question1);
        questionDao.save(question2);
        questionDao.save(question3);

        List<Question> questions = questionDao.findAll();
        assertThat(questions).hasSize(3)
                .extracting("writer", "title", "contents")
                .containsExactly(
                        Tuple.tuple("김지원1", "JWP1", "How to study jwp1"),
                        Tuple.tuple("김지원2", "JWP2", "How to study jwp2"),
                        Tuple.tuple("김지원3", "JWP3", "How to study jwp3")
                );
    }

    private Question updateQuestionDto(String title, String contents) {
        return new Question(null, title, contents, null);
    }
}