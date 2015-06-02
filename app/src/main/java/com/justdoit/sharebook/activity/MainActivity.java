package com.justdoit.sharebook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.justdoit.sharebook.R;
import com.justdoit.sharebook.application.MyApp;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button mainLoginBtn;
    private Button mainRegistBtn;
    private MyApp myApp;

    /**
     * 初始化函数
     */
    public void init() {
        myApp = (MyApp) getApplicationContext();

        mainLoginBtn = (Button) findViewById(R.id.main_login_btn);
        mainRegistBtn = (Button) findViewById(R.id.main_regist_btn);

        mainRegistBtn.setOnClickListener(this);
        mainLoginBtn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_login_btn:
                if (myApp.isLogin()) {
                    Toast.makeText(MainActivity.this, "你已经登录了-_-", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.main_regist_btn:
                Intent intent = new Intent(MainActivity.this, RegistActivity.class);
                startActivity(intent);
                break;
        }
    }
}
