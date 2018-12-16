package test;

public class EventBusTest {
    @Subscribe
    public void method1(String message) {
        System.out.println("==method1==" + message);
    }
    @Subscribe
    public void method2(String message) {
        System.out.println("==method2==" + message);
    }

    public static void main(String[] args) {
        Bus bus = new EventBus("TestBus");
        bus.register(new EventBusTest());
        bus.post("hello");
        bus.post("world");
    }
}
