package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.mvc.JspView;
import next.dao.QuestionDao;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

@Controller
public class HomeController {
    private QuestionDao questionDao = QuestionDao.getInstance();

    @RequestMapping("/")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("home.jsp")).addObject("questions", questionDao.findAll());
    }
}
