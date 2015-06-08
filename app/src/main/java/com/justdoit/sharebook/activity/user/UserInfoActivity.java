package com.justdoit.sharebook.activity.user;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.justdoit.sharebook.R;
import com.justdoit.sharebook.application.HttpConstant;
import com.justdoit.sharebook.business.UserInfoBO;
import com.justdoit.sharebook.entity.UserInfo;
import com.justdoit.sharebook.util.HttpUtil;

public class UserInfoActivity extends Activity {

    private TextView username;
    private TextView major;
    private TextView academy;
    private TextView school;
    private TextView sex;
    private TextView id;
    private TextView phonenum;
    private TextView registDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        init();

        new GetUserInfoTask().execute();
    }

    public void init() {
        username = (TextView) findViewById(R.id.userinfo_usernameTx);
        major = (TextView) findViewById(R.id.userinfo_majorTx);
        academy = (TextView) findViewById(R.id.userinfo_academyTx);
        sex = (TextView) findViewById(R.id.userinfo_sexTx);
        school = (TextView) findViewById(R.id.userinfo_schoolTx);
        id = (TextView) findViewById(R.id.userinfo_idTx);
        phonenum = (TextView) findViewById(R.id.userinfo_phonenumTx);
        registDate = (TextView) findViewById(R.id.userinfo_registdateTx);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class GetUserInfoTask extends AsyncTask<Void, Void, UserInfo> {

        UserInfoBO userInfoBO = new UserInfoBO();

        @Override
        protected UserInfo doInBackground(Void... params) {

            return userInfoBO.getUserInfo(UserInfoActivity.this);
        }

        @Override
        protected void onPostExecute(UserInfo userInfo) {
            super.onPostExecute(userInfo);

            if (userInfo != null) {
                username.append(userInfo.getUserName());
                school.append(userInfo.getSchool());
                academy.append(userInfo.getAcademy());
                sex.append(userInfo.getSex());
                phonenum.append(userInfo.getPhoneNum());
                id.append(String.valueOf(userInfo.getId()));
                major.append(userInfo.getMajor());
                registDate.append(userInfo.getRegistDate());
            }
        }
    }
}
