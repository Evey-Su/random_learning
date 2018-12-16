package test;

import test.*;

import java.util.HashMap;
import java.util.Map;

public class OrderServiceProxy implements OrderService {

    private final OrderService orderService;
    private final ActiveMessageQueue activemessageQueue;

    public OrderServiceProxy(OrderService orderService, ActiveMessageQueue activemessageQueue) {
        this.orderService = orderService;
        this.activemessageQueue = activemessageQueue;
    }

    @Override
    public Future<String> findOrderDetails(long orderId) {
        //定义一个acctivefuture ，并且可支持立即返回
        final ActiveFuture<String> activeFuture = new ActiveFuture<>();
        //收集方法入参以及返回的activeFuture封装成MethodMessage
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("activeFuture", activeFuture);
        MethodMessage message = new FindOrderDetailMessage(params, orderService);
        //将MethodMessage保存到messageQueue中去
//        activemessageQueue.offer(message);
        return activeFuture;
    }

    @Override
    public void order(String account, long orderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("account", account);
        params.put("orderId", orderId);
        MethodMessage message = new OrderMessage(params, orderService);
//        activemessageQueue.offer(message);
    }
}
