package test;

import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ProductionTest {
    public static void main(String[] args) {
        //流水线上有5个工人
        final ProductionChannel channel = new ProductionChannel(1);
        AtomicInteger prductionNo = new AtomicInteger();
        //流水线上有8个工人在网传送带上不断地防止等待加工的半成品
        IntStream.range(1, 100).forEach(i -> new Thread(() -> {
            while (true) {
                channel.offProduction(new Production(prductionNo.getAndIncrement()));
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start());
    }
}
