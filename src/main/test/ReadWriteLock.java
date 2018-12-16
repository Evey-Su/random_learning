package test;

public interface ReadWriteLock {
    //创建reader锁
    Lock readLock();

    //创建write锁
    Lock writeLock();

    //获取当前有多少线程正在执行
    int getWritingWriters();

    //获取当前线程有多少正在等待获取写入锁
    int getWatingWriters();

    //获取当前有多少线程正在等待reader锁
    int getReadingReaders();

    //工厂方法，创建ReadWriteLock
    static ReadWriteLock readWriteLock() {
        return new ReadWriteLockImpl();
    }

    //工厂方法，传入preferWriter
    static ReadWriteLock readWriteLock(boolean preferWriter) {
        return new ReadWriteLockImpl(preferWriter);
    }
}
