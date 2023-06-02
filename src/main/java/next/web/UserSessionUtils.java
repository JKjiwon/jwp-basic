package next.web;

import next.model.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class UserSessionUtils {
    private static final String USER_SESSION_KEY = "user";

    public static Optional<User> getUserFromSession(HttpSession session) {
        Object value = session.getAttribute(USER_SESSION_KEY);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of((User) value);
    }

    public static boolean isLogin(HttpSession session) {
        return getUserFromSession(session).isPresent();
    }

    public static boolean isSameUser(HttpSession session, User user) {
        if (!isLogin(session)) {
            return false;
        }
        if (user == null) {
            return false;
        }

        return user.isSameUser(getUserFromSession(session).get());
    }
}
