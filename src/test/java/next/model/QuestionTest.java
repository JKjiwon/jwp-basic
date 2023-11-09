package next.model;

import next.CannotDeleteException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class QuestionTest {

    @Test(expected = CannotDeleteException.class)
    public void canDelete_글쓴이_다름() throws Exception {
        User user = createUser("jiwon");
        Question question = createQuestion("john");
        question.canDelete(user, new ArrayList<>());
    }

    @Test
    public void canDelete_글쓴이_같음_답변_없음() throws Exception {
        User user = createUser("jiwon");
        Question question = createQuestion("jiwon");

        assertTrue(question.canDelete(user, new ArrayList<>()));
    }

    @Test
    public void canDelete_같은_사용자_답변() throws Exception {
        String userId = "jiwon";
        User user = createUser(userId);
        Question question = createQuestion(userId);

        assertTrue(question.canDelete(user, Arrays.asList(createAnswer(userId))));
    }

    @Test(expected = CannotDeleteException.class)
    public void canDelete_다른_사용자_답변() throws Exception {
        User user = createUser("jiwon");
        Question question = createQuestion("jiwon");
        question.canDelete(user, Arrays.asList(createAnswer("john")));
    }

    private User createUser(String userId) {
        return new User(userId, "password", "name", "email");
    }

    private Question createQuestion(String writer) {
        return new Question(1L, writer, "title", "contents", new Date(), 0);
    }

    private Answer createAnswer(String writer) {
        return new Answer(writer, "content", 1L);
    }

}