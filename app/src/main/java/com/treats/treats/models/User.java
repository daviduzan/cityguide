package com.treats.treats.models;

import java.util.ArrayList;

/**
 * Created by david.uzan on 10/1/2016.
 */

public class User {

    private String mName;
    private ArrayList<UserList> mUserLists;
    private String mDeviceModel;
    private String mDeviceManufacturer;

    public User() {
        mUserLists = new ArrayList<>();
    }

    public static User fromSM(ServerModels.UserSM userSM) {
        User user = new User();
        if (userSM == null) return user;
        user.mName = userSM.name;
        user.mDeviceModel = userSM.deviceModel;
        user.mDeviceManufacturer = userSM.deviceManufacturer;
        if (userSM.lists != null) {
            for (String key : userSM.lists.keySet()) {
                ServerModels.UserListSM listSM = userSM.lists.get(key);
                user.mUserLists.add(UserList.fromSM(listSM));
            }
        }
        return user;
    }

    public String getName() {
        return mName;
    }

    public ArrayList<UserList> getUserLists() {
        return mUserLists;
    }

    public String getDeviceModel() {
        return mDeviceModel;
    }

    public String getDeviceManufacturer() {
        return mDeviceManufacturer;
    }
}
