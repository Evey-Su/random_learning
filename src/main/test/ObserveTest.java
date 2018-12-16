package test;

import java.util.concurrent.TimeUnit;

public class ObserveTest {
    public static void main(String[] args) {
        final TaskLifeCycle<String> lifeCycle = new TaskLifeCycle.EmptyLifeCycle<String>() {
            public void onFinish(Thread thread, String result) {
                System.out.println("The result is " + result);
            }
        };

        new ObservableThread<>(lifeCycle,()->{
            try {
                TimeUnit.SECONDS.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("finished done");
            return "hello observer";
        }).start();

    }
}
