package com.meimei.mmusic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.meimei.mmusic.Activity.Activity_songdetail;
import com.meimei.mmusic.Adapter.FragmentAdapter;
import com.meimei.mmusic.Fragment.FragmentAmerica;
import com.meimei.mmusic.Fragment.FragmentChina;
import com.meimei.mmusic.Fragment.FragmentHot;
import com.meimei.mmusic.Fragment.FragmentTai;
import com.meimei.mmusic.Item.SongItem;
import com.meimei.mmusic.Util.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    public static final int FRAGMENTHOT = 0;
    public static final int SONGDETAIL = 1;
    public static final int FRAGMENTCHINA = 2;
    public static final int FRAGMENTTAI = 3;
    public static final int UPDATA = 4;

    private View detailed;
    private TextView songname;
    private TextView singername;
    private ImageView pic_small;
    private ImageView play;
    private ImageView download;
    private SeekBar seekBar;
    public static List<SongItem> data;
    public static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView(){
        Log.i("1234","initView");
        List<Fragment> fragments = new ArrayList<>();
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_view);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        songname = (TextView) findViewById(R.id.tv_songname);
        singername = (TextView) findViewById(R.id.tv_singername);
        pic_small = (ImageView) findViewById(R.id.iv_pic_small);
        play = (ImageView) findViewById(R.id.ib_play);
        download = (ImageView) findViewById(R.id.ib_download);
        seekBar = (SeekBar) findViewById(R.id.skb_Progress);
        detailed = findViewById(R.id.lin_detailed);
        FragmentHot.player = new Player(seekBar);

        FragmentHot fragmentHot = new FragmentHot(handler);
        FragmentAmerica fragmentAmerica = new FragmentAmerica(handler);
        FragmentChina fragmentChina = new FragmentChina(handler);
        FragmentTai fragmentTai = new FragmentTai(handler);
        fragments.add(fragmentHot);
        fragments.add(fragmentAmerica);
        fragments.add(fragmentChina);
        fragments.add(fragmentTai);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FRAGMENTHOT:
                    data = (List<SongItem>) msg.obj;
                    position = msg.arg1;
                    songname.setText(data.get(position).getSongname());
                    singername.setText(data.get(position).getSingername());
                    getSongpic();

                    play.setImageResource(R.mipmap.ic_stop);
                    play.setOnClickListener(new ClickEvent());
                    download.setOnClickListener(new ClickEvent());
                    break;
                case UPDATA:
                    pic_small.setImageBitmap(data.get(position).getSongpic_small());
                    detailed.setOnClickListener(new ClickEvent());
                    break;
                case SONGDETAIL:
                    songname.setText(data.get(position).getSongname());
                    singername.setText(data.get(position).getSingername());
                    getSongpic();

                    play.setImageResource(R.mipmap.ic_stop);
                    play.setOnClickListener(new ClickEvent());
                    break;

            }
        }
    };

    class ClickEvent implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ib_play:
                    if (FragmentHot.player.isPlaying()){
                        play.setImageResource(R.mipmap.ic_play);
                        FragmentHot.player.pause();
                    }else if (!FragmentHot.player.isPlaying()){
                        play.setImageResource(R.mipmap.ic_stop);
                        FragmentHot.player.play();
                    }
                    break;
                case R.id.lin_detailed:
                    Intent intent = new Intent(MainActivity.this, Activity_songdetail.class);
                    startActivity(intent);
                    break;
                case R.id.ib_download:
                    downLoad();
                    Toast.makeText(MainActivity.this,"下载成功",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }

    private StringBuilder getSongpic(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;

                try {
                    URL url = new URL(data.get(position).getSongpicrul_small());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    Bitmap bitmap_small = BitmapFactory.decodeStream(in);

                    url = new URL(data.get(position).getSongpicurl_big());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    in = connection.getInputStream();
                    Bitmap bitmap_big = BitmapFactory.decodeStream(in);
                    data.get(position).setSongpic_small(bitmap_small);
                    data.get(position).setSongpic_big(bitmap_big);
                    Message message = new Message();
                    message.obj = data;
                    message.what = UPDATA;
                    handler.sendMessage(message);

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

    private StringBuilder downLoad(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                String path="file";
                String fileName= data.get(position).getSongname()+".mp3";
                OutputStream output = null;

                try {
                    URL url = new URL(data.get(position).getDownurl());
                    connection = (HttpURLConnection) url.openConnection();
                    String SDCard = Environment.getExternalStorageDirectory()+"";
                    String pathName = SDCard+"/"+path+"/"+fileName; //文件存储路径

                    File file = new File(pathName);

                    InputStream in = connection.getInputStream();
                    if(file.exists()){
                    }else {
                        String dir = SDCard+"/"+path;
                        new File(dir).mkdir(); //新建文件夹
                        file.createNewFile();  //新建文件
                        output = new FileOutputStream(file);
                        //读取大文件
                        byte[] buffer = new byte[4*1024];
                        while (in.read(buffer) != -1){
                            output.write(buffer);
                        }
                        output.flush();
                    }
                    Message message = new Message();
                    message.obj = data;
                    message.what = UPDATA;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return null;
    }

}
