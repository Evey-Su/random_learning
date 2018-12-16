package test;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class IntegerAccumulator {
    private int init;

    public IntegerAccumulator(int init) {
        this.init = init;
    }

    //构造新的累加器
    public IntegerAccumulator(IntegerAccumulator accumulator, int init) {
        this.init = accumulator.getInit() + init;
    }

    //每次相加会产生一个新的IntegerAccumulator
    public IntegerAccumulator add(int i) {
        return new IntegerAccumulator(this, i);
    }

    public int getInit() {
        return this.init;
    }

    public static void main(String[] args) {
        IntegerAccumulator accumulator = new IntegerAccumulator(0);

        IntStream.range(0, 3).forEach(i -> new Thread(() -> {
            int inc = 0;
            while (true) {
                int oldValue;
                int result;
                oldValue = accumulator.getInit();
                result = accumulator.add(inc).getInit();
                System.out.println(oldValue + "+" + inc + "=" + result);
                if (inc + oldValue != result) {
                    System.err.println("ERROR:" + oldValue + "+" + inc + "=" + result);
                }
                inc++;
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start());
    }
}
