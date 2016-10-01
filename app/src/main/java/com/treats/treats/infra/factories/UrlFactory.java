package com.treats.treats.infra.factories;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by david.uzan on 4/17/2016.
 */
public class UrlFactory {

    /**
     * Domain
     */

    /**
     * QA Domains
     */

    /**
     * Address
     */

    /**
     * Parameters
     */

    private static String encodeParameter(int parameter) {
        return encodeParameter(String.valueOf(parameter));
    }

    private static String encodeParameter(boolean parameter) {
        return encodeParameter(String.valueOf(parameter));
    }

    private static String encodeParameter(String parameter) {
        String encodedParam = "";
        try {
            encodedParam = URLEncoder.encode(parameter, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedParam;
    }
}
