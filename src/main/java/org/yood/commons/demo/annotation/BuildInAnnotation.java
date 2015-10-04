package org.yood.commons.demo.annotation;


import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static java.lang.annotation.ElementType.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
@Inherited
public @interface BuildInAnnotation {

    String value() default "";

}

@BuildInAnnotation("Hello World")
class Test {
    public static void main(String[] args) throws NoSuchMethodException {
        Method test = Test.class.getMethod("test", String.class);
        int i = 0;
        Parameter[] parameters = test.getParameters();
        for (Annotation[] annotations : test.getParameterAnnotations()) {
            Parameter parameter = parameters[i];
            for (Annotation annotation : annotations) {
                System.out.println("param name = " + parameter.getName());
                System.out.println("param type = " + parameter.getType());
                if (annotation.annotationType() == BuildInAnnotation.class) {
                    BuildInAnnotation annotation1 = (BuildInAnnotation) annotation;
                    System.out.println("annotation value = " + annotation1.value());
                }
            }
            i++;
        }
        BuildInAnnotation annotation1 = test.getAnnotation(BuildInAnnotation.class);
        System.out.println("value=" + annotation1.value());
        BuildInAnnotation annotation = Test.class.getAnnotation(BuildInAnnotation.class);
        System.out.println(annotation.value());
    }

    /**
     * @deprecated user test1 instead
     */
    @Deprecated
    @BuildInAnnotation("test")
    public void test(@BuildInAnnotation("param value") String str) {
    }
}
