package test;

import java.util.concurrent.TimeUnit;

public class ThreadHook {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try {
                System.out.println("The hook thread1 is running.");
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("The hook thread1 will exit");
        }));

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try {
                System.out.println("The hook thread2 is running.");
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("The hook thread2 will exit");
        }));

        System.out.println("the program will stop");
    }
}
