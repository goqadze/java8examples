package SynchronizationAndLocks;

import Shared.Exec;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class LockEx implements Exec {

    private ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    private void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void exec() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("Start of Lock example");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10000)
                .forEach(i -> executor.submit(this::increment));

        executor.submit(() -> {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        executor.submit(() -> {
            System.out.println("Locked: " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);
        });

        stop(executor);

        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
            System.out.println("Count " + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void stop(ExecutorService executor) {
        executor.shutdown();
    }
}
