package com.treats.treats.models;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by david.uzan on 9/11/2016.
 */
public class Collection {
    public static final int SHOW_FLAG_CATEGORY = 4;
    public static final int SHOW_FLAG_REGULAR = 0;

    private ArrayList<String> members;
    private ArrayList<Integer> types;
    private String description;
    private int showFlag;

    public Collection() {
    }

    public static Collection fromSM(ServerModels.CollectionSM collectionSM) {
        Collection collection = new Collection();
        if (collectionSM.members != null) {
            collectionSM.members.removeAll(Collections.singleton(null));
            collection.members = collectionSM.members;
        } else {
            collection.members = new ArrayList<>();
        }
        if (collectionSM.types != null) {
//            collectionSM.types.removeAll(Collections.singleton(null));
            collection.types = collectionSM.types;
        } else {
            collection.types = new ArrayList<>();
        }
        collection.description = collectionSM.description;
        collection.showFlag = collectionSM.showFlag;

        return collection;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public ArrayList<Integer> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Integer> types) {
        this.types = types;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(int showFlag) {
        this.showFlag = showFlag;
    }
}
