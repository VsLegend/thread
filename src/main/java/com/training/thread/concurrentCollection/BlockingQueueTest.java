package com.training.thread.concurrentCollection;

import java.util.Random;
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

        // 生产者，
        new Thread(() -> {
            try {
                int time = new Random().nextInt(1000);
                Thread.sleep(time);
                System.out.println(Thread.currentThread().getName() + " 开始生产");
                queue.put(1);
                System.out.println(Thread.currentThread().getName() + " 生产");
                queue.put(2);
                System.out.println(Thread.currentThread().getName() + " 生产");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 忠实客户，一直等，直到有货
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

        // 普通客户，指定时间内每货，就不需要了
        new Thread(() -> {
            try {
                int time = new Random().nextInt(1000);
                Integer poll = queue.poll(time, TimeUnit.MILLISECONDS);
                if (null != poll) {
                    System.out.println(Thread.currentThread().getName() + " 限定时间" + time + "内消费：" + poll);
                } else {
                    System.out.println(Thread.currentThread().getName() + " 限定时间" + time + "内未能消费");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
