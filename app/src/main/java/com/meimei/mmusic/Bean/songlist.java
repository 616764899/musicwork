package com.meimei.mmusic.Bean;

/**
 * Created by 梅梅 on 2016/5/14.
 */
public class songlist {
    private int albumid;
    private String albummid;
    private String albumpic_big;
    private String albumpic_small;
    private String downUrl;
    private int seconds;
    private int singerid;
    private String singername;
    private int songid;
    private String songname;
    private String url;

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }

    public void setAlbummid(String albummid) {
        this.albummid = albummid;
    }

    public void setAlbumpic_big(String albumpic_big) {
        this.albumpic_big = albumpic_big;
    }

    public void setAlbumpic_small(String albumpic_small) {
        this.albumpic_small = albumpic_small;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setSingerid(int singerid) {
        this.singerid = singerid;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAlbumid() {
        return albumid;
    }

    public String getAlbummid() {
        return albummid;
    }

    public String getAlbumpic_big() {
        return albumpic_big;
    }

    public String getAlbumpic_small() {
        return albumpic_small;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getSingerid() {
        return singerid;
    }

    public String getSingername() {
        return singername;
    }

    public int getSongid() {
        return songid;
    }

    public String getSongname() {
        return songname;
    }

    public String getUrl() {
        return url;
    }
}
