package com.keepup.keepup.models;

import java.util.ArrayList;

/**
 * Created by david.uzan on 10/29/2016.
 */

public class TrendingGroup {
    private ArrayList<TrendingItem> mMembers;
    private String mHeadlineOne;
    private String mHeadlineTwo;

    public static TrendingGroup fromSM(ServerModels.TrendingGroupSM trendingGroupSM) {
        TrendingGroup trendingGroup = new TrendingGroup();
        trendingGroup.mHeadlineOne = trendingGroupSM.headline_one;
        trendingGroup.mHeadlineTwo = trendingGroupSM.headline_two;
        trendingGroup.mMembers = new ArrayList<>();
        if (trendingGroupSM.members != null) {
            for (ServerModels.TrendingItemSM trendingItemSM : trendingGroupSM.members) {
                trendingGroup.mMembers.add(TrendingItem.fromSM(trendingItemSM));
            }
        }
        return trendingGroup;
    }

    public ArrayList<TrendingItem> getMembers() {
        return mMembers;
    }

    public String getHeadlineOne() {
        return mHeadlineOne;
    }

    public String getHeadlineTwo() {
        return mHeadlineTwo;
    }
}
