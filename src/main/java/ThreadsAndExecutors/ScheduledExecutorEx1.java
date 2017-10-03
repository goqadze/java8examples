package ThreadsAndExecutors;

import Shared.Exec;

import java.util.concurrent.*;

public class ScheduledExecutorEx1 implements Exec {
    @Override
    public void exec() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("Start of Scheduled Executor example1");

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

        int initialDelay = 0;
        int period = 1;
        //scheduleAtFixedRate() doesn't take into account the actual duration of the task
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(6);
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
