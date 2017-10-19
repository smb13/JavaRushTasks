package com.javarush.task.task29.task2909.car;

/**
 * Created by Mikhail Shamanin on 11.06.2017.
 */
public class Truck extends Car {
    private static final int MAX_TRUCK_SPEED = 80;
    public Truck(int numberOfPassengers) {
        super(0, numberOfPassengers);
    }

    @Override
    public int getMaxSpeed() {
        return MAX_TRUCK_SPEED;
    }
}
