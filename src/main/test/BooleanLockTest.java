package test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class BooleanLockTest {

    //定义BooleanLock

    private final BooleanLock lock = new BooleanLock();
//    private final ReentrantLock lock = new BooleanLock();

    //使用try finally语句保证lock每次都能被正确释放
    public void synMethod() throws InterruptedException {
        //加锁
        lock.lock();
//        lock.unlock();
        try {
            int randomInt = ThreadLocalRandom.current().nextInt(10);
            System.out.println(Thread.currentThread() + " get the lock.");
            TimeUnit.SECONDS.sleep(randomInt);
            System.out.println("sleep end "+randomInt+" seconds.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("release the lock");
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        BooleanLockTest blt = new BooleanLockTest();
        //定义10个线程并启动
        for (int i=0;i<3;i++){
            new Thread(() -> {
                try {
                    blt.synMethod();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
