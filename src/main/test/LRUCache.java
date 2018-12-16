package test;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LRUCache<K, V> {
    //用于记录key值得顺序
    private final LinkedList<K> keyList = new LinkedList<>();
    //用于存放数据
    private final Map<K, SoftReference<V>> cache = new HashMap<>();
    //cache的最大容量
    private final int capacity;
    //cacheLoader接口提供一种加载数据的方式
    private final CacheLoader<K, V> cacheLoader;

    public LRUCache(int capacity, CacheLoader<K, V> cacheLoader) {
        this.capacity = capacity;
        this.cacheLoader = cacheLoader;
    }

    public void put(K key, V value) {
        //当元素数量超过容量，将最老的数据清除
        if (keyList.size() >= capacity) {
            K eldestKey = keyList.removeFirst();
            cache.remove(eldestKey);
        }
        //如果数据已经存在，则从key的队列中删除
        if (keyList.contains(key)) {
            keyList.remove(key);
        }
        keyList.addLast(key);
        cache.put(key, new SoftReference<>(value));
    }

    public V get(K key) {
        V value;
        //先将key从list中删除
        boolean success = keyList.remove(key);
        //如果删除失败表明该key不在list，试图从cache中进行加载
        if (!success) {
            value = cacheLoader.load(key);
            this.put(key, value);
        } else {
            //如果删除成功，则从cache中返回数据，并且将key再次放到队尾
            value = cache.get(key).get();
            keyList.addLast(key);
        }
        return value;
    }

    @Override
    public String toString() {
        return this.keyList.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        LRUCache<Integer, Referance> cache = new LRUCache<>(10000, key -> new Referance());
//        cache.get("Alex");
//        cache.get("Evey");
//        cache.get("Lex");
//        cache.get("Jane");
//        cache.get("Maggie");
//
//        cache.get("Jenny");
//        System.out.println(cache.toString());
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            cache.get(i);
            TimeUnit.MILLISECONDS.sleep(1);
            System.out.println("the " + i + " reference is stored at cache.");
        }
    }
}
