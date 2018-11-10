package com.gaoge.code;

import java.io.Serializable;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gaoge
 * @date 2018/11/1 17:23
 * description:
 *     深入理解重载原理和线程池。重载并非严格匹配，而是有一个模糊边界和匹配顺序
 *     如下重载，按匹配程度依次递减
 *        参数类型：
 *           char
 *           int
 *           long
 *           float
 *           double
 *           Character
 *           interface
 *           Object
 *           array
 */
public class Overload {
    public static void sayHello(Object arg) {
        System.out.println("hello Object");
    }

    public static void sayHello(int arg) {
        System.out.println("hello int");
    }

    public static void sayHello(long arg) {
        System.out.println("hello long");
    }

    public static void sayHello(float arg) {
        System.out.println("hello float");
    }
    public static void sayHello(double arg) {
        System.out.println("hello float");
    }
    public static void sayHello(Character arg) {
        System.out.println("hello Character");
    }

    public static void sayHello(char arg) {
        System.out.println("hello char");
    }

    public static void sayHello(char... args) {
        System.out.println("hello char……");
    }

    public static void sayHello(Serializable arg) {
        System.out.println("hello Serializable");
    }

    public static void main(String[] args) throws InterruptedException {
        sayHello('a');
        /**
         * Thread pool
         */
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger poolNumber = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread( r,
                        "test" + poolNumber.getAndIncrement()
                        );
                if (t.isDaemon()) {
                    t.setDaemon(false);
                }
                if (t.getPriority() != Thread.NORM_PRIORITY) {
                    t.setPriority(Thread.NORM_PRIORITY);
                }
                return t;
            }
        };
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1, 20,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        ExecutorService thread = Executors.newFixedThreadPool(1);
        singleThreadPool.execute(() -> {
            System.out.println(Thread.currentThread().getName());
        });

    }
}

