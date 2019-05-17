package com.aphrodite.framework.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

/**
 * Created by Aphrodite on 2019/5/17.
 */
public class NetworkUtils {
    /**
     * Wifi
     */
    public static final int NET_TYPE_WIFI = 0x01;
    /**
     * 2G网
     */
    public static final int NET_TYPE_CMWAP = 0x02;
    /**
     * 3G网
     */
    public static final int NET_TYPE_CMNET = 0x03;

    /**
     * 获取当前设备是否能连接到网络
     *
     * @return true:能连接网络
     * false:无法连接网络
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前网络类型
     *
     * @return 网络类型
     * @see #NET_TYPE_WIFI
     * @see #NET_TYPE_CMWAP
     * @see #NET_TYPE_CMNET
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if ("cmnet".equals(extraInfo.toLowerCase())) {
                    netType = NET_TYPE_CMNET;
                } else {
                    netType = NET_TYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NET_TYPE_WIFI;
        }
        return netType;
    }
}
