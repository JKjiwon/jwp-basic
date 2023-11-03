package next.dao;

import core.jdbc.ConnectionManager;
import next.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.time.LocalDateTime;

public class QuestionDaoTest {

    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void insert() throws Exception {
        Question question = new Question(null, "김지원", "JWP", "How to study jwp", LocalDateTime.now(), 5);
        QuestionDao questionDao = new QuestionDao();
        Question foundQuestion = questionDao.insert(question);
        System.out.println(question);
        System.out.println(foundQuestion);
    }
}