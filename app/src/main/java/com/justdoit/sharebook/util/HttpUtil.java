package com.justdoit.sharebook.util;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络操作工具
 * Created by feimei.zhan on 2015/5/21.
 */
public class HttpUtil {
    private static final String TAG = "HTTP_UTIL";
    private static CookieManager cookieManager;
    private static String token;
    private static final String tokenKey = "_xsrf";
    private static final int MAX_SIZE_BUF = 1024;

    /**
     * 发送login post请求，返回字符串结果
     * @param context
     * @param urlStr 网址字符串
     * @param postStr 传递数据字符串(username&passwd)
     * @return 处理结果字符串
     */
    public static String login(Context context, String urlStr, String postStr) {

        String result = null;

        try {
            URL url = new URL(urlStr);
            init();
            initToken(url);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);

            String params = token + "&" + postStr;

            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            out.write(params.getBytes());
            out.flush();
            out.close();

            Log.e(TAG, connection.getResponseMessage());
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                result = new String(getBytesFromInputStream(in));
                for (HttpCookie cookie : cookieManager.getCookieStore().getCookies()) {
                    Log.e(TAG, "Name-->" + cookie.getName() + " value-->" + cookie.getValue());
                }
//                Log.e(TAG, result);
                in.close();
            }

            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "new URL() failed");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "url.openConnection() failed");
        }

        return result;
    }

    /**
     * 从stream中读取数据
     * @param inputStream
     * @return
     */
    public static byte[] getBytesFromInputStream(InputStream inputStream) {
        byte[] buf = new byte[MAX_SIZE_BUF];
        int len;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            while ((len = inputStream.read(buf)) != -1) {
                bout.write(buf, 0, len);
            }

            bout.close();

            return bout.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "InputStream.read() failed");
        }

        return null;
    }

    /**
     * 获取token值
     * @param url 被获取token的网站
     */
    public static void initToken(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                for (String cookie : connection.getHeaderFields().get("Set-Cookie")) {
                    if (cookie.indexOf(tokenKey) == 0) {
                        token = cookie.substring(0, cookie.indexOf(";"));
                    }
                }
            }else {
                token = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "openConnection() failed");
        }

    }

    /**
     * 初始化CookieManager
     */
    private static void init() {
        cookieManager = new CookieManager(new PersistentCookieStore(), CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
    }

    public static class PersistentCookieStore implements CookieStore {
        private CookieStore cookieStore;

        public PersistentCookieStore() {
            cookieStore = new CookieManager().getCookieStore();
        }

        @Override
        public void add(URI uri, HttpCookie cookie) {
            cookieStore.add(uri, cookie);
        }

        @Override
        public List<HttpCookie> get(URI uri) {
            return cookieStore.get(uri);
        }

        @Override
        public List<HttpCookie> getCookies() {
            return cookieStore.getCookies();
        }

        @Override
        public List<URI> getURIs() {
            return cookieStore.getURIs();
        }

        @Override
        public boolean remove(URI uri, HttpCookie cookie) {
            return cookieStore.remove(uri, cookie);
        }

        @Override
        public boolean removeAll() {
            return cookieStore.removeAll();
        }
    }
}
