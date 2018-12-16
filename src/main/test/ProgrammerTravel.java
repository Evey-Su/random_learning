package test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ProgrammerTravel extends Thread {
    //门阀
    private final Latch latch;
    //程序员
    private final String programmer;
    //交通工具
    private final String transporttation;

    public ProgrammerTravel(Latch latch, String programmer, String transporttation) {
        this.latch = latch;
        this.programmer = programmer;
        this.transporttation = transporttation;
    }

    @Override
    public void run() {
        System.out.println(programmer + " start take the transportation:" + transporttation);
        try {
            //乘坐交通工具花费在路上的时间使用随机数字进行模拟
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(programmer + " arrived by " + transporttation);
        latch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        Latch latch = new CountDownLatch(4);
        new ProgrammerTravel(latch, "Alex", "Bus").start();
        new ProgrammerTravel(latch, "Evey", "Bicycle").start();
        new ProgrammerTravel(latch, "Maggie", "Taxi").start();
        new ProgrammerTravel(latch, "Jelly", "Subway").start();
        try {
            latch.await(TimeUnit.SECONDS, 5);
            System.out.println("== all of programmer arrived ==");
        } catch (WaitTimeoutException e) {
            e.printStackTrace();
        }
    }
}
