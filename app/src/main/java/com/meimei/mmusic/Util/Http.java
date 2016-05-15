package com.meimei.mmusic.Util;

import android.util.Log;

import com.google.gson.Gson;
import com.meimei.mmusic.Bean.Bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 梅梅 on 2016/5/15.
 */
public class Http {

    public static Bean bean;

    public static StringBuilder getSongdata(final String URL){

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;

                try {
                    URL url = new URL(URL);
                    Log.i("aa",URL);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    Log.i("aa",URL);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();

                    //下面对获取到的输入流进行读取
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }

                    Gson gson = new Gson();
                    Bean bean = gson.fromJson(response.toString(),Bean.class);
                    Http.bean = bean;
                    Log.i("aa","aa");
                    if (bean == null){
                        Log.i("aa","aa1");
                    }else {
                        Log.i("aa","aa2");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();

        return null;
    }


}
