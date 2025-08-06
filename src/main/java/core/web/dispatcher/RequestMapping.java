package core.web.dispatcher;

import next.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private final static Map<String, Controller> mappings = new HashMap<>();
    private final static DefaultController defaultController = new DefaultController();

    static {
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
    }

    public static Controller getController(String path) {
        return mappings.get(path);
    }
}
