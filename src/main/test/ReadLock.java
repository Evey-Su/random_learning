package test;

import java.util.List;
import java.util.concurrent.TimeoutException;

class ReadLock implements Lock {
    private final ReadWriteLockImpl readWriteLock;

    ReadLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {
        //使用Mutex作为锁
        synchronized (readWriteLock.getMutex()){
            //若此时有线程正在进行写操作，或有写线程在等待并且偏向锁的标识为true时，就会无法获得读锁，只能被挂起
            while(readWriteLock.getWritingWriters()>0||(readWriteLock.getPreferWriter()&&readWriteLock.getWritingWriters()>0)){
                readWriteLock.getMutex().wait();
            }
            //成功获得锁，并且使readingReaders的数量增加
            readWriteLock.incrementReadingReaders();
        }
    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeoutException {

    }

    @Override
    public void unlock() {
        //使用Mutex作为锁，并进行同步
        synchronized (readWriteLock.getMutex()){
            //释放锁的过程就是使得当前reading的数量减一
            //将preferWriter设置为true，可以使得writer获得更多的机会
            //通知唤醒与Mutex关联monitor waitset中的线程
            readWriteLock.decrementReadingReaders();
            readWriteLock.changePrefer(true);
            readWriteLock.getMutex().notifyAll();
        }

    }

    @Override
    public List<Thread> getBlockedThreads() {
        return null;
    }
}
