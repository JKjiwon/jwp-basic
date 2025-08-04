package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final long serialVersionUID = 1L;

    private static final String USER_SESSION_KEY = "user";

    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUserId = req.getParameter("userId");

        HttpSession session = req.getSession();
        Object value = session.getAttribute(USER_SESSION_KEY);
        if (value == null) {
            resp.sendRedirect("/");
            return;
        }
        User user = (User) value;
        if (!requestUserId.equals(user.getUserId())) {
            resp.sendRedirect("/");
            return;
        }

        req.setAttribute("users", DataBase.findUserById(requestUserId));
        RequestDispatcher rd = req.getRequestDispatcher("/user/update.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object value = session.getAttribute(USER_SESSION_KEY);
        if (value == null) {
            resp.sendRedirect("/");
            return;
        }
        User sessionUser = (User) value;

        User user = DataBase.findUserById(sessionUser.getUserId());

        user.update(req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"));
        log.debug("Update User[{}]", user);

        resp.sendRedirect("/user/list");
    }
}
