package com.example.lottery.RedisProject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.concurrent.*;

public class Consumer implements Callable<String> {
    private static final String HOST = "192.168.190.68";
    // Redis服务器端口号
    private static final int PORT = 6379;

    private String prize ;



    public void run() {
        Jedis jedis = new Jedis(HOST, PORT);
        jedis.select(2);
        Lottory lottory = new Lottory();
        prize = jedis.srandmember("setPrizes");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String call() throws Exception {
        Jedis jedis = new Jedis(HOST, PORT);
        jedis.select(2);
        Lottory lottory = new Lottory();
        prize = jedis.srandmember("setPrizes");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " : " + prize);
        return prize;
    }
}
