package com.leeJavaStudy;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;

public class XlassURLClassLoader extends URLClassLoader {

    URL url = null;
    // 指定类加载器
    public XlassURLClassLoader(URL url) {
        super(new URL[]{url});
        this.url = url;
    }

    @Override
    public Class<?> findClass(String fullName) throws ClassNotFoundException {
        try {
            //加载自定义类数据
            byte [] classData = getXlassFileData(fullName, this.url.getPath());
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