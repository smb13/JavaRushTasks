package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail Shamanin on 06.11.2017.
 */
public class StatisticAdvertisementManager {
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private static StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();

    public static StatisticAdvertisementManager getInstance() {
        return ourInstance;
    }

    private StatisticAdvertisementManager() {
    }

    public List<Advertisement> getActiveVideoSet(){
        List<Advertisement> adList = new ArrayList<>();
        for (Advertisement ad: advertisementStorage.list()) {
            if (ad.getHits() > 0) adList.add(ad);
        }
        return adList;
    }
    public List<Advertisement> getArchivedVideoSet() {
        List<Advertisement> adList = new ArrayList<>();
        for (Advertisement ad: advertisementStorage.list()) {
            if (ad.getHits() == 0) adList.add(ad);
        }
        return adList;
    }
}
