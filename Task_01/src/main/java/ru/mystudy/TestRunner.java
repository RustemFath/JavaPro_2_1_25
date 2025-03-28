package ru.mystudy;

import ru.mystudy.annotations.AfterSuite;
import ru.mystudy.annotations.BeforeSuite;
import ru.mystudy.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {
    public static void runTests(Class<?> c) {
        if (Objects.isNull(c)) {
            throw new IllegalArgumentException("Parameter c is null");
        }

        Method[] beforeSuiteMethods = getMethodsWithAnnotation(c, BeforeSuite.class);
        Method[] afterSuiteMethods = getMethodsWithAnnotation(c, AfterSuite.class);
        if (beforeSuiteMethods.length > 1) {
            generateThrowByCount(BeforeSuite.class, beforeSuiteMethods);
        }
        if (afterSuiteMethods.length > 1) {
            generateThrowByCount(AfterSuite.class, afterSuiteMethods);
        }

        try {
            Constructor<?> constructor = c.getConstructor();
            Object obj = constructor.newInstance();

            // BeforeSuite
            if (beforeSuiteMethods.length > 0) {
                beforeSuiteMethods[0].invoke(obj);
            }

            // Test
            Method[] testMethods = getMethodsWithAnnotation(c, Test.class);
            testMethods = getSortedByPriorityTestMethods(testMethods);
            invokeSortedTestMethods(obj, testMethods);

            // AfterSuite
            if (afterSuiteMethods.length > 0) {
                afterSuiteMethods[0].invoke(obj);
            }

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
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
            map.put(method.getAnnotation(Test.class).priority(), method);
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
        for (var method : methods) {
            method.invoke(obj);
        }
    }

    private static void generateThrowByCount(Class<? extends Annotation> annotationClass, Method[] methods) {
        String error = "count methods with annotation = " + annotationClass.getSimpleName() +
                " more that 1, count = " + methods.length;
        System.out.println(error);
        throw new IllegalArgumentException(error);
    }
}
