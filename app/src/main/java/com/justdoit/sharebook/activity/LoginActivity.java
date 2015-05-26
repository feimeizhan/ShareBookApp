package com.justdoit.sharebook.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.justdoit.sharebook.R;
import com.justdoit.sharebook.entity.HttpConstant;
import com.justdoit.sharebook.util.HttpUtil;

public class LoginActivity extends Activity implements View.OnClickListener{

    private Button loginButton;
    private Button registButton;
    private TextView testTv;
    private EditText usernameETx;
    private EditText passwdETx;

    private final String TAG = "LOGIN_ACTIVITY";

    private String params = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        testTv = (TextView) findViewById(R.id.testTv);

        usernameETx = (EditText) findViewById(R.id.usernameETx);
        passwdETx = (EditText) findViewById(R.id.passwdETx);

        loginButton = (Button) findViewById(R.id.loginBtn);
        registButton = (Button) findViewById(R.id.registBtn);

        loginButton.setOnClickListener(this);
        registButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if (checkAndSetParams()) {
//                    Log.e(TAG, params);
                    new loginTask().execute(HttpConstant.LOGIN_URL, params);
                }
                break;
            case R.id.registBtn:
                break;
        }
    }

    /**
     * 检测用户名和密码是否为空
     * @return true表示都非空
     */
    public boolean checkAndSetParams() {
        if (!"".equals(usernameETx.getText().toString().trim())){
            params = "username=" + usernameETx.getText().toString().trim();

            if (!"".equals(passwdETx.getText().toString())) {
                params += HttpConstant.SEPARATOR + "passwd=" + passwdETx.getText().toString();

                return true;
            }else {
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
        }

        return false;

    }

    /**
     * 传递两个参数，第一个是网址，第二个是传递的数据
     */
    public class loginTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(LoginActivity.this, "正在登录", Toast.LENGTH_SHORT).show();
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */


        @Override
        protected String doInBackground(String... params) {
            return HttpUtil.login(LoginActivity.this, params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            testTv.setText(s);
        }
    }
}
