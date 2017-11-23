package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Mikhail Shamanin on 03.11.2017.
 */
public class StatisticManager {

    private static StatisticManager instance;
    private StatisticStorage statisticStorage = new StatisticStorage();
    private Set<Cook> cooks = new HashSet();
    private StatisticManager () {
    }

    public static StatisticManager getInstance () {
        if (instance == null) {
            instance = new StatisticManager ();
        }
        return instance;
    }

    public void register(EventDataRow data) {
        if (data != null) {
        statisticStorage.put(data);
        }
    }


    public void register(Cook cook) {
        cooks.add(cook);
    }

    public Map <Date, Long> getVideoAmountStat(){
        Map <Date, Long> result = new HashMap<>();
        for (EventDataRow eventDataRow : statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS)) {
            VideoSelectedEventDataRow videoSEDR = (VideoSelectedEventDataRow) eventDataRow;
            Date rowDate = videoSEDR.getDate();
            GregorianCalendar rowCalendar = new GregorianCalendar(rowDate.getYear() + 1900, rowDate.getMonth(), rowDate.getDate());
            if (result.containsKey(rowCalendar.getTime())) {
                result.put(rowCalendar.getTime(), result.get(rowCalendar.getTime()) + videoSEDR.getAmount());
            } else {
                result.put(rowCalendar.getTime(), videoSEDR.getAmount());
            }
        }
        return result;
    }

    public Map <Date, Map<String, Integer>> getCookedOrderStat(){
        Map <Date, Map<String, Integer>> result = new HashMap<>();
        for (EventDataRow eventDataRow : statisticStorage.getStorage().get(EventType.COOKED_ORDER)) {
            CookedOrderEventDataRow cookedOrderEDR = (CookedOrderEventDataRow) eventDataRow;
            Date rowDate = cookedOrderEDR.getDate();
            GregorianCalendar rowCalendar = new GregorianCalendar(rowDate.getYear() + 1900, rowDate.getMonth(), rowDate.getDate());
            if (result.containsKey(rowCalendar.getTime())) {
                if (result.get(rowCalendar.getTime()).containsKey(cookedOrderEDR.getCookName())) {
                    result.get(rowCalendar.getTime()).put(cookedOrderEDR.getCookName(), result.get(rowCalendar.getTime()).get(cookedOrderEDR.getCookName()) + cookedOrderEDR.getTime());
                } else {
                    result.get(rowCalendar.getTime()).put(cookedOrderEDR.getCookName(), cookedOrderEDR.getTime());
                }
            } else {
                Map<String, Integer> row = new HashMap<>();
                result.put(rowCalendar.getTime(), row);
                row.put(cookedOrderEDR.getCookName(), cookedOrderEDR.getTime());
            }
        }
        return result;
    }

    public Set<Cook> getCooks() {
        return cooks;
    }

    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        public StatisticStorage() {
            for (EventType aT : EventType.values()) {
                storage.put(aT, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data){
            storage.get(data.getType()).add(data);
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }
    }
}
