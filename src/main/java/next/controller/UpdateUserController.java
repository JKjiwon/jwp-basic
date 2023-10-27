package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import core.mvc.Controller;
import next.model.User;

import java.sql.SQLException;

public class UpdateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDao userDao = new UserDao();
        String userId = req.getParameter("userId");
        User user = null;
        try {
            user = userDao.findByUserId(userId);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        if (user == null) {
            throw new NullPointerException("유저[" + userId + "]를 찾을 수 없습니다.");
        }

        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("Update User : {}", updateUser);
        try {
            user.update(updateUser);
            userDao.update(user);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return "redirect:/";
    }
}
