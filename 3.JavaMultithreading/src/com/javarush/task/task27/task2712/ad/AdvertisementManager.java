package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Mikhail Shamanin on 16.10.2017.
 */
public class AdvertisementManager {
    private int timeSeconds; //время показа рекламы в секундах
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() throws NoVideoAvailableException{
        if(listToDisplay().isEmpty()) {
            throw new NoVideoAvailableException();
        }
        List<Advertisement> bestAdsList = getBestAdsList();

        Collections.sort(bestAdsList, new Comparator<Advertisement>() {
            public int compare(Advertisement o1, Advertisement o2) {
                if (o2.getAmountPerOneDisplaying() == o1.getAmountPerOneDisplaying()) {
                    return (int)((o1.getAmountPerOneDisplaying() * 1000 / o1.getDuration()) - (o2.getAmountPerOneDisplaying() * 1000 / o2.getDuration()));
                } else {
                    return (int)(o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying());
                }
            }
        });

        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(bestAdsList, sumAmount(bestAdsList), sumTime(bestAdsList)));

        for (Advertisement ad : bestAdsList) {
            ConsoleHelper.writeMessage(ad.getName() + " is displaying... " + ad.getAmountPerOneDisplaying() + ", " + ad.getAmountPerOneDisplaying() * 1000 / ad.getDuration());
            ad.revalidate();
        }
    }

    public List<Advertisement> listToDisplay() {
        List<Advertisement> l = new ArrayList<Advertisement>();
        for (Advertisement a : storage.list()) {
            if (a.getHits() > 0) {
                l.add(a);
            }
        }
        return l;
    }

    public List<Advertisement> getBestAdsList() throws NoVideoAvailableException {
        List<List<Advertisement>> allAdsLists = getAlltAdsList();

        Collections.sort(allAdsLists, new Comparator<List<Advertisement>>() {

            public int compare(List<Advertisement> o1, List<Advertisement> o2) {
                if (AdvertisementManager.sumAmount(o1) != AdvertisementManager.sumAmount(o2)) {
                    return AdvertisementManager.sumAmount(o1) - AdvertisementManager.sumAmount(o2);
                }

                if (AdvertisementManager.sumTime(o1) != AdvertisementManager.sumTime(o2)) {
                    return AdvertisementManager.sumTime(o1) - AdvertisementManager.sumTime(o2);
                }

                if (o1.size() != o2.size()) {
                    return o2.size() - o1.size();
                }

                return 0;
            }
        });
        if (!allAdsLists.isEmpty()) {
            return allAdsLists.get(allAdsLists.size()-1);
        } else {
            throw new NoVideoAvailableException();
        }
    }

    private static int sumAmount (List<Advertisement> list) {
        int sum = 0;
        for (Advertisement ad : list) {
            sum += ad.getAmountPerOneDisplaying();
        }
        return sum;
    }

    private static int sumTime (List<Advertisement> list) {
        int sum = 0;
        for (Advertisement ad : list) {
            sum += ad.getDuration();
        }
        return sum;
    }

    public List<List<Advertisement>> getAlltAdsList() {
        List<List<Advertisement>> result = new ArrayList<>();
        final int _n = listToDisplay().size();
        final List<Advertisement> _list = listToDisplay();
        final int _time = timeSeconds;
        for (int i = 1; i <= listToDisplay().size(); i++) {
            final int _k = i;
            List<List<Advertisement>> resK = new ArrayList() {
                private int[] i = new int[_k];
                private int k = _k;
                private int n = _n;
                private int index;
                private List<Advertisement> list = _list;
                private int time = _time;

                {
                    i[0] = -1;
                    _add();
                }

                private void _add() {
                    int j = index;
                    i[j]++;
                    for (j++; j < k; j++) {
                        i[j] = i[j - 1] + 1;
                    }
                }

                private boolean _next() {
                    int l, j;
                    for (j = k - 1; j >= 0; j--) {
                        l = k - 1 - j;
                        if (n - 1 != i[j] + l) {
                            break;
                        }
                    }
                    index = j;

                    if (index == -1) {
                        return false;
                    }
                    _add();
                    return true;
                }

                public List<List<Advertisement>>_makelist() {
                    int j;
                    List<Advertisement> currList = new ArrayList<>();
                    do {
                        for (j = 0; j < k; j++) {
                            currList.add(list.get(i[j]));
                        }
                        if (checkTime(currList)) {
                            add(currList);
                        }
                        currList = new ArrayList<>();
                    } while (this._next());
                    return this;
                }

                private boolean checkTime (List<Advertisement> list1) {
                    int listTime = 0;
                    for (Advertisement curAd : list1) {
                        listTime += curAd.getDuration();
                    }
                    return (listTime <= time);
                }

            }._makelist();
            result.addAll(resK);
        }
        return result;
    }
}
