package com.training.thread.concurrentCollection;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @User: Wang Junwei
 * @Date: 2021/4/1
 * @Description:
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        final BlockingQueue<String> queue = new LinkedBlockingQueue<>(1);
        // 生产者，
        new Thread(() -> {
            try {
                int time = new Random().nextInt(200);
                System.out.println("生产者休眠" + time + "");
                Thread.sleep(time);
                System.out.println("生产者开始生产");
                queue.put("商品1");
                System.out.println("生产者生产1");
                queue.put("商品2");
                System.out.println("生产者生产2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 忠实消费者，一直等，直到有货
        new Thread(() -> {
            try {
                String peek = queue.peek();
                System.out.println("忠实消费者查看有无货：" + peek);
                String take = queue.take();
                System.out.println("忠实消费者消费：" + take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 普通消费者，指定时间内每货，就不需要了
        new Thread(() -> {
            try {
                int time = new Random().nextInt(100);
                String poll = queue.poll(time, TimeUnit.MILLISECONDS);
                if (null != poll) {
                    System.out.println("普通消费者 限定时间" + time + "内消费：" + poll);
                } else {
                    System.out.println("普通消费者 限定时间" + time + "内未能消费");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
