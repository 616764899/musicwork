package com.meimei.mmusic.Item;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by 梅梅 on 2016/5/14.
 */
public class SongItem implements Serializable{
    private String songname;
    private String singername;
    private String songpicrul_small;
    private String songpicurl_big;
    private Bitmap songpic_small;
    private Bitmap songpic_big;
    private String downurl;
    private String url;

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public void setSongpicrul_small(String songpicrul_small) {
        this.songpicrul_small = songpicrul_small;
    }

    public void setSongpicurl_big(String songpicurl_big) {
        this.songpicurl_big = songpicurl_big;
    }

    public void setSongpic_small(Bitmap songpic_small) {
        this.songpic_small = songpic_small;
    }

    public void setSongpic_big(Bitmap songpic_big) {
        this.songpic_big = songpic_big;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSongname() {
        return songname;
    }

    public String getSingername() {
        return singername;
    }

    public String getSongpicrul_small() {
        return songpicrul_small;
    }

    public String getSongpicurl_big() {
        return songpicurl_big;
    }

    public Bitmap getSongpic_small() {
        return songpic_small;
    }

    public Bitmap getSongpic_big() {
        return songpic_big;
    }

    public String getDownurl() {
        return downurl;
    }

    public String getUrl() {
        return url;
    }
}
