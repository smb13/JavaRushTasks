package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Mikhail Shamanin on 15.10.2017.
 */
public class Waiter implements Runnable{
    private LinkedBlockingQueue<Order> queueReady;
    private boolean isCancel = false;
    private boolean busy = false;
    private String name;

    public void setCancel() {
        isCancel = true;
    }

    public boolean isBusy() {
        return busy;
    }

    public Waiter(LinkedBlockingQueue<Order> queueReady, String name) {
        this.queueReady = queueReady;
        this.name = name;
    }

    public Waiter(String name) {
        this.name = name;
    }

    public void setQueueReady(LinkedBlockingQueue<Order> queueReady) {
        this.queueReady = queueReady;
    }

    @Override
    public void run() {
        while (!isCancel || !queueReady.isEmpty()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage(e.getMessage());
            }
            if (queueReady.peek()!=null){
                if (!this.isBusy()) {
                    try{
                        Order order = queueReady.take();
                        if (order!=null){
                            startDelivering(order);
                        }
                    }
                    catch (InterruptedException e){

                    }
                }
            }
        }
    }

    public void startDelivering(Order order) {
        busy = true;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        ConsoleHelper.writeMessage(order.toString() + " was delivered by " + this.toString());
        busy = false;
    }

    @Override
    public String toString() {
        return name;
    }
}
