package SynchronizationAndLocks;

import Shared.Exec;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class SynchronizedEx implements Exec {

    private int count = 0;

    private void increment() {
        count = count + 1;
    }

    synchronized private void incrementSync() {
        count = count + 1;
    }

    private void incrementSync1() {
        synchronized (this){
            count = count + 1;
        }
    }

    @Override
    public void exec() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("Start of Synchronized example");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10000)
                .forEach(i -> executor.submit(this::incrementSync1));

        stop(executor);

        System.out.println(count);  // 10000
        
    }

    private void stop(ExecutorService executor) {
        executor.shutdown();
    }
}
