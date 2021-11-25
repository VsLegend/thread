package com.training.thread.util.lock;

import java.util.concurrent.Semaphore;

/**
 * @Author Wang Junwei
 * @Date 2021/8/23
 * @Description
 */
public class SemaphoreTest {

    private final int n;
    private final Semaphore foo = new Semaphore(1);
    private final Semaphore bar = new Semaphore(0);

    public SemaphoreTest(int n) {
        this.n = n;
    }

    public void foo() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            foo.acquire();
            System.out.print("foo");
            bar.release();
        }
    }

    public void bar() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            bar.acquire();
            System.out.print("bar");
            foo.release();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final SemaphoreTest s = new SemaphoreTest(10);
        new Thread(() -> {
            try {
                s.foo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                s.bar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
