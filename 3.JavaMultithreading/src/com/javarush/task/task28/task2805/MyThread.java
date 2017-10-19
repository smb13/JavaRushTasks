package com.javarush.task.task28.task2805;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mikhail Shamanin on 05.08.2017.
 */
public class MyThread extends Thread{
    private static final AtomicInteger currPriority = new AtomicInteger(0);
    {
        int p = currPriority.incrementAndGet();
        if (this.getThreadGroup() != null) {
            this.setPriority(p <= this.getThreadGroup().getMaxPriority() ? p : this.getThreadGroup().getMaxPriority());
        }
        if (p == 10) {
            currPriority.set(0);
        }
    }

    public MyThread() {
    }

    public MyThread(Runnable target) {
        super(target);
    }

    public MyThread(ThreadGroup group, Runnable target) {
        super(group, target);
    }

    public MyThread(String name) {
        super(name);
    }

    public MyThread(ThreadGroup group, String name) {
        super(group, name);
    }

    public MyThread(Runnable target, String name) {
        super(target, name);
    }

    public MyThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
    }

    public MyThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
    }
}
