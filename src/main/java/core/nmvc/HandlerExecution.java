package core.nmvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

import java.lang.reflect.Method;

public class HandlerExecution {

    private Object controller;
    private Method method;


    public HandlerExecution(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            return (ModelAndView) method.invoke(controller, request, response);
        } catch (Exception e) {
            throw new RuntimeException("Fail to invoke.", e);
        }

    }
}
