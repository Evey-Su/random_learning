package test;

public interface OrderService {
    Future<String> findOrderDetails(long orderId);

    //提交订单，没有返回值
    void order(String account, long orderId);
}
