package test;

import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisTest {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("localhost");
        System.out.println(jedis.ping());
        jedis.set("key1","fish1");
        jedis.set("key2","fish1");
        jedis.set("key3","fish1");
        jedis.set("key4","fish1");

        jedis.lpush("list","fish1");
        jedis.lpush("list","fish1");
        jedis.lpush("list","fish1");
        List<String> list = jedis.lrange("list",0,10);

    }
}
