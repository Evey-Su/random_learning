package test;

public class FutureTask<T> implements Future<T> {
    private T result;
    private boolean isDone = false;
    private final Object LOCK = new Object();

    @Override
    public T get() throws InterruptedException {
        synchronized (LOCK) {
            //当任务还没有完成时,调用get方法会被挂起而进入阻塞
            while (!done()) {
                LOCK.wait();
            }
            return result;
        }
    }

    @Override
    public boolean done() {
        return isDone;
    }

    //finish方法主要用于为futureTask设置计算结果
    protected void finish(T result) {
        synchronized (LOCK) {
            //balking设计模式
            if (isDone) {
                return;
            }
            //计算完成，为result指定结果，并且将isDone设为true，同时唤醒阻塞中的线程
            this.result = result;
            this.isDone = true;
            LOCK.notifyAll();
        }
    }
}
