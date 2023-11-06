package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.ModelAndView;
import core.mvc.ModelMap;
import core.mvc.view.JsonView;
import core.mvc.view.JspView;
import next.dao.QuestionDao;

public class HomeController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();

        ModelMap model = new ModelMap();
        model.setAttribute("questions", questionDao.findAll());
        return new ModelAndView(model, new JspView("home.jsp"));
    }
}
