package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mikhail Shamanin on 14.10.2017.
 */
public class ConsoleHelper {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return bufferedReader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishesToOrder = new ArrayList<>();
        String dishName;
        while (true) {
            System.out.println(Dish.allDishesToString());
            System.out.println("Выберите блюда для заказа или наберите exit, если закончили:");
            dishName = readString();
            if (Dish.allDishesToString().matches(".*" + dishName + ".*")) {
                dishesToOrder.add(Dish.valueOf(dishName));
            } else if (dishName.equals("exit")) {
                break;
            } else {
                System.out.println("Такого блюда нет и продолжите формирование заказа или введите exit для выхода");
            }
        }
        return dishesToOrder;
    }
}
