package threadPool;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue {
    //任务队列的最大容量，在构造时传入
    private final int limit;
    //当队列中的任务已经满了，则需要执行局决策略
    private final DenyPolicy denyPolicy;
    //存放任务的队列
    private final LinkedList<Runnable> runnableList = new LinkedList<>();

    private final ThreadPool threadPool;

    public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }

    @Override
    public void offer(Runnable runnable) {
        synchronized (runnableList) {
            if (runnableList.size() > limit) {
                //无法容纳新的任务时执行局决策略
                denyPolicy.reject(runnable, threadPool);
            } else {
                //否则将任务加入到队尾，并且唤醒阻塞中的线程
                runnableList.addLast(runnable);
                runnableList.notifyAll();
            }
        }
    }

    @Override
    public Runnable take() throws InterruptedException {
        synchronized (runnableList) {
            while (runnableList.isEmpty()) {
                try {
                    //如果队列任务中没有可执行的任务，则当前线程会挂起，进入runnable关联的monitor.waitset中等待唤醒（新的任务加入时唤醒）
                    runnableList.wait();
                } catch (InterruptedException e) {
                    //被中断时应该将该异常抛出
                    throw e;
                }
            }
            //从任务队列头部移除一个任务
            return runnableList.removeFirst();
        }
    }

    @Override
    public int size() {
        //返回当前队列中的任务数
        return runnableList.size();
    }
}
