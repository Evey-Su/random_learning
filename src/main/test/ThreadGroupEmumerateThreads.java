package test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ThreadGroupEmumerateThreads {
    public static void main(String[] args) throws InterruptedException {
        //创建一个新的group
        ThreadGroup myGroup = new ThreadGroup("MyGroup");
        System.out.println(myGroup.getParent().getName());
        //创建线程并加入myGroup
        Thread thread = new Thread(myGroup, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "MyThread");
        thread.start();
        TimeUnit.MILLISECONDS.sleep(2);
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        System.out.println("active thread count:" + mainGroup.activeCount());
        Thread[] list = new Thread[mainGroup.activeCount()];
        int recurseSize = mainGroup.enumerate(list, false);
        System.out.println("recurseSize" + recurseSize);
        System.out.println(list.length);
        for (int i = 0; i < recurseSize; i++) {
            if (list[i] != null) {

                System.out.println(list[i].getName());
            }
        }

        recurseSize = mainGroup.enumerate(list, false);
        System.out.println("recurseSize:" + recurseSize);
        for (int i = 0; i < recurseSize; i++) {
            if (list[i] != null) {

                System.out.println(list[i].getName());
            }
        }

    }

}
