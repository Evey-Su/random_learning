package test;

@FunctionalInterface
public interface Callback<T> {
    void call(T t);
}
