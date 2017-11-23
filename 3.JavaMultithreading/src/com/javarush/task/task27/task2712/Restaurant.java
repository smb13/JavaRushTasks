package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Mikhail Shamanin on 14.10.2017.
 */
public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<Order>();

    public static void main (String[] args) {
        //Tablet t5 = new Tablet(5);

        Cook amigo = new Cook("Amigo");
        Cook diego = new Cook("Diego");

        amigo.setOrderQueue(orderQueue);
        diego.setOrderQueue(orderQueue);

        StatisticManager.getInstance().register(amigo);
        StatisticManager.getInstance().register(diego);

        Waiter waiter = new Waiter();

        List<Tablet> tabletList = new ArrayList<>();
        for (int i=1; i<=5; i++) {
            Tablet t = new Tablet(i);
            tabletList.add(t);
            //t.addObserver(amigo);
            //t.addObserver(diego);
        }

        //t5.addObserver(amigo);
        amigo.addObserver(waiter);
        diego.addObserver(waiter);
        //Order order = t5.createOrder();

        Thread thread = new Thread(new RandomOrderGeneratorTask(tabletList, ORDER_CREATING_INTERVAL));
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        thread.interrupt();
        /*t5.createOrder();
        t5.createOrder();
        t5.createOrder();*/

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();


    }
}
