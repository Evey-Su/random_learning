package test;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadLocalExample {
    public static void main(String[] args) {
        //创建ThreadLocal实例
        ThreadLocal<Integer> tlocal=new ThreadLocal<>();
        //创建十个线程使用tlocal
        IntStream.range(0,10).forEach(i->new Thread(()->{
            try {
                //每个线程都会设置tlocal,但是彼此之前的数据是独立的
                tlocal.set(i);
                System.out.println(Thread.currentThread()+"set i "+tlocal.get());
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread()+"set i "+tlocal.get());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start());
    }
}
