package SynchronizationAndLocks;

import Shared.Exec;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockEx1 implements Exec {

    private int count = 0;

    @Override
    public void exec() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("Start of StampedLock1 example");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();

        /**
         * The task first obtains a read lock and prints the current value of field count to the console.
         * But if the current value is zero we want to assign a new value of 23.
         * We first have to convert the read lock into a write lock to not break potential concurrent access by other threads.
         * Calling tryConvertToWriteLock() doesn't block but may return a zero stamp indicating that no write lock is currently available.
         * In that case we call writeLock() to block the current thread until a write lock is available.
         * */

        executor.submit(() -> {
            long stamp = lock.readLock();
            try {
                if (count == 0) {
                    stamp = lock.tryConvertToWriteLock(stamp);
                    if (stamp == 0L) {
                        System.out.println("Could not convert to write lock");
                        stamp = lock.writeLock();
                    }
                    count = 23;
                }
                System.out.println(count);
            } finally {
                lock.unlock(stamp);
            }
        });

        stop(executor);
    }

    private void stop(ExecutorService executor) {
        executor.shutdown();
    }
}
