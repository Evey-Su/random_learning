package test;


import com.alibaba.fastjson.JSON;

import java.util.LinkedList;

//产品传送带需要有产品和工人配合
public class ProductionChannel {
    //定义传送带上的产品的最大容量
    private final static int MAX_PROD = 30;
    //存放加工的产品
    private final LinkedList<Production> productionQueue = new LinkedList<>();
    //在流水线上工作的工人
    private final Worker[] workers;

    public ProductionChannel(int workSize) {
        this.workers = new Worker[workSize];
        //实例化每一个工人worker
        for (int i = 0; i < workSize; i++) {
            workers[i] = new Worker("worker-" + i, this);
            workers[i].start();
        }
    }

    //接受来自上游的半成品(待加工的产品)
    public void offProduction(Production production) {
        synchronized (this) {
            while (productionQueue.size() >= MAX_PROD) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            productionQueue.addLast(production);
            System.out.println("offer size:"+productionQueue.size());
            this.notifyAll();
        }
    }

    //工人线程从传送带上获取产品，并且进行加工
    public Production takeProduction() {
        synchronized (this) {
            //当传送带上没有产品的时候wait
            while (productionQueue.size() <= 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //获取产品
            Production prod = productionQueue.removeFirst();
            System.out.println("take size:"+productionQueue.size());
            this.notifyAll();
            return prod;
        }
    }
}
