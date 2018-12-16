package test;

public class OrderServiceFactory {
    //将queue定义成static,保证其再在整个jvm进程中是惟一的，并且ActiveDaemonThread会在此刻启动
    private final static ActiveMessageQueue activeMessageQueue = new ActiveMessageQueue();
    //不允许外部通过new的方式构建

    private OrderServiceFactory() {
    }

    //返回proxy
    public static OrderService toActiveObject(OrderService orderService) {
        return new OrderServiceProxy(orderService, activeMessageQueue);
    }

    public static void main(String[] args) throws InterruptedException{
        OrderService orderService = OrderServiceFactory.toActiveObject(new OrderServiceImpl());
        orderService.order("hello", 3434);
        orderService.findOrderDetails(889);
        System.out.println("return immediately");
        Thread.currentThread().join();
    }

//    public static int select(int n) {
//        int total = 0;
//        for (int i = 0; i <=n; i++) {
//            int k = (n - i) % 2;
//            int v = (n - i) / 2;
//            if (k == 0) {
//                total = total + C(i, v + i);
//            }
//        }
//        return total;
//    }
}
