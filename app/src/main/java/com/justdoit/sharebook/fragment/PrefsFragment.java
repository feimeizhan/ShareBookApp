package com.justdoit.sharebook.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.justdoit.sharebook.R;

/**
 * Created by feimei.zhan on 2015/6/3.
 */
public class PrefsFragment extends PreferenceFragment{

    public PrefsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
