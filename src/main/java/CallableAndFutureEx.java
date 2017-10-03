import java.util.concurrent.*;

public class CallableAndFutureEx implements Exec {
    @Override
    public void exec() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("Start of Callable and Future example");

        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return 123;
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(1); // This is equivalent to newSingleThreadExecutor()
        Future<Integer> future = executor.submit(task);

        System.out.println("future done? " + future.isDone());

        Integer result;
        try {
            result = future.get();
            System.out.println("future done? " + future.isDone());
            System.out.print("result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
