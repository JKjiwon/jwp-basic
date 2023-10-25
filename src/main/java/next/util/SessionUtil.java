package next.util;

import next.model.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static boolean isSessionUser(HttpSession session, String userId) {
        User sessionUser = getSessionUser(session);
        if (sessionUser == null) {
            return false;
        }

        return sessionUser.getUserId().equals(userId);
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
