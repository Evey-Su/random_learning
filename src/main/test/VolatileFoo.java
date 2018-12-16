package test;

import java.util.concurrent.TimeUnit;

public class VolatileFoo {
    private final static int MAX = 5;
    private static int initValue = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            while (initValue < MAX) {
                System.out.println("the initValue is updated to " + initValue);
            }
        }, "Reader").start();

        new Thread(() -> {
            while (initValue < MAX) {
                System.out.println("the initValue will be changed to " + (++initValue));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Updater").start();
    }
}
