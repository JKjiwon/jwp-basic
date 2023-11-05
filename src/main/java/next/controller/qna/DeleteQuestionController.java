package next.controller.qna;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteQuestionController implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }

        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findByQuestionId(Long.parseLong(req.getParameter("questionId")));
        String userId = question.getWriter();
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);

        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 삭제할 수 없습니다.");
        }

        questionDao.delete(question);

        return "redirect:/";
    }
}
