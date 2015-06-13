package com.justdoit.sharebook.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.NetworkInterface;

/**
 * 获取系统状态工具
 * Created by ljz on 2015/5/26.
 */
public class SystemStateUtil {

    public SystemStateUtil(){

    }

    public SystemStateUtil getSystemStateUtil (){
        return new SystemStateUtil();
    }

    public static boolean isWifi(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetInfo != null & activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI){
            return true;
        }
        return false;
    }

    /**
     * 检测是否有网络(2G/3G/4G/wifi)
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();
    }
}

