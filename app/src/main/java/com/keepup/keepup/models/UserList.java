package com.keepup.keepup.models;

import java.util.ArrayList;

/**
 * Created by david.uzan on 10/1/2016.
 */

public class UserList {
    private String mName;
    private ArrayList<String> mPlaces;

    static UserList fromSM(ServerModels.UserListSM listSM) {
        UserList userList = new UserList();
        userList.mName = listSM.name;
        userList.mPlaces = new ArrayList<>();
        if (listSM.places != null) {
            for (String placeName : listSM.places.keySet()) {
                userList.mPlaces.add(placeName);
            }
        }
        return userList;
    }

    public String getName() {
        return mName;
    }

    public ArrayList<String> getPlaces() {
        return mPlaces;
    }

}
