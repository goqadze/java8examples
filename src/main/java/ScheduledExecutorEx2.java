import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorEx2 implements Exec {
    @Override
    public void exec() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("Start of Scheduled Executor example2");

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            }
            catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };

        //This method works just like the counterpart scheduleAtFixedRate(). The difference is that the wait time period applies between the end of a task and the start of the next task
        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(6);
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
