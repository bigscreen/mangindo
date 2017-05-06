package com.bigscreen.mangindo.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utils {

    public static String getEncodedUrl(String url) {
        try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static boolean isTextNotNullOrEmpty(String text) {
        return !(text == null || text.isEmpty());
    }
    
    public static boolean isNotAdsUrl(String url) {
        String[] adsKeywords = {"iklan", "all_anime", "ik.jpg", "rekrut", "ilan.jpg", "animeindonesia",
                "IKLAN2", "Credit", "animeindonesia"};
        for (String keyword : adsKeywords) {
            if (url.contains(keyword)) return false;
        }
        return true;
    }
}
