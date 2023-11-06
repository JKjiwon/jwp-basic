package core.mvc;

import java.io.IOException;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private LegacyRequestMapping lrm;
    private AnnotationHandlerMapping ahm;

    @Override
    public void init() throws ServletException {
        lrm = new LegacyRequestMapping();
        lrm.initMapping();

        ahm = new AnnotationHandlerMapping("next");
        ahm.initialize();

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Controller controller = lrm.findController(req.getRequestURI());
        ModelAndView mav;
        if (controller != null) {
            try {
                mav = controller.execute(req, resp);
                View view = mav.getView();
                view.render(mav.getModel(), req, resp);
            } catch (Throwable e) {
                logger.error("Exception : {}", e);
                throw new ServletException(e.getMessage());
            }
        } else {
            HandlerExecution handler = ahm.getHandler(req);
            try {
                mav = handler.handle(req, resp);
                View view = mav.getView();
                view.render(mav.getModel(), req, resp);
            } catch (Throwable e) {
                logger.error("Exception : {}", e);
                throw new ServletException(e.getMessage());
            }
        }
    }
}
