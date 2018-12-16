package test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Worker extends Thread {
    private final ProductionChannel channel;
    private final static Random random = new Random(System.currentTimeMillis());

    public Worker(String workerName, ProductionChannel channel) {
        super(workerName);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //从传送带上获取产品
                Production production = channel.takeProduction();
                System.out.println(Thread.currentThread().getName() + " process the " + production);
                production.create();
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
