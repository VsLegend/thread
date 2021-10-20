package com.training.thread.threadPool.cancelThread;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Wong Jwei
 * @Date 2021/10/20
 * @Description 取消或中断线程任务
 */

public class CancelThreadTest {


    public static void main(String[] args) throws InterruptedException {
        PrimeThread1 primeThread = new PrimeThread1();
        Thread thread = new Thread(primeThread);
        thread.start();
        Thread.sleep(100);
        thread.interrupt(); // 中断操作并不是真正的中断一个线程的运行，而是发起中断请求，由线程决定下一个合适的时候中断自己（取消点）
        System.out.println("interrupting...");
        Thread.sleep(100);
        primeThread.cancel();
    }


    static class PrimeThread1 implements Runnable { // 通过外部控制来中断线程

        private final List<BigInteger> primes = new ArrayList<>();

        private volatile boolean cancelled;

        public void cancel() {
            cancelled = true;
        }

        @Override
        public void run() {
            System.out.println("Thread starting...");
            BigInteger p = BigInteger.ONE;
            while (!cancelled) { // 用于被外部中断时的判断操作
                p = p.nextProbablePrime();
                synchronized (primes) {
                    primes.add(p);
                }
//                try {
//                    Thread.sleep(100); // wait sleep join 会严格处理interrupt请求，并抛出一个异常
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            System.out.println("Thread interrupted...");
            System.out.println(Arrays.toString(primes.toArray()));
        }

    }
    static class PrimeThread2 extends Thread { // 通过线程中断方法中断线程

        private final List<BigInteger> primes = new ArrayList<>();

        public void cancel() {
            interrupt();
        }

        @Override
        public void run() {
            System.out.println("Thread starting...");
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) { // 用于被外部中断时的判断操作
                p = p.nextProbablePrime();
                synchronized (primes) {
                    primes.add(p);
                }
            }
            System.out.println("Thread interrupted...");
            System.out.println(Arrays.toString(primes.toArray()));
        }

    }

}
