package next.controller.user;

import core.mvc.Controller;
import core.mvc.ModelAndView;
import core.mvc.ModelMap;
import core.mvc.view.JspView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new ModelAndView(new JspView("redirect:/users/loginForm"));
        }

        ModelMap model = new ModelMap();
        model.setAttribute("users", new UserDao().findAll());

        return new ModelAndView(model, new JspView("/user/list.jsp"));
    }
}
