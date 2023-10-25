package next.util;

import next.model.User;

import javax.servlet.http.HttpSession;

public class UserSessionUtils {

    public static final String USER_SESSION_KEY = "user";

    public static boolean isSessionUser(HttpSession session, User user) {
        User sessionUser = getSessionUser(session);
        if (sessionUser == null) {
            return false;
        }

        return sessionUser.isSameUser(user);
    }

    public static User getSessionUser(HttpSession session) {
        Object value = session.getAttribute("user");

        if (value != null) {
            return (User) value;
        }

        return null;
    }

    public static boolean isLogin(HttpSession session) {
        return getSessionUser(session) != null;
    }
}
