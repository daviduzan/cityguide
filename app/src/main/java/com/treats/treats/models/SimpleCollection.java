package com.treats.treats.models;

import java.util.ArrayList;

/**
 * Created by david.uzan on 9/11/2016.
 */
public class SimpleCollection {

    private ArrayList<String> members;
    private String description;

    public SimpleCollection() {
        members = new ArrayList<>();
    }

    public static SimpleCollection fromSM(ServerModels.SimpleCollectionSM collectionSM) {
        SimpleCollection simpleCollection = new SimpleCollection();
        if (collectionSM.members != null) {
            for (String key : collectionSM.members.keySet()) {
                simpleCollection.members.add(key);
            }
        }
        return simpleCollection;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public String getDescription() {
        return description;
    }
}