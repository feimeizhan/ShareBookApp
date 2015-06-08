package com.justdoit.sharebook.business;

import android.content.Context;
import android.util.Log;

import com.justdoit.sharebook.application.HttpConstant;
import com.justdoit.sharebook.entity.UserInfo;
import com.justdoit.sharebook.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by feimei.zhan on 2015/5/21.
 */
public class UserInfoBO {
    private final String TAG = "USER_INFO_BO";

    public UserInfo getUserInfo(Context context) {
        String str = HttpUtil.HttpGET(context, HttpConstant.USER_INFO_URL);

        if (str != null) {
            return jsonToUserInfo(str);
        } else {
            return null;
        }
    }

    public UserInfo jsonToUserInfo(String jsonStr) {
        UserInfo userInfo = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            userInfo = new UserInfo();
            userInfo.setUserName(jsonObject.getString("UserName"));
            userInfo.setMajor(jsonObject.getString("Major"));
            userInfo.setPhoneNum(jsonObject.getString("Phone_num"));
            userInfo.setAcademy(jsonObject.getString("Academy"));
            userInfo.setSchool(jsonObject.getString("School"));
            userInfo.setSex(jsonObject.getString("Sex"));
            userInfo.setRegistDate(jsonObject.getString("RegistDate"));
            userInfo.setId(jsonObject.getInt("ID"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "new JsonObject() failed");
        } finally {
            return userInfo;
        }
    }
}
