package SynchronizationAndLocks;

public class App
{
    public static void main(String[] args )
    {
        new SynchronizedEx().exec();
        new LockEx().exec();
        new ReadWriteLockEx().exec();
        new StampedLockEx().exec();
        new StampedLockEx1().exec();
    }
}