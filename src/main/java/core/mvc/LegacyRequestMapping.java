package core.mvc;

import core.nmvc.HandlerMapping;
import next.controller.qna.*;
import next.controller.user.LoginController;
import next.controller.user.LogoutController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class LegacyRequestMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/logout", new LogoutController());

        mappings.put("/qna/show", new ShowQuestionController());
        mappings.put("/qna/form", new CreateFormQuestionController());
        mappings.put("/qna/create", new CreateQuestionController());
        mappings.put("/qna/updateForm", new UpdateFormQuestionController());
        mappings.put("/qna/update", new UpdateQuestionController());
        mappings.put("/qna/delete", new DeleteQuestionController());
        mappings.put("/api/qna/deleteQuestion", new ApiDeleteQuestionController());
        mappings.put("/api/qna/list", new ApiListQuestionController());
        mappings.put("/api/qna/addAnswer", new AddAnswerController());
        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController());

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }

    @Override
    public Controller getHandler(HttpServletRequest request) {
        return mappings.get(request.getRequestURI());
    }
}
