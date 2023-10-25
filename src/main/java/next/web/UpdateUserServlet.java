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
import java.io.IOException;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);
        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/user/update_form.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");

        String updatedPassword = req.getParameter("password");
        String updatedEmail = req.getParameter("email");
        String updatedName = req.getParameter("name");

        log.debug("Update User[{}] password={}, name={}, email={}", userId, updatedPassword, updatedName, updatedEmail);

        User user = DataBase.findUserById(userId);
        user.update(updatedPassword, updatedName, updatedEmail);
        DataBase.updateUser(user);

        resp.sendRedirect("/user/list");
    }
}
