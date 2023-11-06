package core.nmvc;

import core.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);

    private Object controller;
    private Method method;


    public HandlerExecution(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.debug("HandlerExecution.handle: controller={}, method={}", controller.getClass().getName(), method.getName());
            return (ModelAndView) method.invoke(controller, request, response);
        } catch (Exception e) {
            throw new RuntimeException("Fail to invoke.", e);
        }
    }
}
