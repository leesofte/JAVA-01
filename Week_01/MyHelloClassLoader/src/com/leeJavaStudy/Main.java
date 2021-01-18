package com.leeJavaStudy;

import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            String className = "Hello";
            ClassLoader cl = new XlassURLClassLoader();
            Class c = cl.loadClass(className);
            Object action = c.newInstance();
            Method method = action.getClass().getMethod("hello");
            method.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
