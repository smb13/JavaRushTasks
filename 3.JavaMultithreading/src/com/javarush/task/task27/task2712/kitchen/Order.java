package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mikhail Shamanin on 14.10.2017.
 */
public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes = new ArrayList<>();

    public Order(Tablet tablet) throws IOException {
        dishes = ConsoleHelper.getAllDishesForOrder();
        this.tablet = tablet;
    }

    public int getTotalCookingTime() {
        int totalDuration = 0;
        for (Dish dish : dishes) totalDuration += dish.getDuration();
        return totalDuration;
    }


    public boolean isEmpty() {
        return dishes.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Your order: [");
        for (Dish d : dishes) {
            sb.append(d);
            if (d.ordinal() < (dishes.size() - 1)) sb.append(", ");
        }
        return sb.append("] of ").append(tablet.toString()).append(", cooking time ").append(Integer.toString(getTotalCookingTime())).append("min").toString();
    }
}
