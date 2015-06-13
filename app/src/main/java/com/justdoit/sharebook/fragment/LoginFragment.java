package com.justdoit.sharebook.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
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
import com.justdoit.sharebook.util.SystemStateUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{


    private Button loginButton;
    private Button registButton;
    private TextView testTv;
    private EditText usernameETx;
    private EditText passwdETx;

    private OnRegistClickListener mListener;

    private final String TAG = "LOGIN_ACTIVITY";

    private String params = null;

    public interface OnRegistClickListener {
        public void OnRegistClick();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRegistClickListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
            Log.e(TAG, "OnRegistClickListener cast Error");
        }

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
                mListener.OnRegistClick();
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
                Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(), "请输入用户名", Toast.LENGTH_SHORT).show();
        }

        return false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        if (view != null) {
            testTv = (TextView) view.findViewById(R.id.testTv);

            usernameETx = (EditText) view.findViewById(R.id.usernameETx);
            passwdETx = (EditText) view.findViewById(R.id.passwdETx);

            loginButton = (Button) view.findViewById(R.id.loginBtn);
            registButton = (Button) view.findViewById(R.id.registBtn);

            loginButton.setOnClickListener(this);
            registButton.setOnClickListener(this);
        }
        return view;
    }

    /**
     * 传递两个参数，第一个是网址，第二个是传递的数据
     */
    public class loginTask extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!SystemStateUtil.isNetworkAvailable(getActivity())) {
                cancel(true);
            } else {
                progressDialog = ProgressDialog.show(getActivity(), null, "正在登录(￣▽￣)", true, true);
            }
        }


        @Override
        protected String doInBackground(String... params) {
            if (!isCancelled()) {
                return HttpUtil.login(getActivity(), params[0], params[1]);
            }else {
                return null;
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressDialog.dismiss();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            testTv.setText(s);
        }
    }
}
