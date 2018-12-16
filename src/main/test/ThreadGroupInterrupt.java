package test;

import java.util.concurrent.TimeUnit;

public class ThreadGroupInterrupt {
    public static void main(String[] args) throws InterruptedException{
        ThreadGroup group = new ThreadGroup("TestGroup");
        new Thread(group,()->{
            while (true){
                try {
                    TimeUnit.MILLISECONDS.sleep(2);
                }catch (InterruptedException e){
                    break;
                }
            }
            System.out.println("t1 exit out.");
        },"t1").start();

        new Thread(group,()->{
            while (true){
                try {
                    TimeUnit.MILLISECONDS.sleep(2);
                }catch (InterruptedException e){
                    break;
                }
            }
            System.out.println("t2 exit out.");
        },"t2").start();

        TimeUnit.MILLISECONDS.sleep(2);
        group.interrupt();
    }
}
