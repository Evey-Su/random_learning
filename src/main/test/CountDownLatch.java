package test;

import java.util.concurrent.TimeUnit;

public class CountDownLatch extends Latch {
    public CountDownLatch(int limit) {
        super(limit);
    }

    @Override
    public void await() throws InterruptedException {
        synchronized (this) {
            //当limit>0时，当前线程进入阻塞状态
            while (limit > 0) {
                System.out.println(limit);
                this.wait();
            }
        }
    }

    @Override
    public void await(TimeUnit unit, long time) throws InterruptedException, WaitTimeoutException {
        if (time <= 0) {
            throw new IllegalArgumentException("the time is invalid.");
        }
        long retainingNanos = unit.toNanos(time);//将time转为纳秒
        //等待任务将在endNanos那秒后超时
        final long endNanos = System.nanoTime() + retainingNanos;
        synchronized (this) {
            while (limit > 0) {
                //如果超时抛出异常
                if (TimeUnit.NANOSECONDS.toMillis(retainingNanos) <= 0) {
                    throw new WaitTimeoutException("the wait time over specify time.");
                }
                //等待remainingNanos，在等待的过程中可能会被中断，需要重新计算remaningNanos
                this.wait(TimeUnit.NANOSECONDS.toMillis(retainingNanos));
                retainingNanos = endNanos - System.nanoTime();
            }
        }
    }

    @Override
    public void countDown() {
        synchronized (this) {
            if (limit <= 0) {
                throw new IllegalStateException("All of task already arrived");
            }
            limit--;
            this.notifyAll();
        }
    }

    @Override
    public int getUnarrived() {
        return limit;//这是个评估值，不能保证准确性
    }
}
