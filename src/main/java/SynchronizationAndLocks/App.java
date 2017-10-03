package SynchronizationAndLocks;

public class App
{
    public static void main(String[] args )
    {
        new SynchronizedEx().exec();
        new LockEx().exec();
        new ReadWriteLockEx().exec();
    }
}