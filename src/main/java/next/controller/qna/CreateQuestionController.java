package next.controller.qna;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Objects;

public class CreateQuestionController implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String title = req.getParameter("title");
        String contents = req.getParameter("contents");
        String userId = Objects.requireNonNull(UserSessionUtils.getUserFromSession(req.getSession())).getUserId();
        Question question = new Question(userId, title, contents, LocalDateTime.now());
        Question savedQuestion = new QuestionDao().save(question);

        return "redirect:/qna/show?questionId=" + savedQuestion.getQuestionId();
    }

}
