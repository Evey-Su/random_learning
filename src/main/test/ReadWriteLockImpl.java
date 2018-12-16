package test;

public class ReadWriteLockImpl implements ReadWriteLock {
    //定义对象锁
    private final Object MUTEX = new Object();

    private int writingWriters = 0;
    private int watingWriters = 0;
    private int readingReaders = 0;

    //read和write的偏好设置
    private boolean preferWriter;

    //默认情况下preferWriter为true
    public ReadWriteLockImpl() {
        this(true);
    }

    public ReadWriteLockImpl(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    //创建read lock
    public Lock readLock() {
        return new ReadLock(this);
    }

    //创建write lock
    public Lock writeLock() {
        return new WriteLock(this);
    }

    //使写线程的数量增加
    void incrementWritingWriters() {
        this.writingWriters++;
    }

    void incrementWatingWriters() {
        this.watingWriters++;
    }

    void incrementReadingReaders() {
        this.readingReaders++;
    }

    //使减少
    void decrementWritingWriters() {
        this.writingWriters--;
    }

    void decrementWatingWriters() {
        this.watingWriters--;
    }

    void decrementReadingReaders() {
        this.readingReaders--;
    }

    @Override
    public int getWritingWriters() {
        return this.writingWriters;
    }

    @Override
    public int getWatingWriters() {
        return this.watingWriters;
    }

    @Override
    public int getReadingReaders() {
        return this.readingReaders;
    }

    //获取对象锁
    Object getMutex() {
        return this.MUTEX;
    }

    //获取当前是否偏向写锁
    boolean getPreferWriter() {
        return this.preferWriter;
    }

    //设置写锁偏好
    void changePrefer(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }
}
