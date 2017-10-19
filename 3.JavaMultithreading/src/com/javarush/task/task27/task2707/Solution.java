package com.javarush.task.task27.task2707;

/* 
Определяем порядок захвата монитора
*/
public class Solution {
    private volatile boolean flag = false;
    public void someMethodWithSynchronizedBlocks(Object obj1, Object obj2) {
        synchronized (obj1) {
            synchronized (obj2) {
                //System.out.println(obj1 + " " + obj2);
                System.out.println(Thread.currentThread().getName()+ " " + obj1 + " " + obj2);
            }
        }
    }

    public static boolean isNormalLockOrder(final Solution solution, final Object o1, final Object o2) throws Exception {
        Thread T1;
        Thread T2;
        boolean flag = false;
        T1 = new Thread("T1") {
            @Override
            public void run() {
                solution.someMethodWithSynchronizedBlocks(o1, o2);
            }
        };

        T2 = new Thread("T2") {
            @Override
            public void run() {
                synchronized (o2) {
                    try {
                        currentThread().sleep(20);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        synchronized (o1) {
            T1.start();
            T2.start();
            Thread.sleep(5);
            return T2.getState() != Thread.State.BLOCKED;
        }
    }

    public static void main(String[] args) throws Exception {
        final Solution solution = new Solution();
        final Object o1 = new Object();
        final Object o2 = new Object();

        System.out.println(isNormalLockOrder(solution, o1, o2));
    }
}
