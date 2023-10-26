package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final String REDIRECT_PREFIX = "redirect:";
    private static Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestMapping requestMapping;

    @Override
    public void init() throws ServletException {
        requestMapping = new RequestMapping();
        requestMapping.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        log.debug("Method: {}, RequestURI: {}", req.getMethod(), uri);

        Controller controller = requestMapping.getController(uri);
        try {
            String viewPath = controller.execute(req, resp);
            move(req, resp, viewPath);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private static void move(HttpServletRequest req, HttpServletResponse resp, String viewPath) throws IOException, ServletException {
        if (viewPath.startsWith(REDIRECT_PREFIX)) {
            resp.sendRedirect(viewPath.substring(REDIRECT_PREFIX.length()));
        } else {
            RequestDispatcher rd = req.getRequestDispatcher(viewPath);
            rd.forward(req, resp);
        }
    }
}
