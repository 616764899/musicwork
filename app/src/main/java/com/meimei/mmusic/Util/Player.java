package com.meimei.mmusic.Util;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 梅梅 on 2016/5/15.
 */
public class Player implements MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener {
    public MediaPlayer mediaPlayer;
    SeekBar seekBar;
    private Timer timer = new Timer();

    public Player(SeekBar seekBar){

        this.seekBar = seekBar;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnPreparedListener(this);
        timer.schedule(timerTask,0,1000);

    }

    /**
     * 通过定时器和Handler来更新进度条
     */

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (mediaPlayer==null)
                return;
            if(mediaPlayer.isPlaying() && seekBar.isPressed() == false){
                handler.sendEmptyMessage(0);
            }
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int position = mediaPlayer.getCurrentPosition(); //歌曲当前播放到什么时间
            int duration = mediaPlayer.getDuration(); //歌曲总共时长

            if (duration > 0){
                long pos = seekBar.getMax() * position / duration;
                seekBar.setProgress((int) pos);
            }
        }
    };



    public void play(){
        mediaPlayer.start();
    }

    public void playUrl(String videoUrl){
        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(videoUrl);
            mediaPlayer.prepare();// prepare()之后自动播放
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public void stop(){
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    public void reSet(){
        mediaPlayer.reset();
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
        int currentProgress = seekBar.getMax()* mediaPlayer.getCurrentPosition()
                /mediaPlayer.getDuration();
        Log.i("aa",currentProgress+"% play"+percent+"% buffer");
    }
}
