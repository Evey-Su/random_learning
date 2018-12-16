package test;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupt {
    public static void main(String[] args){
        Thread thread = new Thread(()->{
            try {
                TimeUnit.MINUTES.sleep(1);
            }catch (InterruptedException e){
                System.out.println("oh,i am be interrupted.");
            }
        });
        thread.start();
        //short block and make sure thread is started
        thread.interrupt();
    }
}
