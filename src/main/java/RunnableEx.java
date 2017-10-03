public class RunnableEx implements Exec {

    public void exec (){
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("Start of Runnable example");

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        };

        task.run();

        Thread thread = new Thread(task);
        thread.start();

        System.out.println("Done!");
    }
}
