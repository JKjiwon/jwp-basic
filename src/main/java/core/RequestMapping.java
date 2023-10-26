package core;

import next.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static final Map<String, Controller> router = new HashMap<>();

    static {
        router.put("/", new HomeController());
        router.put("/user/form", new CreateUserFormController());
        router.put("/users/create", new CreateUserFormController());
        router.put("/users", new ListUserController());
        router.put("/users/loginForm", new LoginFormController());
        router.put("/users/login", new LoginController());
        router.put("/users/logout", new LogoutController());
        router.put("/users/profile", new ProfileController());
        router.put("/users/updateForm", new UpdateUserFormController());
        router.put("/users/update", new UpdateUserController());
    }

    public static Controller getController(String url) {
        return router.get(url);
    }
}
