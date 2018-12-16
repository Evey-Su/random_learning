package test;

import java.util.concurrent.TimeUnit;

public class OrderServiceImpl implements OrderService {
    @ActiveMethod
    @Override
    public Future<String> findOrderDetails(long orderId) {
        return FutureService.<Long, String>newService().submit(input -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "the order details information";
        }, orderId, System.out::println);
    }

    @ActiveMethod
    @Override
    public void order(String account, long orderId) {
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("process the order for account " + account + ",orderId" + orderId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
