package next.controller.qna;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }

        AnswerDao answerDao = new AnswerDao();
        Answer answer = answerDao.findByAnswerId(Long.parseLong(req.getParameter("answerId")));
        String userId = answer.getWriter();
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);

        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 삭제할 수 없습니다.");
        }

        answerDao.delete(answer);

        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findByQuestionId(answer.getQuestionId());
        question.removeAnswer();
        questionDao.save(question);

        return "redirect:/qna/show?questionId=" + answer.getQuestionId();
    }
}
