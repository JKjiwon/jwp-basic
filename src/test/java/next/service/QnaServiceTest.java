package next.service;

import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private AnswerDao answerDao;

    private QnaService qnaService;

    @Before
    public void setUp() {
        qnaService = new QnaService(questionDao, answerDao);
    }

    @Test(expected = CannotDeleteException.class)
    public void deleteQuestion_없는_질문() throws Exception {
        when(questionDao.findById(1L)).thenReturn(null);
        qnaService.deleteQuestion(1L, newUser("userId"));
        verify(questionDao, never()).delete(1L);
    }

    @Test(expected = CannotDeleteException.class)
    public void deleteQuestion_다른_사용자() throws Exception {
        Question question = getQuestion(1L, "jiwon");
        when(questionDao.findById(1L)).thenReturn(question);
        qnaService.deleteQuestion(1L, newUser("userId"));
        verify(questionDao, never()).delete(1L);
    }

    @Test
    public void deleteQuestion_같은_사용자_답변없음() throws Exception {
        Question question = getQuestion(1L, "jiwon");
        when(questionDao.findById(1L)).thenReturn(question);
        when(answerDao.findAllByQuestionId(1L)).thenReturn(new ArrayList<>());

        qnaService.deleteQuestion(1L, newUser("jiwon"));
        verify(questionDao, times(1)).delete(1L);
    }

    private static Question getQuestion(Long questionId, String writer) {
        return new Question(questionId, writer, "title", "contents", new Date(), 0);
    }

    private static User newUser(String userId) {

        return new User(userId, "password", "name", "email");
    }
}