package com.training.thread.concurrentCollection;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @User: Wang Junwei
 * @Date: 2021/4/1
 * @Description:
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        new Thread(() -> {
            try {
                queue.put(1);
                System.out.println(Thread.currentThread().getName() + " 生产");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Integer peek = queue.peek();
                System.out.println(Thread.currentThread().getName() + " 查看：" + peek);
                Integer take = queue.take();
                System.out.println(Thread.currentThread().getName() + " 消费：" + take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Integer poll = queue.poll(2, TimeUnit.SECONDS);
                if (null != poll) {
                    System.out.println(Thread.currentThread().getName() + " 限定时间内消费：" + poll);
                } else {
                    System.out.println(Thread.currentThread().getName() + " 限定时间内未能消费");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
