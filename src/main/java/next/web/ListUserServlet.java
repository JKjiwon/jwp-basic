package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.model.User;

@WebServlet("/user/list")
public class ListUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String USER_SESSION_KEY = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUser(req.getSession());
        if (user == null) {
            resp.sendRedirect("/");
            return;
        }
        req.setAttribute("users", DataBase.findAll());
        RequestDispatcher rd = req.getRequestDispatcher("/user/list.jsp");
        rd.forward(req, resp);
    }

    private User getUser(HttpSession session) {
        Object value = session.getAttribute(USER_SESSION_KEY);
        if (value != null) {
            return (User) value;
        }
        return null;
    }
}
