package test;

import java.util.concurrent.TimeUnit;

public class SynDefect {
    public synchronized void syncMethod(){
        try{
            System.out.println("当前线程："+Thread.currentThread().getName());
            TimeUnit.HOURS.sleep(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        SynDefect defect = new SynDefect();
        Thread t1 = new Thread(defect::syncMethod,"T1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        Thread t2 = new Thread(defect::syncMethod,"T2");
        t2.start();

        t2.interrupt();
        System.out.println(t2.isInterrupted());
        System.out.println(t2.getState());
    }
}
