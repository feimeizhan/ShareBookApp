package com.justdoit.sharebook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.justdoit.sharebook.R;
import com.justdoit.sharebook.activity.user.UserInfoActivity;
import com.justdoit.sharebook.application.MyApp;

/**
 * Created by feimei.zhan on 2015/6/3.
 */
public class PrefsFragment extends PreferenceFragment{

    private MyApp myApp;

    public PrefsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        init();

    }

    private void init() {
        myApp = (MyApp) getActivity().getApplicationContext();

        Preference usernamePrefs = findPreference("username");
        usernamePrefs.setSummary(myApp.getSp().getString(MyApp.USER_NAME, "unknown"));

        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        usernamePrefs.setIntent(intent);

    }
}
