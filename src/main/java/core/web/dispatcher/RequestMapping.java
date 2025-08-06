package core.web.dispatcher;

import next.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private final static Logger logger = LoggerFactory.getLogger(RequestMapping.class);
    private final static Map<String, Controller> mappings = new HashMap<>();

    void init() {
        mappings.put("/", new HomeController());
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/form", new CreateUserFormController());
        mappings.put("/users", new ListUserController());
        mappings.put("/users/loginForm", new LoginFormController());
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/updateForm", new UpdateUserFormController());
        mappings.put("/users/update", new UpdateUserController());

        logger.info("Initialized Request Mapping!!");
    }

    public Controller getController(String path) {
        return mappings.get(path);
    }
}
