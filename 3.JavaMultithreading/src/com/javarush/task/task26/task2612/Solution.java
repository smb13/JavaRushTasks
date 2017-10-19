package com.javarush.task.task26.task2612;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* 
Весь мир играет комедию
*/
public class Solution {
    private Lock lock = new ReentrantLock();

    public void someMethod() {
        //implement logic here, use the lock field
        boolean flag = false;
        try {
            flag = lock.tryLock();
            if (flag) {
                ifLockIsFree();
            } else {
                ifLockIsBusy();
            }

        } finally {
            if (flag) lock.unlock();
        }
    }

    public void ifLockIsFree() {
    }

    public void ifLockIsBusy() {
    }
}
