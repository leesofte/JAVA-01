package com.leeJavaStudy;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;

public class XlassURLClassLoader extends URLClassLoader {

    String projectPath = "";
    // 指定类加载器
    public XlassURLClassLoader() {
        super(new URL[0]);
        projectPath = getProjectPath();
    }

    @Override
    public Class<?> findClass(String fullName) throws ClassNotFoundException {
        try {
            System.out.println("当前项目目录：" + this.projectPath);
            //获取字节码
            byte [] classData = getXlassFileData(fullName, this.projectPath);
            //字节码文件名解析
            String className = getClassName(fullName);
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

    public byte[] getXlassFileData(String fullName, String path) throws IOException {
        String classPath = path + fullName.replace('.', '/') + ".xlass";
        InputStream in = null;
        ByteArrayOutputStream out = null;
        byte[] result = null;
        try {
            in = new FileInputStream(classPath);
            out = new ByteArrayOutputStream();
            int data;
            while ((data = in.read()) != -1) {
                out.write(data);
            }
            result = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
            out.close();
        }
        return result;
    }

    public byte[] decode(byte[] xlass) {
        for (int i = 0; i < xlass.length; i++) {
            xlass[i] = (byte) (255 - xlass[i]);
        }
        return xlass;
    }

    public String getProductionPath() {
        //从当前类运行目录加载自定义类数据，编码：UTF-8。中文路径乱码问题
        String productionUTF8Path = "";
        try {
            String productionPath = this.getClass().getResource("/").getPath();
            productionUTF8Path = URLDecoder.decode(productionPath,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return productionUTF8Path;
    }

    public String getProjectPath() {
        //从当前项目目录加载自定义类数据，编码：UTF-8。解决中文路径乱码问题
        String projectUTF8Path = "";
        try {
            String projectPath = new File("").getCanonicalPath();
            projectUTF8Path = URLDecoder.decode(projectPath,"UTF-8");
            projectUTF8Path = projectPath + "\\";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectUTF8Path;
    }

    public String getClassName(String fullName) {
        //从com.leeJavaStudy.Hello中获取真正的Hello类名
        fullName = fullName.replace('.', '/');
        String[] names = fullName.split("/");
        String className = "";
        if (names.length > 0) {
            className = names[names.length-1];
        }
        return className;
    }

}