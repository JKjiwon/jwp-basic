package core.ref;

import next.model.Question;
import next.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        String fields = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.joining(", "));
        logger.debug("fields={}", fields);

        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            String name = constructor.getName();
            String parameterType = Arrays.stream(constructor.getParameterTypes()).map(Class::getName).collect(Collectors.joining(", "));
            String signature = name + "(" + parameterType + ")";
            logger.debug("constructor={}", signature);
        }

        for (Method method : clazz.getDeclaredMethods()) {
            String name = method.getName();
            String parameterType = Arrays.stream(method.getParameterTypes()).map(Class::getName).collect(Collectors.joining(", "));
            String signature = name + "(" + parameterType + ")";
            logger.debug("method={}", signature);
        }

        logger.debug(clazz.getName());
    }

    // public User(String userId, String password, String name, String email)
    @Test
    public void newInstanceWithConstructorArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == 4) {
                User user = (User) constructor.newInstance("jiwon", "1234", "김지원", "jwkim@gmail.com");
                logger.debug("user={}", user);
            }
        }

    }
    
    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Student student = new Student();
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Field nameField = clazz.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(student, "jiwon");


        Field ageField = clazz.getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.set(student, 32);

        logger.debug("student={}", student);
    }
}
