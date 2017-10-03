package SynchronizationAndLocks;

import Shared.Exec;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class ReadWriteLockEx implements Exec {

    @Override
    public void exec() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("Start of ReadWriteLock example");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();

        /**********************************************************************************************************************************************
         *   The idea behind read-write locks is that it's usually safe to read mutable variables concurrently as long as nobody is writing to this variable.
         *   So the read-lock can be held simultaneously by multiple threads as long as no threads hold the write-lock.
         *   This can improve performance and throughput in case that reads are more frequent than writes.
         ****************************************************************************************************************/
        executor.submit(() -> {
            lock.writeLock().lock();
            try {
                sleep(1);
                map.put("foo", "bar");
            } finally {
                lock.writeLock().unlock();
            }
        });

        Runnable readTask = () -> {
            lock.readLock().lock();
            try {
                System.out.println(map.get("foo"));
                sleep(1);
            } finally {
                lock.readLock().unlock();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);

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
