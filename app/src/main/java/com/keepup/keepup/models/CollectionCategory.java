package com.keepup.keepup.models;

import java.util.ArrayList;

/**
 * Created by david.uzan on 10/29/2016.
 */

public class CollectionCategory {
    private ArrayList<String> mMembers;
    private String mTitle;
    private String mDescription;

    public static CollectionCategory fromSM(ServerModels.CollectionCategorySM collectionCategorySM) {
        CollectionCategory collectionCategory = new CollectionCategory();
        collectionCategory.mTitle = collectionCategorySM.title;
        collectionCategory.mDescription = collectionCategorySM.description;
        collectionCategory.mMembers = new ArrayList<>();
        if (collectionCategorySM.members != null) {
            collectionCategory.mMembers.addAll(collectionCategorySM.members);
        }

        return collectionCategory;
    }

    public ArrayList<String> getMembers() {
        return mMembers;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
