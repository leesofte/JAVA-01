package com.leeJavaStudy;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;

public class XlassURLClassLoader extends URLClassLoader {

    // 指定类加载器
    public XlassURLClassLoader() {
        super(new URL[0]);
    }

    @Override
    public Class<?> findClass(String fullName) throws ClassNotFoundException {
        try {
            //从当前类运行目录，加载自定义类数据
            String pathpath = "";
            try {
                pathpath = URLDecoder.decode(this.getClass().getResource("/").getPath(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("当前项目CLASSPATH目录：" + pathpath);
            byte [] classData = getXlassFileData(fullName, pathpath);
            fullName = fullName.replace('.', '/');
            String[] names = fullName.split("/");
            String className = "";
            if (names.length > 0) {
                className = names[names.length-1];
            }
            //xlass数据解密
            classData = decode(classData);
            if(classData != null){
                //defineClass方法将字节码转化为类
                return defineClass(className, classData,0, classData.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(fullName);
    }

    public byte[] getXlassFileData(String fullName, String path) {
        String classPath = path + fullName.replace('.', '/') + ".xlass";
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(classPath);
            out = new ByteArrayOutputStream();
            int data;
            while ((data = in.read()) != -1) {
                out.write(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public byte[] decode(byte[] xlass) {
        for (int i = 0; i < xlass.length; i++) {
            xlass[i] = (byte) (255 - xlass[i]);
        }
        return xlass;
    }

}