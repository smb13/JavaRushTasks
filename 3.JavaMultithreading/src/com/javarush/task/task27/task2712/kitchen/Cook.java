package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Mikhail Shamanin on 15.10.2017.
 */
public class Cook extends Observable implements Runnable{
    private final String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue;
    private LinkedBlockingQueue<Order> queueReady;
    private boolean isCancel = false;

    public Cook(String name) {
        this.name = name;
    }

    public void setCancel() {
        isCancel = true;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void setQueueReady(LinkedBlockingQueue<Order> queueReady) {
        this.queueReady = queueReady;
    }

    @Override
    public String toString() {
        return name;
    }


    @Override
    public void run() {
        while (!isCancel || !queue.isEmpty()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage(e.getMessage());
            }
            if (queue.peek()!=null){
                if (!this.isBusy()) {
                    try{
                        Order order = queue.take();
                        if (order!=null){
                            startCookingOrder(order);
                        }
                    }
                    catch (InterruptedException e){

                    }
                }
            }
        }
    }

    public void startCookingOrder(Order order) {
        busy = true;
        StatisticManager.getInstance().register(new CookedOrderEventDataRow((order.getTablet()).toString(), name, (order).getTotalCookingTime() * 60, (order.getDishes())));
        try {
            Thread.sleep(order.getTotalCookingTime()*10);
            queueReady.put(order);
        } catch (InterruptedException e) {

        }
        ConsoleHelper.writeMessage(order.toString() + " was cooked by " + this.toString());

        busy = false;
    }

    public boolean isBusy() {
        return busy;
    }
}
