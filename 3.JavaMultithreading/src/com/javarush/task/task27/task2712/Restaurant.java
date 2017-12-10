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
    private static final LinkedBlockingQueue<Order> readyOrderQueue = new LinkedBlockingQueue<Order>();

    public static void main (String[] args) {
        Cook amigo = new Cook("Amigo");
        amigo.setQueue(orderQueue);
        Cook diego = new Cook("Diego");
        diego.setQueue(orderQueue);
        amigo.setQueueReady(readyOrderQueue);
        diego.setQueueReady(readyOrderQueue);
        Waiter waiter1 = new Waiter(readyOrderQueue, "Papy");
        Waiter waiter2 = new Waiter(readyOrderQueue, "Billy");



        Thread amigoThread = new Thread(amigo);
        amigoThread.start();
        Thread diegoThread = new Thread(diego);
        diegoThread.start();
        Thread waiter1Thread = new Thread(waiter1);
        Thread waiter2Thread = new Thread(waiter2);
        waiter1Thread.start();
        waiter2Thread.start();


        List<Tablet> tabletList = new ArrayList<>();
        for (int i=1; i<=5; i++) {
            Tablet t = new Tablet(i);
            t.setQueue(orderQueue);
            tabletList.add(t);
        }


        Thread thread = new Thread(new RandomOrderGeneratorTask(tabletList, ORDER_CREATING_INTERVAL));
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        thread.interrupt();
        amigo.setCancel();
        diego.setCancel();
        try {
            amigoThread.join();
            diegoThread.join();
        } catch (InterruptedException e) {

        }

        waiter1.setCancel();
        waiter2.setCancel();
        try {
            waiter1Thread.join();
            waiter2Thread.join();
        } catch (InterruptedException e) {

        }

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();

    }
}
