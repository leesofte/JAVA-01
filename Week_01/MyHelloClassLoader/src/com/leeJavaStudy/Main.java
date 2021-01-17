package com.leeJavaStudy;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            String path = new File("").getCanonicalPath();
            URL url = new URL("file:\\"+path+"\\");
            System.out.println("当前项目路径:" + url.getPath());
            String className = "Hello";
            ClassLoader cl = new XlassURLClassLoader(url);
            Class c = cl.loadClass(className);
            Object action = c.newInstance();
            Method method = action.getClass().getMethod("hello");
            method.invoke(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
