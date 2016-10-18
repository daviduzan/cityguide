package com.treats.treats.models;

import java.util.ArrayList;

/**
 * Created by david.uzan on 10/1/2016.
 */

public class UserList {
    private String mName;
    private ArrayList<String> mPlaces;

    static UserList fromSM(ServerModels.UserListSM listSM) {
        UserList userList = new UserList();
        userList.mName = listSM.getName();
        userList.mPlaces = new ArrayList<>();
        for (String placeName : listSM.getPlaces().keySet()) {
            userList.mPlaces.add(placeName);
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
