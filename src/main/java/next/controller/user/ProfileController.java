package next.controller.user;

import core.mvc.Controller;
import core.mvc.ModelAndView;
import core.mvc.ModelMap;
import core.mvc.view.JspView;
import next.dao.UserDao;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        ModelMap model = new ModelMap();
        model.setAttribute("user", user);
        return new ModelAndView(model, new JspView("/user/profile.jsp"));
    }
}
