package test;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest {
//    不安全
//    public static volatile int race =0;
//    public static void increase(){
//        race++;
//    }
    //安全（结果正确，使用乐观锁）
    public static AtomicInteger race = new AtomicInteger(0);
    public static void increase(){
        race.incrementAndGet();
    }

    private static final int THREADS_COUNT=20;

    public static void main(String args[]){
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i=0;i<THREADS_COUNT;i++){
            threads[i] = new Thread(() -> {
                for (int i1 = 0; i1 <10000; i1++){
                    increase();
                }
            });
            threads[i].start();
        }

        //等待所有累加线程都结束
        while (Thread.activeCount()>2){
            Thread.currentThread().getThreadGroup().list();
            Thread.yield();
        }
        System.out.println("final="+race);
    }
}
