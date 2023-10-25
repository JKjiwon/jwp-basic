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
    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isLogin(req)) {
            resp.sendRedirect("/user/login");
            return;
        }

        User sessionUser = getSessionUser(req);
        String userId = req.getParameter("userId");

        assert sessionUser != null; // isLogin(req)을 통해 sessionUser != null 보장
        if (!sessionUser.getUserId().equals(userId)) {
            throw new IllegalStateException("잘못된 접근 입니다.");
        }

        User user = DataBase.findUserById(userId);
        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/user/update_form.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isLogin(req)) {
            resp.sendRedirect("/user/login");
            return;
        }

        User sessionUser = getSessionUser(req);
        String userId = req.getParameter("userId");


        assert sessionUser != null; // isLogin(req)을 통해 sessionUser != null 보장
        if (!sessionUser.getUserId().equals(userId)) {
            throw new IllegalStateException("잘못된 접근 입니다.");
        }

        User user = DataBase.findUserById(userId);
        user.update(req.getParameter("password"), req.getParameter("name"), req.getParameter("email"));
        DataBase.updateUser(user);
        log.debug("Update User[{}] password={}, name={}, email={}", userId, user.getPassword(), user.getName(), user.getEmail());

        resp.sendRedirect("/user/list");
    }

    private User getSessionUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object value = session.getAttribute("user");

        if (value != null) {
            return (User) value;
        }

        return null;
    }

    private boolean isLogin(HttpServletRequest req) {
        return getSessionUser(req) != null;
    }
}
