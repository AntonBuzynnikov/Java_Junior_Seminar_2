import Annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {
    public static void run(Class<?> testClass){
        final Object object = initTestObj(testClass);
        iteratorAnnotation(testClass, object, BeforeAll.class);
        test(testClass, object);
        iteratorAnnotation(testClass, object, AfterAll.class);
    }

    private static Object initTestObj(Class<?> testClass){
        try {
            Constructor<?> noArgsConstructor = testClass.getConstructor();
            return noArgsConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Нет конструктора по умолчанию");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e){
            throw new RuntimeException("Не удалось создать объект тест класса");
        }
    }
    private static void iteratorAnnotation(Class<?> testClass, Object testObject,Class annotation){
        for (Method testDeclaredMethod : testClass.getDeclaredMethods()) {
            if(testDeclaredMethod.getAnnotation(annotation)!=null){
                    try {
                        testDeclaredMethod.setAccessible(true);
                        testDeclaredMethod.invoke(testObject);
                        testDeclaredMethod.setAccessible(false);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
            }
        }
    }
    private static void beforeEach(Class<?> testClass, Object testObject){
        iteratorAnnotation(testClass, testObject, BeforeEach.class);
    }
    private static void afterEach(Class<?> testClass, Object testObject){
        iteratorAnnotation(testClass, testObject, AfterEach.class);
    }
    private static void test(Class<?> testClass, Object testObject){
        final List<Method> sortedMethods = sortedTest(testClass);
        for (Method testDeclaredMethod : sortedMethods) {
            if(testDeclaredMethod.getAnnotation(Test.class)!=null){
                try {
                    beforeEach(testClass, testObject);
                    testDeclaredMethod.setAccessible(true);
                    testDeclaredMethod.invoke(testObject);
                    testDeclaredMethod.setAccessible(false);
                    afterEach(testClass, testObject);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private static List<Method> sortedTest(Class<?> testClass){
        List<Method> sorted = new ArrayList<>();
        for (Method declaredMethod : testClass.getDeclaredMethods()) {
            if(declaredMethod.getAnnotation(Test.class)!=null){
                sorted.add(declaredMethod);
            }
        }
        sorted.sort(Comparator.comparing(x->x.getAnnotation(Test.class).order()));
        return sorted;
    }


}
