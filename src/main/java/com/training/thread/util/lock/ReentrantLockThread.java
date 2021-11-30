package com.training.thread.util.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @User: Wang Junwei
 * @Date: 2020/7/31
 * @Description:
 */
public class ReentrantLockThread {

  final ReentrantLock lock = new ReentrantLock();

  volatile boolean flag = true;

  int sorce = 100;

  public void output(int i) {
    // 🔒 可代替synchronized
    try {
      if (lock.tryLock(1, TimeUnit.SECONDS)) {
        try {
          sorce += i;
          System.out.println(sorce);
        } finally {
          lock.unlock();
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ReentrantLockThread lockThread = new ReentrantLockThread();
    final ReentrantLock lock = lockThread.lock;
    final Condition condition = lock.newCondition();
    new Thread(() -> {
      lock.lock();
      System.out.println(Thread.currentThread().getName() + "线程获取锁");
      try {
        while (lockThread.flag) {
          // ...
          condition.await();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
        System.out.println(Thread.currentThread().getName() + "线程释放锁");
      }
    }).start();
    new Thread(() -> {
      lock.lock();
      System.out.println(Thread.currentThread().getName() + "线程获取锁");
      try {
        while (lockThread.flag) {
          // ...
          condition.await();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
        System.out.println(Thread.currentThread().getName() + "线程释放锁");
      }
    }).start();
    new Thread(() -> {
      lock.lock();
      try {
        Thread.sleep(2000);
        lockThread.flag = false;
        condition.signalAll();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        System.out.println("变更状态");
        lock.unlock();
      }
    }).start();
//    Thread a = new Thread(() -> {
//      for (int i = 0; i < 1000; i++) {
//        System.out.print(i + " ");
//        lockThread.output(i);
//      }
//    });
//    System.out.println();
//    Thread b = new Thread(() -> {
//      for (int y = 0; y > -1000; y--) {
//        System.out.print(y + " ");
//        lockThread.output(y);
//      }
//    });
//    a.start();
//    b.start();
//    try {
//      a.join();
//      b.join();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
  }

}
