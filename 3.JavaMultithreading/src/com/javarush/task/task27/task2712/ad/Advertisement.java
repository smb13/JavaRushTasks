package com.javarush.task.task27.task2712.ad;

/**
 * Created by Mikhail Shamanin on 15.10.2017.
 */
public class Advertisement {
    Object content;     //видео
    String name;        //имя/название
    long initialAmount; //начальная сумма, стоимость рекламы в копейках. Используем long, чтобы избежать проблем с округлением
    int hits;           //количество оплаченных показов
    int duration;       //продолжительность в секундах
    long amountPerOneDisplaying;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        this.amountPerOneDisplaying = initialAmount / hits;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }


}
