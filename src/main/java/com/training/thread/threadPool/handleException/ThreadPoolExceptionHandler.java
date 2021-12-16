package com.training.thread.threadPool.handleException;

import java.util.concurrent.*;

/**
 * @Author Wang Junwei
 * @Date 2021/7/1
 * @Description thread pool exception handler
 */
public class ThreadPoolExceptionHandler extends ThreadPoolExecutor {

    public ThreadPoolExceptionHandler(int size) {
        super(size, size, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }


    /**
     * if (t == null && r instanceof Future<?>) {
     *   try {
     *     Object result = ((Future<?>) r).get();
     *   } catch (CancellationException ce) {
     *       t = ce;
     *   } catch (ExecutionException ee) {
     *       t = ee.getCause();
     *   } catch (InterruptedException ie) {
     *       Thread.currentThread().interrupt(); // ignore/reset
     *   }
     * }
     * if (t != null)
     *   System.out.println(t);
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        // catch the exception
        // 如果任务是Callable类型，那么异常将会直接通过Future返回
        System.out.println("Thread:" + r.toString());
        System.out.println("Throwing exception:" + t);
    }


    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExceptionHandler threadPoolExecutor = new ThreadPoolExceptionHandler(processors);
        threadPoolExecutor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("throw a new Exception ! ");
        });
        threadPoolExecutor.shutdown();
        System.out.println("Thread terminated");
    }

}
