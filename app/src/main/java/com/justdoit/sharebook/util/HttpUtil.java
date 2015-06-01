package com.justdoit.sharebook.util;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 网络操作工具
 * Created by feimei.zhan on 2015/5/21.
 */
public class HttpUtil {
    private static final String TAG = "HTTP_UTIL";
    private static CookieManager cookieManager;
    private static SharedPreferences sharedPreferences;
    private static final String COOKIE_STORE_NAME = "userInfoPrefs";
    private static String token = null;
    private static final String tokenKey = "_xsrf";
    private static final int MAX_SIZE_BUF = 1024;


    /**
     * 封装HTTP GET 方法
     * @param context
     * @param urlStr 网址
     * @return get的结果字符串
     */
    public static String HttpGET(Context context, String urlStr) {
        String result = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlStr);
            init(context, url.toURI());

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);

            connection.connect();
            Log.e(TAG, connection.getResponseMessage());
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                result = new String(getBytesFromInputStream(in));
                in.close();
            }

            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "new URL() failed");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.e(TAG, "url.toURI() failed");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "url.openConnection() failed");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

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
            init(context, url.toURI());
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

                sharedPreferences = context.getSharedPreferences(COOKIE_STORE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                for (HttpCookie cookie : cookieManager.getCookieStore().getCookies()) {
                    Log.e(TAG, "Name-->" + cookie.getName() + " value-->" + cookie.getValue());
                    editor.putString(cookie.getName(), cookie.getValue());
                }

                editor.commit();
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
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.e(TAG, "url to uri failed");
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
            for (HttpCookie cookie : cookieManager.getCookieStore().get(url.toURI())) {
                if (tokenKey.equals(cookie.getName())) {
                    token = tokenKey + "=" + cookie.getValue();
                    break;
                }
            }

            if (token == null) {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    for (String cookie : connection.getHeaderFields().get("Set-Cookie")) {
                        if (cookie.indexOf(tokenKey) == 0) {
                            token = cookie.substring(0, cookie.indexOf(";"));
                        }
                    }
                } else {
                    token = null;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "openConnection() failed");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.e(TAG, "url.toURI() failed");
        }

    }

    /**
     * 如果cookiemanager为空，初始化CookieManager
     */
    private static void init(Context context, URI uri) {
        if (cookieManager == null) {
            Log.e(TAG, "cookieManager is null");
            cookieManager = new CookieManager(new PersistentCookieStore(context, uri), CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cookieManager);
        }
    }

    /**
     * 保存用户登录cookie的自定义cookiestore
     */
    public static class PersistentCookieStore implements CookieStore {
        private CookieStore cookieStore;

        public PersistentCookieStore(Context context, URI uri) {
            cookieStore = new CookieManager().getCookieStore();

            SharedPreferences sp = context.getSharedPreferences(COOKIE_STORE_NAME, Context.MODE_PRIVATE);

            for (Map.Entry<String, ?> entry : sp.getAll().entrySet()) {
                HttpCookie cookie = new HttpCookie(entry.getKey(), (String) entry.getValue());
                cookieStore.add(uri, cookie);
            }
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
