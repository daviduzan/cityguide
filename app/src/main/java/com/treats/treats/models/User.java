package com.treats.treats.models;

import java.util.ArrayList;

/**
 * Created by david.uzan on 10/1/2016.
 */

public class User {

    private String mName;
    private ArrayList<UserList> mUserLists;

    public User() {
        mUserLists = new ArrayList<>();
    }

    public static User fromSM(ServerModels.UserSM userSM) {
        User user = new User();
        user.mName = userSM.getName();
        user.mUserLists = new ArrayList<>();
        for (String key : userSM.getLists().keySet()) {
            ServerModels.UserListSM listSM = userSM.getLists().get(key);
            user.mUserLists.add(UserList.fromSM(listSM));
        }

        return user;
    }

    public String getName() {
        return mName;
    }

    public ArrayList<UserList> getUserLists() {
        return mUserLists;
    }
}
