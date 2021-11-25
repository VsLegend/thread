package com.training.thread.threadPool;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Wang Junwei
 * @Date 2021/8/4
 * @Description
 */
public class CompletionServiceTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        AtomicInteger num = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // 初始化CompletionService类
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        for (int i = 0; i < 1000; i++) {
            completionService.submit(() -> {
                int random = new Random().nextInt(10) * 10;
                try {
                    Thread.sleep(random);
                } catch (InterruptedException e) {
                    // 中断检测
                    e.printStackTrace();
                    return "中断返回";
                }
                return Thread.currentThread().getName() + " " + random;
            });
        }
        // 关闭线程池
        executorService.shutdown();
        // 获取任务计算结果
        Future<String> future;
        while (!executorService.isTerminated()) {
            // take获取并移除下一个完成的任务结果，如果为空，将会持续堵塞
            future = completionService.take();
            // poll获取并移除下一个完成的任务结果，如果为空，则返回null
//            future = completionService.poll();
            if (future.isDone()) {
                System.out.println(num.getAndIncrement());
                String s = future.get();
                System.out.println(s);
            }
        }
        System.out.println(num.get());
    }

}
