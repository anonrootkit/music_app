package com.ankit.music.model;

import java.io.Serializable;

public class Song implements Serializable {
    private int clipart;
    private String songName;
    private String artistName;

    public Song() {
    }

    public Song(int clipart, String songName, String artistName) {
        this.clipart = clipart;
        this.songName = songName;
        this.artistName = artistName;
    }

    public int getClipart() {
        return clipart;
    }

    public void setClipart(int clipart) {
        this.clipart = clipart;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
