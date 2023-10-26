package core;

import next.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);

    private static final Map<String, Controller> router = new HashMap<>();

    public void init() {
        router.put("/", new HomeController());
        router.put("/users/form", new ForwardController("/user/form.jsp"));
        router.put("/users/create", new CreateUserController());
        router.put("/users", new ListUserController());
        router.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        router.put("/users/login", new LoginController());
        router.put("/users/logout", new LogoutController());
        router.put("/users/profile", new ProfileController());
        router.put("/users/updateForm", new UpdateUserFormController());
        router.put("/users/update", new UpdateUserController());

        log.info("RequestMapping init");
    }

    public Controller getController(String url) {
        return router.get(url);
    }
}
