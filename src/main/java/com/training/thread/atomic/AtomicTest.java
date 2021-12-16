package com.training.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Wong Jwei
 * @Date 2021/12/3
 * @Description
 */
public class AtomicTest {


    public static void main(String[] args) throws InterruptedException {
        final AtomicInteger count = new AtomicInteger();
        final int capacity = 100;
        // put
        Thread put = new Thread(() -> {
            while (count.get() <= capacity && !Thread.currentThread().isInterrupted()) {
                int increment = count.getAndIncrement();
                System.out.println("PUT:" + increment);
            }
        });
        // take
        Thread take = new Thread(() -> {
            while (count.get() > 0  && !Thread.currentThread().isInterrupted()) {
                int decrement = count.getAndDecrement();
                System.out.println("TAKE:" + decrement);
            }
        });



        put.start();
        take.start();
        Thread.sleep(5000);
        put.interrupt();
        take.interrupt();
    }

}
