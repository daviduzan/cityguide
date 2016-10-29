package com.keepup.keepup.models;

import java.util.HashMap;

/**
 * Created by david.uzan on 9/16/2016.
 */
public class Place {

    public String mName;
    public String mDescription;
    public String mTitle;
    public HashMap<String, String> mHours;
    public String mImage;

    public static Place fromSM(ServerModels.PlaceSM placeSM) {
        Place place = new Place();
        place.mName = placeSM.name;
        place.mDescription = placeSM.description;
        place.mTitle = placeSM.title;
        place.mHours = placeSM.hours;
        place.mImage = placeSM.image;

        return place;
    }
}
