package com.justdoit.sharebook.activity.user;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.justdoit.sharebook.R;
import com.justdoit.sharebook.fragment.LoginFragment;
import com.justdoit.sharebook.fragment.RegistFragment;

public class LoginActivity extends Activity implements LoginFragment.OnRegistClickListener{
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        LoginFragment loginFragment = new LoginFragment();
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.login_container, loginFragment);
        ft.commit();
    }

    @Override
    public void OnRegistClick() {
        RegistFragment registFragment = new RegistFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(R.id.login_container, registFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
