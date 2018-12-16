package test;

public class ObservableThread<T> extends Thread implements Observable {
    private final TaskLifeCycle<T> lifeCycle;
    private final ATask<T> ATask;
    private Cycle cycle;

    //指定Task的实现，默认情况下使用EmptyLifeCycle
    public ObservableThread(ATask<T> ATask) {
        this(new TaskLifeCycle.EmptyLifeCycle<>(), ATask);
    }

    public ObservableThread(TaskLifeCycle<T> lifeCycle, ATask<T> ATask) {
        super();
        //task不允许为null
        if (ATask == null) {
            throw new IllegalArgumentException("The ATask is required.");
        }
        this.lifeCycle = lifeCycle;
        this.ATask = ATask;
    }

    @Override
    public Cycle getCycle() {
        return null;
    }

    @Override
    public final void run() {
        //在执行线程逻辑单元的时候，分别触发相应的事件
        this.update(Cycle.STARTED, null, null);
        try {
            this.update(Cycle.RUNNING, null, null);
            T result = this.ATask.call();
            this.update(Cycle.DONE, result, null);
        } catch (Exception e) {
            this.update(Cycle.ERROR, null, e);
        }
    }

    private void update(Cycle cycle, T result, Exception e) {
        this.cycle = cycle;
        if (lifeCycle == null) {
            return;
        }
        try {
            switch (cycle) {
                case STARTED:
                    this.lifeCycle.onStart(Thread.currentThread());
                    break;
                case RUNNING:
                    this.lifeCycle.onRunning(Thread.currentThread());
                    break;
                case DONE:
                    this.lifeCycle.onFinish(Thread.currentThread(), result);
                    break;
                case ERROR:
                    this.lifeCycle.onError(Thread.currentThread(), e);
                    break;
            }
        } catch (Exception ex) {
            if (cycle == Cycle.ERROR) {
                throw ex;
            }
        }
    }
}
