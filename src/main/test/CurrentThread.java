package test;

public class CurrentThread {
    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            System.out.println(Thread.currentThread().getName());
//            System.out.println(Thread.currentThread()== this);
        });
        System.out.println(thread.getId());
        thread.start();
        System.out.println(thread.getId());
        String name = Thread.currentThread().getName();
        System.out.println("main".equals(name));
        System.out.println(Thread.currentThread().getId());

    }
}
