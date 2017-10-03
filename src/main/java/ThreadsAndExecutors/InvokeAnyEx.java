package ThreadsAndExecutors;

import Shared.Exec;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class InvokeAnyEx implements Exec {
    @Override
    public void exec() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("Start of InvokeAny example");
        ExecutorService executor = Executors.newWorkStealingPool();


        List<Callable<String>> callables = Arrays.asList(
                callable("task1", 2),
                callable("task2", 1),
                callable("task3", 3));

        try {
            String result;
            result = executor.invokeAny(callables);
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    private Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }
}
