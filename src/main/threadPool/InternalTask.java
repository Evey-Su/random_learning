package threadPool;

public class InternalTask implements Runnable {
    private final RunnableQueue runnableQueue;
    private volatile boolean running = true;

    public InternalTask(RunnableQueue runnableQueue) {
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void run() {
        //如果当前任务为running且没有被中断，则将其不懂从queue中取出runnable,然后执行
        while (running && !Thread.currentThread().isInterrupted()) {
            Runnable task = null;
            try {
                task = runnableQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            task.run();
        }
    }

    //停止当前任务，主要会在线程池的shutDown方法中使用
    public void stop() {
        this.running = false;
    }
}
