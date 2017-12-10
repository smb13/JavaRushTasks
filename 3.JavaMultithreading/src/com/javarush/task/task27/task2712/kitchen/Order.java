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
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException{
        initDishes();
        this.tablet = tablet;
    }

    protected void initDishes() throws IOException {
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public int getTotalCookingTime() {
        int totalDuration = 0;
        for (Dish dish : dishes) totalDuration += dish.getDuration();
        return totalDuration;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public boolean isEmpty() {
        return dishes.size() == 0;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Your order: [");
            for (int i=0; i<dishes.size(); i++) {
                sb.append(dishes.get(i));
                if (i != dishes.size() - 1) sb.append(", ");
            }
            return sb.append("] of ").append(tablet.toString()).append(", cooking time ").append(Integer.toString(getTotalCookingTime())).append("min").toString();
    }

    public Tablet getTablet() {
        return tablet;
    }
}
