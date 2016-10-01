package com.treats.treats.models;

import java.util.ArrayList;
import java.util.HashMap;

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

    public static class CollectionSM {

        public ArrayList<String> members;
        public ArrayList<Integer> types;
        public String description;
        public int showFlag;

        public CollectionSM() {
        }

        public ArrayList<String> getMembers() {
            return members;
        }

        public ArrayList<Integer> getTypes() {
            return types;
        }

        public String getDescription() {
            return description;
        }

        public int getShowFlag() {
            return showFlag;
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

        String headline_one;
        String headline_two;
        public ArrayList<TrendingItemSM> members;

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
}
