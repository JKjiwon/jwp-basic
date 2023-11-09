package next.service;

import next.dao.QuestionDao;
import next.model.Question;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class MockQuestionDao implements QuestionDao {

    private Map<Long, Question> questions = new HashMap<>();

    @Override
    public Question insert(Question question) {
        return questions.put(question.getQuestionId(), question);
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<>(questions.values());
    }

    @Override
    public Question findById(long questionId) {
        return questions.get(questionId);
    }

    @Override
    public void update(Question question) {
    }

    @Override
    public void delete(long questionId) {
        questions.remove(questionId);
    }

    @Override
    public void updateCountOfAnswer(long questionId) {
        Question question = questions.get(questionId);
        try {
            Class<Question> clazz = Question.class;
            Field countOfComment = clazz.getDeclaredField("countOfComment");
            countOfComment.setAccessible(true);
            countOfComment.set(question, question.getCountOfComment() + 1);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        questions.clear();
    }
}
