package test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ActiveServiceFactory {
    private final static ActiveMessageQueue queue = new ActiveMessageQueue();

    public static <T> T active(T instance) {
        //生成service的代理类
        Object proxy = Proxy.newProxyInstance(instance.getClass().getClassLoader(), instance.getClass().getInterfaces(), new ActiveInvocationHandler<>(instance));
        return (T) proxy;
    }

    //ActiveInvocationHandler是invocationHandler的子类，生成Proxy时需要使用到
    private static class ActiveInvocationHandler<T> implements InvocationHandler {
        private final T instance;

        ActiveInvocationHandler(T instance) {
            this.instance = instance;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //如果接口方法被@ActiveMessage标价机，则会转为ActiveMessage
            if (method.isAnnotationPresent(ActiveMethod.class)) {
                //检查该方法是否符合规范,否则会抛出异常
                this.checkMethod(method);
                ActiveMessage.Builder builder = new ActiveMessage.Builder();
                //并不知道为啥要这样写，构造函数不是可以一步到位的吗
                builder.userMethod(method).withObjects(args).forService(instance);
                Object result = null;
                if (this.isReturnFutureType(method)) {
                    //如果有返回值
                    result = new ActiveFuture<>();
                    builder.returnFuture((ActiveFuture) result);
                }
                //将ActiveMessage加入队列中
                queue.offer(builder.build());
                return result;
            } else {
                //如果是普通方法（没有使用ActiveMethod标记）,则会正常执行
                return method.invoke(instance, args);
            }
        }

        //检查有返回值的方法是否为future 否则会抛出IllegalActiveMethod异常
        private void checkMethod(Method method) throws IllegalActiveMethodException {
            //有返回值，必须是ActiveFuture类型的返回值
            if (!isRetureVoidType(method) && !isReturnFutureType(method)) {
                throw new IllegalActiveMethodException("the method [" + method.getName() + "] return type must be void/Future");
            }
        }

        //判断方法是否是Future返回类型
        private boolean isReturnFutureType(Method method) {
            return method.getReturnType().isAssignableFrom(Future.class);
        }

        //判断方法是否无返回类型
        private boolean isRetureVoidType(Method method) {
            return method.getReturnType().equals(Void.TYPE);
        }
    }

    public static void main(String[] args) throws InterruptedException{
        OrderService orderService = active(new OrderServiceImpl());
        Future<String> future = orderService.findOrderDetails(77897);
        System.out.println("i will be returned immediately");
        System.out.println(future.get());
    }
}
