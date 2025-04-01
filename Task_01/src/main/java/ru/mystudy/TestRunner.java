package ru.mystudy;

import ru.mystudy.annotations.AfterSuite;
import ru.mystudy.annotations.BeforeSuite;
import ru.mystudy.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class TestRunner {
    public static void runTests(Class<?> c) {
        System.out.println("\nStart checking Class = " + getClassSimpleName(c) + "\n");

        try {

            if (Objects.isNull(c)) {
                System.out.println("Parameter c is null");
                throw new IllegalArgumentException("Parameter c is null");
            }

            Method[] beforeSuiteMethods = getMethodsWithAnnotation(c, BeforeSuite.class);
            Method[] afterSuiteMethods = getMethodsWithAnnotation(c, AfterSuite.class);
            checkStaticMethods(beforeSuiteMethods);
            checkStaticMethods(afterSuiteMethods);
            if (beforeSuiteMethods.length > 1) {
                generateThrowByCount(BeforeSuite.class, beforeSuiteMethods);
            }
            if (afterSuiteMethods.length > 1) {
                generateThrowByCount(AfterSuite.class, afterSuiteMethods);
            }
            Method[] testMethods = getMethodsWithAnnotation(c, Test.class);
            checkSimpleMethods(testMethods);

            Constructor<?> constructor = c.getConstructor();
            Object obj = constructor.newInstance();

            // BeforeSuite
            if (beforeSuiteMethods.length > 0) {
                System.out.println("*** invoke method with annotation @BeforeSuite ***");
                beforeSuiteMethods[0].invoke(obj);
            }

            // Test
            testMethods = getSortedByPriorityTestMethods(testMethods);
            invokeSortedTestMethods(obj, testMethods);

            // AfterSuite
            if (afterSuiteMethods.length > 0) {
                System.out.println("*** invoke method with annotation @AfterSuite ***");
                afterSuiteMethods[0].invoke(obj);
            }

            System.out.println("\nChecking Class = " + getClassSimpleName(c) + " is SUCCESS\n");
        } catch (Exception e) {
            System.out.println("\nChecking Class = " + getClassSimpleName(c) + " is FAIL\n");
            throw new RuntimeException(e);
        }
    }

    private static Method[] getMethodsWithAnnotation(Class<?> classObj, Class<? extends Annotation> classAnnotation) {
        Method[] declaredMethods = classObj.getDeclaredMethods();
        ArrayList<Method> methods = new ArrayList<>();
        for (var method : declaredMethods) {
            if (method.isAnnotationPresent(classAnnotation)) {
                methods.add(method);
            }
        }
        return methods.toArray(new Method[0]);
    }

    private static Method[] getSortedByPriorityTestMethods(Method[] methods) {
        if (Objects.isNull(methods)) {
            return null;
        }
        Map<Integer, Method> map = new HashMap<>(methods.length);
        for (var method : methods) {
            int priority = method.getAnnotation(Test.class).priority();
            if (priority < 1 || priority > 10) {
                String error = "Method = \"" + method.getName() + "()\" has not correct parameter Priority = " + priority;
                System.out.println(error);
                throw new IllegalArgumentException(error);
            }
            map.put(priority, method);
        }
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toList().toArray(new Method[0]);
    }

    private static void invokeSortedTestMethods(Object obj, Method[] methods)
            throws InvocationTargetException, IllegalAccessException {
        if (Objects.isNull(methods)) {
            return;
        }
        System.out.println("*** invoke methods with annotation @Test ***");
        for (var method : methods) {
            System.out.print("(" + method.getAnnotation(Test.class).priority() + "): ");
            method.invoke(obj);
        }
    }

    private static void generateThrowByCount(Class<? extends Annotation> annotationClass, Method[] methods) {
        String error = "count methods with annotation = " + annotationClass.getSimpleName() +
                " more that 1, count = " + methods.length;
        System.out.println(error);
        throw new IllegalArgumentException(error);
    }

    private static void checkStaticMethods(Method[] methods) {
        if (Objects.isNull(methods)) {
            return;
        }
        for (var method : methods) {
            if (!Modifier.isStatic(method.getModifiers())) {
                String error = "Method = \"" + method.getName() + "()\" is not STATIC";
                System.out.println(error);
                throw new IllegalArgumentException(error);
            }
        }
    }

    private static void checkSimpleMethods(Method[] methods) {
        if (Objects.isNull(methods)) {
            return;
        }
        for (var method : methods) {
            if (Modifier.isStatic(method.getModifiers())) {
                String error = "Method = \"" + method.getName() + "()\" is STATIC";
                System.out.println(error);
                throw new IllegalArgumentException(error);
            }
            else if (Modifier.isAbstract(method.getModifiers())) {
                String error = "Method = \"" + method.getName() + "()\" is ABSTRACT";
                System.out.println(error);
                throw new IllegalArgumentException(error);
            }
        }
    }

    private static String getClassSimpleName(Class<?> c) {
        return Objects.isNull(c) ? null : "\"" + c.getSimpleName() + "\"";
    }
}
