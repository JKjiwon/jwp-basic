package next.web;

import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/profile")
public class UserProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String USER_SESSION_KEY = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object value = session.getAttribute(USER_SESSION_KEY);
        if (value == null) {
            resp.sendRedirect("/");
            return;
        }
        User user = (User) value;
        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/user/profile.jsp");
        rd.forward(req, resp);
    }
}
