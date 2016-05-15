package com.meimei.mmusic.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.meimei.mmusic.Fragment.FragmentHot;
import com.meimei.mmusic.MainActivity;
import com.meimei.mmusic.R;
import com.meimei.mmusic.Util.Player;

public class Activity_songdetail extends AppCompatActivity {
    private TextView songname;
    private TextView singername;
    private ImageView pic_big;
    private SeekBar seekBar;
    private ImageView previous;
    private ImageView play;
    private ImageView next;
    private ImageView retu;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songdetail);

        init();
    }

    private void init(){
        songname = (TextView) findViewById(R.id.tv_songname);
        singername = (TextView) findViewById(R.id.tv_singername);
        pic_big = (ImageView) findViewById(R.id.iv_pic_big);
        previous = (ImageView) findViewById(R.id.iv_previous);
        play = (ImageView) findViewById(R.id.iv_play);
        next = (ImageView) findViewById(R.id.iv_next);
        retu = (ImageView) findViewById(R.id.iv_return);
        seekBar = (SeekBar) findViewById(R.id.skb_Progress);

        player = FragmentHot.player;

        songname.setText(MainActivity.data.get(MainActivity.position).getSongname());
        singername.setText(MainActivity.data.get(MainActivity.position).getSingername());
        pic_big.setImageBitmap(MainActivity.data.get(MainActivity.position).getSongpic_big());

        retu.setOnClickListener(new ClickEvent());
        play.setOnClickListener(new ClickEvent());
        next.setOnClickListener(new ClickEvent());
        previous.setOnClickListener(new ClickEvent());

    }

    class ClickEvent implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_return:
                    Intent intent = new Intent(Activity_songdetail.this,MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.iv_previous:

                    break;
                case R.id.iv_play:
                    if (player.isPlaying()){
                        player.pause();
                        play.setImageResource(R.mipmap.ic_play1);
                    }else {
                        player.play();
                        play.setImageResource(R.mipmap.ic_stop);
                    }
                    break;
                case R.id.iv_next:

                    break;

            }
        }
    }

}
