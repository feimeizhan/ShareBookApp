package com.justdoit.sharebook.customized;

import android.app.Activity;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import com.justdoit.sharebook.application.MyApp;
import com.justdoit.sharebook.util.HttpUtil;

/**
 * Created by feimei.zhan on 2015/6/3.
 */
public class MyDialogPreference extends DialogPreference {


    public MyDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            MyApp myApp = (MyApp)getContext().getApplicationContext();
            myApp.getSp().edit().clear();
            myApp.setIsLogin(false);
            HttpUtil.clearCookies();
            ((Activity) getContext()).finish();
        }
    }
}
