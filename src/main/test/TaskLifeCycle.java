package test;

public interface TaskLifeCycle<T> {
    //任务启动时触发onStart方法
    void onStart(Thread thread);

    //任务在运行时触发onRunning方法
    void onRunning(Thread thread);

    //任务在结束时会触发onFinish方法，其中result是任务执行结束后的结果
    void onFinish(Thread thread, T result);

    //任务报错时触发
    void onError(Thread thread, Exception e);

    //生命周期接口的空实现
    class EmptyLifeCycle<T> implements TaskLifeCycle<T> {
        @Override
        public void onStart(Thread thread) {

        }

        @Override
        public void onRunning(Thread thread) {

        }

        @Override
        public void onFinish(Thread thread, T result) {

        }

        @Override
        public void onError(Thread thread, Exception e) {

        }
    }
}
