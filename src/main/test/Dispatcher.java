package test;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class Dispatcher {
    private final Executor executorService;
    private final EventExceptionHandler exceptioHandler;

    public static final Executor SEQ_EXCUTOR_SERVICE = SeqExecutorService.INSTANCE;
    public static final Executor PRE_THREAD_EXCUTOR_SERVICE = PreThreadExecutorService.INSTANCE;

    public Dispatcher(Executor executorService, EventExceptionHandler exceptioHandler) {
        this.executorService = executorService;
        this.exceptioHandler = exceptioHandler;
    }

    public void dispatch(Bus bus, Registry registry, Object event, String topic) {
        //根据topic获取所有的Subscribe列表
        ConcurrentLinkedDeque<Subscriber> subscribers = registry.scanSubscriber(topic);
        if (null == subscribers) {
            if (exceptioHandler != null) {
                exceptioHandler.handle(new IllegalArgumentException("The topic " + topic + " not bind yet"), new BaseEventContext(bus.getBusName(), null, event));
            }
            return;
        }
        //遍历所有方法，并且通过反射方式进行方法调用
        subscribers.stream().filter(subscriber -> !subscriber.isDisable())
                .filter(subscriber -> {
                    Method subscribeMethod = subscriber.getSubscribeMethod();
                    Class<?> aClass = subscribeMethod.getParameterTypes()[0];
                    return (aClass.isAssignableFrom(event.getClass()));
                }).forEach(subscriber -> realInvokeSubscribe(subscriber, event, bus));
    }

    private void realInvokeSubscribe(Subscriber subscriber, Object event, Bus bus) {
        Method subscribeMethod = subscriber.getSubscribeMethod();
        Object subscribeObject = subscriber.getSubscribeObject();
        executorService.execute(() -> {
            try {
                subscribeMethod.invoke(subscribeObject, event);
            } catch (Exception e) {
                if (null != exceptioHandler) {
                    exceptioHandler.handle(e, new BaseEventContext(bus.getBusName(), subscriber, event));
                }
            }
        });
    }

    public void close() {
        if (executorService instanceof ExecutorService) {
            ((ExecutorService) executorService).shutdown();
        }
    }

    static Dispatcher newDispatcher(EventExceptionHandler exceptioHandler, Executor executor) {
        return new Dispatcher(executor, exceptioHandler);
    }

    static Dispatcher seqDispatcher(EventExceptionHandler exceptioHandler) {
        return new Dispatcher(SEQ_EXCUTOR_SERVICE, exceptioHandler);
    }

    static Dispatcher preThreadDispatcher(EventExceptionHandler exceptioHandler) {
        return new Dispatcher(PRE_THREAD_EXCUTOR_SERVICE, exceptioHandler);
    }

    //顺序执行的ExecutorService
    private static class SeqExecutorService implements Executor {
        private final static SeqExecutorService INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    //每个线程负责一次消息推送
    private static class PreThreadExecutorService implements Executor {
        private final static PreThreadExecutorService INSTANCE = new PreThreadExecutorService();

        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }

    private static class BaseEventContext implements EventContext {
        private final String eventBusName;
        private final Subscriber subscriber;
        private final Object event;

        public BaseEventContext(String eventBusName, Subscriber subscriber, Object event) {
            this.eventBusName = eventBusName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public String getSource() {
            return this.eventBusName;
        }

        @Override
        public Object getSubscriber() {
            return subscriber != null ? subscriber.getSubscribeObject() : null;
        }

        @Override
        public Method getSubscribe() {
            return subscriber != null ? subscriber.getSubscribeMethod() : null;
        }

        @Override
        public Object getEvent() {
            return this.event;
        }
    }
}
