package com.treats.treats.models;

/**
 * Created by david.uzan on 9/16/2016.
 */
public class TrendingItem {

    public String listPic;
    public String place;
    public String collection;

    public static TrendingItem fromSM(ServerModels.TrendingItemSM itemSM) {
        TrendingItem trendingItem = new TrendingItem();
        trendingItem.listPic = itemSM.image;
        trendingItem.collection = itemSM.collection;
        trendingItem.place = itemSM.place;

        return trendingItem;
    }
}
