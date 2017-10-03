public class App
{
    public static void main(String[] args )
    {
        new RunnableEx().exec();
        new RunnableEx1().exec();
        new ExecutorEx().exec();
        new ExecutorEx1().exec();
        new CallableAndFutureEx().exec();
        new TimeoutEx().exec();
        new InvokeAllEx().exec();
        new InvokeAnyEx().exec();
        new ScheduledExecutorEx().exec();
        new ScheduledExecutorEx1().exec();
        new ScheduledExecutorEx2().exec();
    }
}