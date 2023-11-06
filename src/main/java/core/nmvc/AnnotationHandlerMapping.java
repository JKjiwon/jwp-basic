package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackages;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackages = basePackage;
    }

    public void initialize() throws Exception {
        Reflections reflections = new Reflections(basePackages);
        Set<Class<?>> controllerClazz = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> clazz : controllerClazz) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    HandlerKey handlerKey = new HandlerKey(annotation.value(), annotation.method());
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    handlerExecutions.put(handlerKey, new HandlerExecution(instance, method));
                    log.debug("Add AnnotationHandlerMapping: handlerKey={}, method={}", handlerKey, clazz.getName() + "." + method.getName() + "()");
                }
            }
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
