package next.service;

import next.CannotDeleteException;
import next.dao.MockAnswerDao;
import next.dao.MockQuestionDao;
import next.model.Question;
import next.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class QnaServiceTest {

    private QnaService qnaService;
    private MockQuestionDao questionDao;
    private MockAnswerDao answerDao;

    @Before
    public void setUp() {
        questionDao = new MockQuestionDao();
        answerDao = new MockAnswerDao();
        qnaService = new QnaService(questionDao, answerDao);
    }

    @Test(expected = CannotDeleteException.class)
    public void deleteQuestion_없는_질문() throws Exception {
        Question question = getQuestion(1L, "jiwon");
        questionDao.insert(question);
        qnaService.deleteQuestion(1L, newUser("userId"));
    }

    @Test(expected = CannotDeleteException.class)
    public void deleteQuestion_다른_사용자() throws Exception {
        Question question = getQuestion(1L, "jiwon");
        questionDao.insert(question);
        qnaService.deleteQuestion(1L, newUser("userId"));
    }

    @Test
    public void deleteQuestion_같은_사용자_답변없음() throws Exception {
        Question question = getQuestion(1L, "jiwon");
        questionDao.insert(question);
        qnaService.deleteQuestion(1L, newUser("jiwon"));
    }

    private static Question getQuestion(Long questionId, String writer) {
        return new Question(questionId, writer, "title", "contents", new Date(), 0);
    }

    private static User newUser(String userId) {

        return new User(userId, "password", "name", "email");
    }
}