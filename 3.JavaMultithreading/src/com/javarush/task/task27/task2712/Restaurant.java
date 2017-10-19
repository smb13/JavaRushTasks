package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

/**
 * Created by Mikhail Shamanin on 14.10.2017.
 */
public class Restaurant {

    public static void main (String[] args) {
        Tablet t5 = new Tablet(5);
        Cook amigo = new Cook("Amigo");
        Waiter waiter = new Waiter();
        t5.addObserver(amigo);
        amigo.addObserver(waiter);
        Order order = t5.createOrder();
    }
}
