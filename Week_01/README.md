学习笔记

第一周作业：
（一）自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。
开发环境：IDE:IDEA_2020.2.3/JDK1.8.0_231
项目名称：MyHelloClassLoader
代码内容：重写了findClass，在自定义的ClassLoader调用loadClass时，如果父加载器都找不到该类的时候，就进入自定义加载器重写的findClass中，按自定义规则寻找并定义指定类。


（二）画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系。
![Image text](https://github.com/leesofte/JAVA-01/blob/main/Week_01/JVM%20Options%20and%20Memory%20Structure.png)
