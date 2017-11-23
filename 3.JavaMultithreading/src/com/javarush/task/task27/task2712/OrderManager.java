package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Mikhail Shamanin on 23.11.2017.
 */
public class OrderManager implements Observer {
    private LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<Order>();

    public OrderManager() {
        Thread demon = new Thread () {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(10);
                        if (!orderQueue.isEmpty()) {
                            for (Cook cook : StatisticManager.getInstance().getCooks()) {
                                if (!cook.isBusy()) {
                                    cook.startCookingOrder(orderQueue.poll());
                                }
                            }
                        }
                    }
                } catch (InterruptedException e){

                }
            }
        };
        demon.setDaemon(true);
        demon.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            orderQueue.put((Order)arg);
        } catch (InterruptedException e) {

        }
    }
}
