package test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Registry {
    //存储Subscriber集合和topic之间关机的map
    private final ConcurrentHashMap<String, ConcurrentLinkedDeque<Subscriber>> subscriberContainer
            = new ConcurrentHashMap<>();

    public void bind(Object subscriber) {
        //获取Subscriber object的方法合集然后进行绑定
        List<Method> subscribeMethods = getSubScribeMethods(subscriber);
        subscribeMethods.forEach(m -> tieSubscriber(subscriber, m));
    }

    public void unbind(Object subscriber) {
        //unbind为了提高速度，只对Subscriber进行失效操作
        subscriberContainer.forEach((key, queue) -> queue.forEach(s -> {
            if (s.getSubscribeObject() == subscriber) {
                s.setDisable(true);
            }
        }));
    }

    public ConcurrentLinkedDeque<Subscriber> scanSubscriber(final String topic) {
        return subscriberContainer.get(topic);
    }

    private void tieSubscriber(Object subscriber, Method method) {
        final Subscribe subscribe = method.getDeclaredAnnotation(Subscribe.class);
        String topic = subscribe.topic();
        //当某个topic没有Subscriber Queue的时候创建一个
        if (subscriberContainer.get(topic)==null){
            subscriberContainer.put(topic,new ConcurrentLinkedDeque<>());
        }
        //创建一个Subscriber并且加入Subscriber列表中
        subscriberContainer.get(topic).add(new Subscriber(subscriber, method));
    }

    private List<Method> getSubScribeMethods(Object subscriber) {
        final List<Method> methods = new ArrayList<>();
        Class<?> temp = subscriber.getClass();
        //不断地获取当前类和父类的所有@Subscribe标识的方法
        while (temp != null) {
            //获取所有方法
            Method[] declareMethods = temp.getDeclaredMethods();
            //只有public方法&&有一个入参&&是被@Subscribe标记的方法才符合回调方法
            Arrays.stream(declareMethods)
                    .filter(m -> m.isAnnotationPresent(Subscribe.class) && m.getParameterCount() == 1 && m.getModifiers() == Modifier.PUBLIC)
                    .forEach(methods::add);
            temp = temp.getSuperclass();
        }
        return methods;
    }
}
