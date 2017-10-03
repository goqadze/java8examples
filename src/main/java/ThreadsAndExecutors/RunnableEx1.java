package ThreadsAndExecutors;

import Shared.Exec;

import java.util.concurrent.TimeUnit;

public class RunnableEx1 implements Exec {

    public void exec (){
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("Start of Runnable example1");

        Runnable runnable = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("Foo " + name);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Bar " + name);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
