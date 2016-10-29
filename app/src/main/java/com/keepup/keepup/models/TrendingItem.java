package com.keepup.keepup.models;

/**
 * Created by david.uzan on 9/16/2016.
 */
public class TrendingItem {

    private String mImage;
    private String mPlace;
    private String mCollection;

    public static TrendingItem fromSM(ServerModels.TrendingItemSM itemSM) {
        TrendingItem trendingItem = new TrendingItem();
        trendingItem.mImage = itemSM.image;
        trendingItem.mCollection = itemSM.collection;
        trendingItem.mPlace = itemSM.place;

        return trendingItem;
    }

    public String getImage() {
        return mImage;
    }

    public String getPlace() {
        return mPlace;
    }

    public String getCollection() {
        return mCollection;
    }
}
