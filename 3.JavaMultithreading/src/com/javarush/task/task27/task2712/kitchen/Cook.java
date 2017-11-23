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

    public Cook(String name) {
        this.name = name;
    }

    public void setOrderQueue(LinkedBlockingQueue<Order> orderQueue) {
        this.queue = orderQueue;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order) {
        busy = true;
        StatisticManager.getInstance().register(new CookedOrderEventDataRow((order.getTablet()).toString(), name, (order).getTotalCookingTime() * 60, (order.getDishes())));
        setChanged();
        notifyObservers(order);
        try {
            Thread.sleep(order.getTotalCookingTime()*10);
        } catch (InterruptedException e) {

        }
        busy = false;
    }

    public boolean isBusy() {
        return busy;
    }


/*@Override
    public void update(Observable o, Object arg) {
        StatisticManager.getInstance().register(new CookedOrderEventDataRow(((Tablet) o).toString(), name, ((Order) arg).getTotalCookingTime() * 60, ((Order) arg).getDishes()));
        //ConsoleHelper.writeMessage("Start cooking - " + arg);
        setChanged();
        notifyObservers(arg);
    }*/
}
