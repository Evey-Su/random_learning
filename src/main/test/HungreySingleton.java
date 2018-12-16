package test;

import threadPool.Singleton;

//不允许被继承
public final class HungreySingleton {
    //实例变量
    private byte[] data = new byte[1024];

    //定义实例对象的嘶吼直接初始化
    private final static HungreySingleton instance = new HungreySingleton();

    //私有构造函数 不允许外部new
    private HungreySingleton(){
    }

    public static HungreySingleton getInstance(){
        return instance;
    }
}
