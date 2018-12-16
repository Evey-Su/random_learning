package test;

public interface CacheLoader<K, V> {
    V load(K k);
}
