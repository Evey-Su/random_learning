package test;

public interface ATask<T> {
    //任务执行接口，允许有返回值
    T call();
}
