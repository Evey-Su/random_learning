package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class MultiJobExcutor {
    private java.util.concurrent.CountDownLatch countDownLatch;
    private List<Task> tasks;
    private  static ExecutorService executor = null;

    public MultiJobExcutor(List<Task> tasks) {
        countDownLatch = new java.util.concurrent.CountDownLatch(tasks.size());
        executor = Executors.newFixedThreadPool(tasks.size());
        this.tasks = tasks;
    }

    public void execute() {
        if (tasks == null|| tasks.isEmpty()) {
            return;
        }
        List<java.util.concurrent.FutureTask<String>> futureTaskList = new ArrayList<>();
        for (int i=0;i< tasks.size();i++) {
            futureTaskList.add(new FutureTask<>(new Job(countDownLatch,tasks.get(i))));
//            executor.submit(new Job(countDownLatch,tasks.get(i)));
//            System.out.println("xx"+i);
        }
        for (int i=0;i< tasks.size();i++){
            executor.execute(futureTaskList.get(i));
            try {
                System.out.println(futureTaskList.get(i).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        try {
            //等待所有线程结束
            countDownLatch.await(15, TimeUnit.SECONDS);
            //执行其他操作
//            System.out.println("it's over");
            //关闭线程池
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class Job implements Callable<String> {
        private java.util.concurrent.CountDownLatch latch;
        private Task task;

        public Job(CountDownLatch latch, Task task) {
            this.latch = latch;
            this.task = task;
        }

        @Override
        public String call() throws Exception {
//            System.out.println(System.currentTimeMillis());
            //执行线程
            task.execute();
            //countDown自减
            latch.countDown();
            return "sulinlin";
        }
    }
    private static class Task{
        private String str;

        public Task(String str) {
            this.str = str;
        }

        public void execute(){
//            System.out.println(str);
        }

    }

    public static void main(String[] args) {
        Task task = new Task("I");
        Task task1 = new Task("love");
        Task task2 = new Task("you");
        Task task3 = new Task(",");
        Task task4 = new Task("its");
        Task task5 = new Task("not");
        Task task6 = new Task("true");

        List<Task> tasks = new ArrayList<Task>();
        tasks.add(task);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);
        tasks.add(task6);

        MultiJobExcutor multiJobExecutors = new MultiJobExcutor(tasks);
        multiJobExecutors.execute();
    }
}

