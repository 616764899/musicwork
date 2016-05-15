package com.meimei.mmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.meimei.mmusic.Adapter.RecyclerViewAdapter;
import com.meimei.mmusic.Bean.Bean;
import com.meimei.mmusic.Item.SongItem;
import com.meimei.mmusic.MainActivity;
import com.meimei.mmusic.R;
import com.meimei.mmusic.Service.LockScreenService;
import com.meimei.mmusic.Util.Player;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梅梅 on 2016/5/14.
 */
public class FragmentHot extends Fragment {

    private View view;
    private View view1;
    private RecyclerView rv_hot;
    private RecyclerViewAdapter recyclerViewAdapter;
    public static final int SHOW_SONG = 0;
    public static List<SongItem> data;
    public static int pos;
    public static Player player;
    Handler handler_activity;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOW_SONG:
                    data = (List<SongItem>) msg.obj;
                    Log.i("1234","handleMessage");
                    recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),data,R.layout.fragment_hot_item);
                    rv_hot.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String url = data.get(position).getUrl();
                            if (!player.isPlaying()){
                                player.playUrl(url);
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), LockScreenService.class);
                                getActivity().startService(intent);
                                Log.i("aa","锁屏服务启动");

                            }else if (player.isPlaying()){
                                player.reSet();
                                player.playUrl(url);
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), LockScreenService.class);
                                getActivity().startService(intent);
                                Log.i("aa","锁屏服务启动");
                            }
                            pos = position;
                            Message message = new Message();
                            message.what = MainActivity.FRAGMENTHOT;
                            message.obj = data;
                            message.arg1 = position;
                            handler_activity.sendMessage(message);

                        }

                       @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });

            }
        }
    };

    public FragmentHot(Handler handler_activity){
        super();
        this.handler_activity = handler_activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_hot,container,false);

        init();
        getSongdata();
        return view;
    }

    private void init(){
        rv_hot = (RecyclerView) view.findViewById(R.id.recyclerview_song);
        rv_hot.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private StringBuilder getSongdata(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;

                try {
                    URL url = new URL("https://route.showapi.com/213-4?showapi_appid=19025&topid=4&showapi_sign=cb0d53bafc8045d5865d96ca67df23f0");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
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

                    parseJSONWithGson(response.toString());
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

    private void parseJSONWithGson(String jsonData){
        Gson gson = new Gson();
        Bean bean = gson.fromJson(jsonData,Bean.class);
        data = new ArrayList<>();

        for(int i=0;i < 100; i++){
            SongItem item = new SongItem();
            item.setSongname(bean.getShowapi_res_body().getPagebean().getSonglist().get(i).getSongname());
            item.setSingername(bean.getShowapi_res_body().getPagebean().getSonglist().get(i).getSingername());
            item.setUrl(bean.getShowapi_res_body().getPagebean().getSonglist().get(i).getUrl());
            item.setDownurl(bean.getShowapi_res_body().getPagebean().getSonglist().get(i).getDownUrl());
            item.setSongpicurl_big(bean.getShowapi_res_body().getPagebean().getSonglist().get(i).getAlbumpic_big());
            item.setSongpicrul_small(bean.getShowapi_res_body().getPagebean().getSonglist().get(i).getAlbumpic_small());
            data.add(item);
        }
        Message message = new Message();
        message.what = SHOW_SONG;
        message.obj = data;
        handler.sendMessage(message);
    }


}
