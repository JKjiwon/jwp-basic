package next.service;

import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private AnswerDao answerDao;

    @InjectMocks
    private QnaService qnaService;

    @Test(expected = CannotDeleteException.class)
    public void deleteQuestion_없는_질문() throws Exception {
        // given
        when(questionDao.findById(1L)).thenReturn(null);

        // when
        qnaService.deleteQuestion(1L, newUser("userId"));

        // then
        verify(questionDao, never()).delete(1L);
    }

    @Test(expected = CannotDeleteException.class)
    public void deleteQuestion_삭제할수_없음() throws Exception {
        // given
        User user = newUser("userId");
        Question question = new Question(1L, user.getUserId(), "title", "contents", new Date(), 0) {
            @Override
            public boolean canDelete(User user, List<Answer> answers) throws CannotDeleteException {
                throw new CannotDeleteException("삭제할수 없음");
            }
        };
        when(questionDao.findById(1L)).thenReturn(question);

        // when
        qnaService.deleteQuestion(1L, user);

        // then
        verify(questionDao, never()).delete(1L);
    }

    @Test
    public void deleteQuestion_삭제할수_있음() throws Exception {
        // given
        User user = newUser("userId");
        Question question = new Question(1L, user.getUserId(), "title", "contents", new Date(), 0) {
            @Override
            public boolean canDelete(User user, List<Answer> answers) throws CannotDeleteException {
                return true;
            }
        };
        when(questionDao.findById(1L)).thenReturn(question);

        // when
        qnaService.deleteQuestion(1L, newUser("jiwon"));

        // then
        verify(questionDao, times(1)).delete(1L);
    }


    private static User newUser(String userId) {
        return new User(userId, "password", "name", "email");
    }
}