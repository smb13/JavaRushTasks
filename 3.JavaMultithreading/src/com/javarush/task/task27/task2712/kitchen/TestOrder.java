package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Mikhail Shamanin on 06.11.2017.
 */
public class TestOrder extends Order {
    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() throws IOException {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        dishes = new ArrayList<>();
        for (Dish dish : Dish.values()) {
            if ((int) (random.nextInt(2)) == 1) {
                dishes.add(dish);
            }
        }
    }
}
