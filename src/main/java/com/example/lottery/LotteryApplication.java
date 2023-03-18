package com.example.lottery;

import com.example.lottery.RedisProject.Consumer;
import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class LotteryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotteryApplication.class, args);

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                3,
                8,
                1000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10),
                new DefaultThreadFactory("new Thread-"),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );

        for (int i = 0; i < 100; i++) {
            Future<String> res = threadPool.submit(new Consumer());
        }

        threadPool.shutdown();
    }

}
