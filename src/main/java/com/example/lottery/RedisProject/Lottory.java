package com.example.lottery.RedisProject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Lottory {
    // Redis服务器地址
    private static final String HOST = "192.168.190.68";
    // Redis服务器端口号
    private static final int PORT = 6379;

    private static final String PRIZE_TOTAL_KEY = "total_prize";
    private static final String PRIZE_LEFT_KEY = "left_prize";

    private static final int PRIZE_COUNT = 200; // 奖品总数
    private static final int FIRST_PRIZE_COUNT = 5; // 一等奖数量
    private static final int SECOND_PRIZE_COUNT = 20; // 二等奖数量
    private static final int THIRD_PRIZE_COUNT = 50; // 三等奖数量

    private static final int USER_COUNT = 200; // 抽奖人数
    private static final double FIRST_PRIZE_PROBABILITY = 0.02; // 一等奖概率
    private static final double SECOND_PRIZE_PROBABILITY = 0.1; // 二等奖概率
    private static final double THIRD_PRIZE_PROBABILITY = 0.25; // 三等奖概率

    private static final String KEY_PREFIX = "prize_"; // Redis键前缀

    private static Map<String,Double> prizesMap;
    private static Map<Integer,Integer> prizesMap1;
    private static Jedis jedis;

    public static void main(String[] args) {
        jedis = new Jedis(HOST, PORT);
        jedis.select(2);

        Lottory lottory = new Lottory();
        lottory.initZsetPrize();

        //使用zset，zset可以直接导入hashmap
        //使用奖品类型（1234）作为分数，奖品类型_第几个 作为
        Map<String, Double> x = new HashMap<>();
        jedis.zadd("zset_prize2",prizesMap);
        for (int i = 1; i <= 4; i++) {
            int count = prizesMap1.get(i);
            for (int j = 1; j <= count; j++) {
                x.put(String.format("%d_%d", i, j), (double) i);
            }
        }
        
        Map<Integer, Integer> result = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            Set<String> member = jedis.zrandmember(KEY_PREFIX,10);
            member.getClass();
        }


        String[] setPrizes = lottory.initSetPrize();
        jedis.sadd("setPrizes", setPrizes);
    }

    private void initZsetPrize(){
        prizesMap = new HashMap<>();
        prizesMap1 = new HashMap<>();
        for (int i = 0; i < FIRST_PRIZE_COUNT; i++) {
            prizesMap.put("FIRST_PRIZE_"+i , 1d);
        }
        for (int i = 0; i < SECOND_PRIZE_COUNT; i++) {
            prizesMap.put("SECOND_PRIZE_"+i , 2d);
        }
        for (int i = 0; i < THIRD_PRIZE_COUNT; i++) {
            prizesMap.put("THIRD_PRIZE_"+i , 3d);
        }
        for (int i = 0; i < PRIZE_COUNT; i++) {
            prizesMap.put("THANKS_PRIZE_"+i , 4d);
        }
        prizesMap1.put(1,5);
        prizesMap1.put(2,20);
        prizesMap1.put(3,50);
        prizesMap1.put(4,200);
    }

    private String[] initSetPrize(){
        Set<String> prizes = new HashSet<>();
        for (int i = 0; i < FIRST_PRIZE_COUNT; i++) {
            prizes.add("FRI_PRIZE_" + i);
        }
        for (int i = 0; i < SECOND_PRIZE_COUNT; i++) {
            prizes.add("SEC_PRIZE" + i );
        }
        for (int i = 0; i < THIRD_PRIZE_COUNT; i++) {
            prizes.add("THR_PRIZE" + i );
        }
        for (int i = 0; i < PRIZE_COUNT; i++) {
            prizes.add("THANKS_PRIZE" + i);
        }

        return prizes.toArray(new String[prizes.size()]);
    }

    public void initPrizePool(){
        jedis = new Jedis(HOST, PORT);
        jedis.select(2);

        Lottory lottory = new Lottory();
        lottory.initZsetPrize();

        //使用set存放奖品
        String[] setPrizes = lottory.initSetPrize();
        jedis.sadd("setPrizes", setPrizes);
    }


}
