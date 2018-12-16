package test;

import java.util.concurrent.TimeUnit;

public class TryConcurrency {

    private static void sleep(int seconcs){
        try {
            TimeUnit.SECONDS.sleep(seconcs);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private static void browseNews(){
        for (;;){
            System.out.println("the good news");
            sleep(1);
        }
    }

    private static void enjoyMusic(){
        for (;;){
            System.out.println("the nice music");
            sleep(1);
        }
    }

    public static void main(String args[]){
        //通过匿名内部类的方式创建线程，并且重写其中的run方法
        //注意线程启动必须在其中一个任务之前，否则线程将永远得不到启动，因为前一个任务永远不会结束
        new Thread(TryConcurrency::enjoyMusic).start();
        browseNews();
    }
}
