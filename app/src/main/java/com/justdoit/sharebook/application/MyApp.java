package com.justdoit.sharebook.application;

import android.app.Application;

/**
 * 1.存储application全局变量，比如，cookiestore
 * Created by feimei.zhan on 2015/5/26.
 */
public class MyApp extends Application {

    private boolean isLogin = false;

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
