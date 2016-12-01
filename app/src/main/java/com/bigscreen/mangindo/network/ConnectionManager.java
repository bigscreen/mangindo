package com.bigscreen.mangindo.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectionManager {

    public static int TYPE_WIFI = 1;

    public static int TYPE_MOBILE = 2;

    public static int TYPE_NOT_CONNECTED = 0;

    private Context context;

    public ConnectionManager(Context context) {
        this.context = context;
    }

    public int getConnectivityStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected() && activeNetwork.isAvailable()) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public boolean isConnected() {
        return getConnectivityStatus() != TYPE_NOT_CONNECTED;
    }
}
