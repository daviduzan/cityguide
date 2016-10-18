package com.treats.treats.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by david.uzan on 9/9/2016.
 */
public class ServerModels {


    public static class CategorySM {
        public String name;
        public String collection;
        public String image;

        public CategorySM() {
        }

        public String getName() {
            return name;
        }

        public String getCollection() {
            return collection;
        }

        public String getImage() {
            return image;
        }
    }

    public static class SimpleCollectionSM {

        public Map<String, Boolean> members;
        public String description;

        public SimpleCollectionSM() {
        }

        public Map<String, Boolean> getMembers() {
            return members;
        }

        public String getDescription() {
            return description;
        }

    }

    public static class CollectionCategorySM {

        public ArrayList<String> members;
        public String title;
        public String description;

        public CollectionCategorySM() {
        }

        public ArrayList<String> getMembers() {
            return members;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class PlaceSM {
        public String description;
        public String title;
        public HashMap<String, String> hours;
        public String image;

        public PlaceSM() {
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }

        public HashMap<String, String> getHours() {
            return hours;
        }

        public String getImage() {
            return image;
        }
    }

    public static class TrendingGroupSM {

        public ArrayList<TrendingItemSM> members;
        String headline_one;
        String headline_two;

        public TrendingGroupSM() {
        }

        public String getHeadline_one() {
            return headline_one;
        }

        public String getHeadline_two() {
            return headline_two;
        }

        public ArrayList<TrendingItemSM> getMembers() {
            return members;
        }
    }


    public static class TrendingItemSM {

        public String image;
        public String place;
        public String collection;

        public TrendingItemSM() {
        }

        public String getImage() {
            return image;
        }

        public String getPlace() {
            return place;
        }

        public String getCollection() {
            return collection;
        }
    }

    public static class UserSM {

        public String name;
        public Map<String, UserListSM> lists;
        public UserSM() {
        }

        public String getName() {
            return name;
        }

        public Map<String, UserListSM> getLists() {
            return lists;
        }
    }

    public static class UserListSM {

        String name;
        Map<String, Boolean> places;
        public UserListSM() {
        }

        public String getName() {
            return name;
        }

        public Map<String, Boolean> getPlaces() {
            return places;
        }
    }
}
