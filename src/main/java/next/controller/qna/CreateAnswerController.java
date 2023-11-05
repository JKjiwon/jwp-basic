package next.controller.qna;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Objects;

public class CreateAnswerController implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = Objects.requireNonNull(UserSessionUtils.getUserFromSession(req.getSession())).getUserId();
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        String contents = req.getParameter("contents");

        Answer answer = new Answer(userId,
                contents,
                LocalDateTime.now(),
                questionId);

        new AnswerDao().save(answer);

        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findByQuestionId(questionId);
        question.addAnswer();
        questionDao.save(question);

        return "redirect:/qna/show?questionId=" + questionId;
    }

}
