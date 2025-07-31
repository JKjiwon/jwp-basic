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
        req.setAttribute("user", DataBase.findUserById(userId));
        RequestDispatcher rd = req.getRequestDispatcher("/user/update.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        User user = DataBase.findUserById(userId);
        user.update(password, name, email);
        log.debug("Update User[{}]", user);

        resp.sendRedirect("/user/list");
    }
}
