package SynchronizationAndLocks;

import Shared.Exec;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class StampedLockEx implements Exec {

    @Override
    public void exec() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("Start of StampedLock example");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();

        /*********************************************************************
         * The optimistic lock is valid right after acquiring the lock.
         * In contrast to normal read locks an optimistic lock doesn't prevent other threads to obtain a write lock instantaneously.
         * After sending the first thread to sleep for one second the second thread obtains a write lock without waiting for the optimistic read lock to be released.
         * From this point the optimistic read lock is no longer valid.
         * Even when the write lock is released the optimistic read locks stays invalid.
         *
         * So when working with optimistic locks you have to validate the lock every time after accessing any shared mutable variable to make sure the read was still valid.
         *****************************************************************************/

        executor.submit(() -> {
            long stamp = lock.tryOptimisticRead();
            try {
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                sleep(1);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                sleep(2);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
            } finally {
                lock.unlock(stamp);
            }
        });

        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                System.out.println("Write Lock acquired");
                sleep(2);
            } finally {
                lock.unlock(stamp);
                System.out.println("Write done");
            }
        });

        stop(executor);
    }

    private void stop(ExecutorService executor) {
        executor.shutdown();
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
