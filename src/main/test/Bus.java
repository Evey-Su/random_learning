package test;

public interface Bus {

    //将某个对象注册到Bus上，从此之后该类就成为Subscriber了
    void register(Object subscriber);
    //将某个对象从bus上取消注册，取消注册之后就不会再接收来自bus的任何消息了
    void unregister(Object subscriber);
    //提交event到默认的topic
    void post(Object event);
    //提交event到指定的topic
    void post(Object event,String topic);
    //关闭该bus
    void close();
    //返回bus的名称
    String getBusName();
}
