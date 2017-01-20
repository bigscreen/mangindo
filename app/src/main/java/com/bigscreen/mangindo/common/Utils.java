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
}
