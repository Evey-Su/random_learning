package test;

import java.util.LinkedList;

public class ActiveMessageQueue {
    //用于存放提交的methodMessage消息
    private final LinkedList<ActiveMessage> messages = new LinkedList<>();

    public ActiveMessageQueue() {
        //启动worker线程
        new ActiveDaemonThread(this).start();
    }

    public void offer(ActiveMessage methodMessage) {
        synchronized (this) {
            messages.addLast(methodMessage);
            //take只会被一个线程使用所以不需要使用notifyAll()
            this.notify();
        }
    }

    public ActiveMessage take() {
        synchronized (this) {
            while (messages.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return messages.removeFirst();
        }
    }
}
