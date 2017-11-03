package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;

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

    public void processVideos() {
        if(storage.list().isEmpty())
            throw new NoVideoAvailableException();
        List<Advertisement> bestAdsList = getBestAdsList();

        List<Advertisement> videoList = bestAdsList;

        Collections.sort(videoList, new Comparator<Advertisement>() {
            public int compare(Advertisement o1, Advertisement o2) {
                if (o2.getAmountPerOneDisplaying() == o1.getAmountPerOneDisplaying()) {
                    return (int)((o1.getAmountPerOneDisplaying() * 1000 / o1.getDuration()) - (o2.getAmountPerOneDisplaying() * 1000 / o2.getDuration()));
                } else {
                    return (int)(o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying());
                }
            }
        });

        for (Advertisement ad : videoList) {
            ConsoleHelper.writeMessage(ad.getName() + " is displaying... " + ad.getAmountPerOneDisplaying() + ", " + ad.getAmountPerOneDisplaying() * 1000 / ad.getDuration());
            ad.revalidate();
        }
    }

    public List<Advertisement> getBestAdsList() {
        List<List<Advertisement>> allAdsLists = getAlltAdsList();

        Collections.sort(allAdsLists, new Comparator<List<Advertisement>>() {

            public int compare(List<Advertisement> o1, List<Advertisement> o2) {
                if (sumAmount(o1) != sumAmount(o2)) {
                    return sumAmount(o1) - sumAmount(o2);
                }

                if (sumTime(o1) != sumTime(o2)) {
                    return sumTime(o1) - sumTime(o2);
                }

                if (o1.size() != o2.size()) {
                    return o2.size() - o1.size();
                }

                return 0;
            }

            private int sumAmount (List<Advertisement> list) {
                int sum = 0;
                for (Advertisement ad : list) {
                    sum += ad.getAmountPerOneDisplaying();
                }
                return sum;
            }

            private int sumTime (List<Advertisement> list) {
                int sum = 0;
                for (Advertisement ad : list) {
                    sum += ad.getDuration();
                }
                return sum;
            }

        });
        return allAdsLists.get(allAdsLists.size()-1);
    }

    public List<List<Advertisement>> getAlltAdsList() {
        List<List<Advertisement>> result = new ArrayList<>();
        final int _n = storage.list().size();
        final List<Advertisement> _list = storage.list();
        final int _time = timeSeconds;
        for (int i = 1; i <= storage.list().size(); i++) {
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
