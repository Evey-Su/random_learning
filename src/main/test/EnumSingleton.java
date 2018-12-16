package test;

import threadPool.Singleton;

//枚举类本省就是final的，不允许被继承
public enum EnumSingleton {
    INSTANCE;
    //实例变量
    private byte[] data = new byte[1024];

    EnumSingleton() {
        System.out.println("instance will be initialized immediately");
    }

    public static void method() {

    }

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        method();
    }
}
