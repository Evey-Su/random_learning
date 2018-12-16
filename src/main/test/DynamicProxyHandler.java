package test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyHandler implements InvocationHandler {
    private Object proxied; // 代理对象

    public DynamicProxyHandler(Object proxied) {
        // TODO Auto-generated constructor stub
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        System.out.println("代理类: " + proxy.getClass() + "\n"
                + "代理方法: " + method + "\n"
                + "参数: " + args);
        if (args != null)
            for (Object arg : args)
                System.out.println(" " + arg);
        return method.invoke(proxied, args);
    }
}

interface Interface {
    void doSomething();
}

class RealObject implements Interface {

    @Override
    public void doSomething() {
        // TODO Auto-generated method stub
        System.out.println("doSomething");
    }

}

class DynamicProxyDemo {
    public static void consumer(Interface iface) {
        iface.doSomething();
    }

    public static void main(String[] args) {
        RealObject realObject = new RealObject();
        // 使用动态代理
        Interface proxy = (Interface) Proxy.newProxyInstance(
                Interface.class.getClassLoader(),
                new Class[]{Interface.class},
                new DynamicProxyHandler(realObject));
        consumer(proxy);
    }
}
