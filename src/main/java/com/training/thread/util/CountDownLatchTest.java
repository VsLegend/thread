package com.training.thread.util;

import java.util.concurrent.CountDownLatch;

/**
 * @User: Wang Junwei
 * @Date: 2021/3/15
 * @Description: 这个类能使一个线程等待其他线程各自执行完毕后再执行，
 * 它是通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕后，计数器的值就-1，
 * 当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了。
 * @see java.util.concurrent.CountDownLatch
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        // 构造器设置初始计数器10，当10个任务都完成后，进行汇总计算
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        // 分支任务
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " 子任务运行完成，等待汇总...");
            // 计数器减一，不会阻塞当前线程，相当于释放一个锁之后继续运行锁外的方法
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " 子任务完成汇总，结束线程...");
        };
        // 汇总任务
        Runnable mainRunnable = () -> {
            System.out.println("汇总任务执行中...");
            try {
                System.out.println(Thread.currentThread().getName() + " 等待其他其他子线程执行完毕中...");
                // 等待计数器清零后，就会释放所有调用该方法的线程
                // 这里并不会获取锁，只是起一个阻塞作用，当计数器state清零时，该线程就会继续运行
//                boolean await = countDownLatch.await(2000, TimeUnit.SECONDS);
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName() + " 汇总计算完成...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        // 任务汇总1
        Thread thread = new Thread(mainRunnable);
        // 任务汇总2
        Thread thread1 = new Thread(mainRunnable);
        thread.start();
        thread1.start();
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
        thread.join();
    }

}
