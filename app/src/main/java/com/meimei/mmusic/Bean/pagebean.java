package com.meimei.mmusic.Bean;

import java.util.List;

/**
 * Created by 梅梅 on 2016/5/14.
 */
public class pagebean {
    private int currentPage;
    private int song_begin;
    private List<songlist> songlist;

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setSong_begin(int song_begin) {
        this.song_begin = song_begin;
    }

    public void setSonglist(List<com.meimei.mmusic.Bean.songlist> songlist) {
        this.songlist = songlist;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getSong_begin() {
        return song_begin;
    }

    public List<com.meimei.mmusic.Bean.songlist> getSonglist() {
        return songlist;
    }
}
