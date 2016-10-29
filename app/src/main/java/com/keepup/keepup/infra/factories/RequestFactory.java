package com.keepup.keepup.infra.factories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by david.uzan on 4/19/2016.
 */
public class RequestFactory {

    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    private static final String CONTENT_CHARSET_UTF_8 = "utf-8";
    private static Gson mGson = new GsonBuilder().setPrettyPrinting().create();

    private static Map<String, String> getDefaultHeaders() {
        //TODO check and update hardcoded session mId header
        Map<String, String> headers = new HashMap<>();
//        headers.put("contentType", "application/json; charset=utf-8");
        headers.put("contentType", CONTENT_TYPE_APPLICATION_JSON);
        return headers;
    }

    private static String getJson(Object object) {
        return mGson.toJson(object);
    }


    public enum RequestType {
    }

}
