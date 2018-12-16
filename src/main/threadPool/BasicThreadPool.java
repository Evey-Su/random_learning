//package threadPool;
//
//import java.util.ArrayDeque;
//import java.util.Queue;
//import java.util.concurrent.TimeUnit;
//
//public class BasicThreadPool extends  Thread implements ThreadPool {
//    private final int initSize;
//    private final int maxSize;
//    private final int coreSize;
//    private int activeCount;
//    private final ThreadFactory threadFactory;
//    private final RunnableQueue runnableQueue;
//    private volatile boolean isShutdown=false;
//    //工作线程队列
//    private final Queue<Thread> threadQueue = new ArrayDeque<>();
//    private final static DenyPolicy DEFAULT_DENY_POLICY= new DenyPolicy.DiscardDenyPolicy();
//    private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();
//    private final long keepAliveTime;
//    private final TimeUnit timeUnit;
//
//    public BasicThreadPool(int initSize, int maxSize, int coreSize,int queueSize) {
//
//    }
//
//    public BasicThreadPool(int initSize, int maxSize, int coreSize, ThreadFactory threadFactory,int queueSize,DenyPolicy denyPolicy, long keepAliveTime, TimeUnit timeUnit) {
//        this.initSize = initSize;
//        this.maxSize = maxSize;
//        this.coreSize = coreSize;
//        this.threadFactory = threadFactory;
//        this.runnableQueue = new LinkedRunnableQueue(queueSize,denyPolicy,this);
//        this.keepAliveTime = keepAliveTime;
//        this.timeUnit = timeUnit;
//        this.init();
//    }
//
//    //初始化时先常见initSize个线程
//    private void init(){
//        start();
//        for (int i=0;i<initSize;i++){
//            new Thread();
//        }
//    }
//}
