package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {

    private static final String USER_SESSION_KEY = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sessionUser = getUser(req.getSession());
        User user = DataBase.findUserById(req.getParameter("userId"));
        if (sessionUser == null || !sessionUser.getUserId().equals(user.getUserId())) {
            resp.sendRedirect("/");
            return;
        }

        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/user/update_form.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sessionUser = getUser(req.getSession());
        if (sessionUser == null || !sessionUser.getUserId().equals(req.getParameter("userId"))) {
            resp.sendRedirect("/");
            return;
        }

        User user = new User(
                req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"));
        DataBase.addUser(user);
        resp.sendRedirect("/user/list");
    }

    private User getUser(HttpSession session) {
        Object value = session.getAttribute(USER_SESSION_KEY);
        if (value != null) {
            return (User) value;
        }
        return null;
    }
}
