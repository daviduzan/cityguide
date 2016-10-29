package com.keepup.keepup.models;

/**
 * Created by david.uzan on 10/29/2016.
 */

public class Category {
    private String mName;
    private String mCollection;
    private String mImage;

    public static Category fromSM(ServerModels.CategorySM categorySM) {
        Category category = new Category();
        category.mName = categorySM.name;
        category.mCollection = categorySM.collection;
        category.mImage = categorySM.image;

        return category;
    }

    public String getName() {
        return mName;
    }

    public String getCollection() {
        return mCollection;
    }

    public String getImage() {
        return mImage;
    }
}
