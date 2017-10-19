package com.javarush.task.task27.task2712.kitchen;

/**
 * Created by Mikhail Shamanin on 14.10.2017.
 */
public enum Dish {Fish(25), Steak(30), Soup(15), Juice(5), Water(3);
    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString() {
        StringBuilder sb = new StringBuilder();
        for (Dish d : Dish.values()) {
            sb.append(d);
            if (d.ordinal() < (Dish.values().length - 1)) sb.append(", ");
        }
        return sb.toString();
    }
}
