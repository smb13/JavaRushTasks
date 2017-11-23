package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Mikhail Shamanin on 04.11.2017.
 */
public class DirectorTablet {

    public void printAdvertisementProfit() {
        SortedMap<Date, Long> result = new TreeMap<>(Collections.reverseOrder());
        result.putAll(StatisticManager.getInstance().getVideoAmountStat());
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        long total = 0;
        for (Map.Entry<Date, Long> row : result.entrySet()) {
            StringBuilder str = new StringBuilder(format.format(row.getKey()) + " - ");
            str.append(String.format(Locale.ENGLISH, "%.2f", (double) row.getValue() / 100));
            ConsoleHelper.writeMessage(str.toString());
            total += row.getValue();
        }
        ConsoleHelper.writeMessage(String.format(Locale.ENGLISH,"Total - %.2f", (double) total / 100));
    }

    public void printCookWorkloading(){
        SortedMap<Date, Map<String, Integer>> result = new TreeMap<>(Collections.reverseOrder());
        result.putAll(StatisticManager.getInstance().getCookedOrderStat());
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (Map.Entry<Date, Map<String, Integer>> row : result.entrySet()) {
            ConsoleHelper.writeMessage(format.format(row.getKey()));
            SortedMap<String, Integer> innerMap = new TreeMap<>();
            innerMap.putAll(row.getValue());
            for (Map.Entry<String, Integer> innerRow : innerMap.entrySet()) {
                ConsoleHelper.writeMessage(innerRow.getKey() + " - " + Math.round((double) innerRow.getValue() / 60) + " min");
            }
        }
    }

    public void printActiveVideoSet(){
        List<Advertisement> adList = StatisticAdvertisementManager.getInstance().getActiveVideoSet();
        Collections.sort(adList, (o1, o2) -> {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        });
        for (Advertisement ad: adList) {
            ConsoleHelper.writeMessage(ad.getName() + " - " + ad.getHits());
        }
    }

    public void printArchivedVideoSet(){
        List<Advertisement> adList = StatisticAdvertisementManager.getInstance().getArchivedVideoSet();
        Collections.sort(adList, (o1, o2) -> {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        });
        for (Advertisement ad: adList) {
            ConsoleHelper.writeMessage(ad.getName());
        }
    }
}
