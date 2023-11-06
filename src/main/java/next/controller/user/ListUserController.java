package next.controller.user;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.mvc.view.JspView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new ModelAndView(new JspView("redirect:/users/loginForm"));
        }

        return jspView("/user/list.jsp").addObject("users", new UserDao().findAll());
    }
}
