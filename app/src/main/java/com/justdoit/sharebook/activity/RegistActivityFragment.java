package com.justdoit.sharebook.activity;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.justdoit.sharebook.R;
import com.justdoit.sharebook.application.HttpConstant;
import com.justdoit.sharebook.util.HttpUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegistActivityFragment extends Fragment implements View.OnClickListener{

    private Button registBtn;
    private EditText usernameETx;
    private EditText passwdETx;
    private EditText confirmPasswdETx;

    private final String USERNAME = "username";
    private final String PASSWORD = "passwd";
    private final String CONFIRM_PASSWORD = "passwd1";


    private String postStr = new String();

    private TextView tv;

    public RegistActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_regist, container, false);

        registBtn = (Button) view.findViewById(R.id.registBtn);
        usernameETx = (EditText) view.findViewById(R.id.regist_usernameETx);
        passwdETx = (EditText) view.findViewById(R.id.regist_passwdETx);
        confirmPasswdETx = (EditText) view.findViewById(R.id.regist_confirm_passwdETx);
        tv = (TextView) view.findViewById(R.id.showTx);

        registBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registBtn:
                if (checkLegal()) {
                    new registTask().execute(HttpConstant.REGIST_URL, postStr);
                }
                break;
        }
    }

    public boolean checkLegal() {
        String userName;
        String passwd;
        String confirmPasswd;

        userName = String.valueOf(usernameETx.getText());
        passwd = String.valueOf(passwdETx.getText());
        confirmPasswd = String.valueOf(confirmPasswdETx.getText());

        if ("".equals(userName)) {
            Toast.makeText(getActivity(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }else if ("".equals(passwd)) {
            Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }else if (passwd.length() < 6) {
            Toast.makeText(getActivity(), "密码不能少于六位", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!passwd.equals(confirmPasswd)) {
            Toast.makeText(getActivity(), "密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }

        postStr = USERNAME + "=" + userName + "&" + PASSWORD + "=" + passwd + "&" + CONFIRM_PASSWORD + "=" + confirmPasswd;

        return true;
    }

    public class registTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return HttpUtil.login(getActivity(), params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv.setText(s);
        }
    }
}
