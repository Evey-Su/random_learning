package test;

import java.util.concurrent.TimeUnit;

public class FutureTest {
    public static void main(String[] args) throws InterruptedException {
        //定义不需要返回值的
        FutureService<String,Integer> service = FutureService.newService();
        Future<Integer> future = service.submit(input->{
            try {
                TimeUnit.SECONDS.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(" I am finish done.");
            return input.length();
        },"hello");
        System.out.println(future.get());
    }
}
