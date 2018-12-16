package test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadCounter extends Thread {
    final static AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void run() {
        try {
            System.out.println("the " + counter.getAndIncrement() + " thread be created.");
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            while (true) {
                new ThreadCounter().start();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("fail at=>" + counter.get());
        }
    }
}
