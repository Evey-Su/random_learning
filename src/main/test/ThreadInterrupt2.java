package test;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupt2 {
    public static void main(String[] args) {
        System.out.println("main is interrupted？"+Thread.interrupted());
        Thread.currentThread().interrupt();
        System.out.println("main is interrupted？"+Thread.interrupted());
        System.out.println("main is interrupted？"+Thread.interrupted());
//        System.out.println("main is interrupted? "+Thread.currentThread().isInterrupted());

        try{
            TimeUnit.MINUTES.sleep(1);
        }catch (InterruptedException e){
            System.out.println("i am be interrupted");
        }
    }
}
