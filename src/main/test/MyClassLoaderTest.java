package test;

import java.lang.reflect.Method;

public class MyClassLoaderTest {
    public static void main(String[] args) throws Exception {
        //声明一个MyClassLoader
        //指定父加载器为扩展加载器
        ClassLoader extClassLoader = MyClassLoaderTest.class.getClassLoader().getParent();
        MyClassLoader classLoader = new MyClassLoader(null, "/Users/eveysu/Documents/classload");
        //使用class加载Test 需要删掉原来路径的test.class
        Class<?> aClass = classLoader.loadClass("test.Test");
        System.out.println(aClass.getClassLoader());

        Object helloWorld = aClass.newInstance();
        System.out.println(helloWorld);

        Method welcomeMethod = aClass.getMethod("welcome");
        String result = (String) welcomeMethod.invoke(helloWorld);
        System.out.println("result=" + result);

    }
}
