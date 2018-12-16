package test;

import java.util.concurrent.TimeUnit;

public class ThreadStartTest {

    public static void main(String args[]){
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("start之前的状态="+thread.getState());
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("start之前的状态="+thread.getState());
        thread.start();
    }

}
