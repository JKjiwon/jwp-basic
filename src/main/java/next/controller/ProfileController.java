package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import core.mvc.Controller;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ProfileController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");

        UserDao userDao = new UserDao();
        User user = null;
        try {
            user = userDao.findByUserId(userId);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }

        req.setAttribute("user", user);
        return "/user/profile.jsp";
    }
}
