package next.controller.qna;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetQuestionController implements Controller {

    private static Logger log = LoggerFactory.getLogger(GetQuestionController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findByQuestionId(questionId);

        String writer = question.getWriter();
        User user = new UserDao().findByUserId(writer);

        List<Answer> answers = new AnswerDao().findAllByQuestionId(questionId);
        log.info("questionId={}, answer size={}", questionId, answers.size());

        req.setAttribute("user", user);
        req.setAttribute("question", question);
        req.setAttribute("answers", answers);

        return "/qna/show.jsp";
    }
}
