package test;

import java.util.Map;

public class FindOrderDetailMessage extends MethodMessage {

    public FindOrderDetailMessage(Map<String, Object> params, OrderService orderService) {
        super(params, orderService);
    }

    @Override
    public void execute() {
        //执行orderService中findOrderDetail方法
        Future<String> realFuture = orderService.findOrderDetails((Long) params.get("orderId"));
        ActiveFuture<String> activeFuture = (ActiveFuture<String>) params.get("activeFuture");
        try {
            //调用future get方法，阻塞到findorderDetails方法完全结束
            String result = realFuture.get();
            //执行结束后，通过finish方法传递给activeFuture
            activeFuture.finish(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
            activeFuture.finish(null);
        }
    }
}
