package com.ankit.music.communication;

import com.ankit.music.R;
import com.ankit.music.model.Song;

import java.util.ArrayList;

public class CommunicationHelper {

    private static int currentSong;
    private static boolean isSongPlaying = false;

    private int cliparts[] = {
            R.drawable.alan_walker_faded,
            R.drawable.maroon_payphone,
            R.drawable.aath_parche_guri_sidhu,
            R.drawable.mankrit_badnaam,
            R.drawable.lehanga_jass,
            R.drawable.alan_walker_faded,
            R.drawable.maroon_payphone,
            R.drawable.aath_parche_guri_sidhu,
            R.drawable.mankrit_badnaam,
            R.drawable.lehanga_jass,
    };

    private String artistNames[] = {
            "Alan Walker",
            "Marron 5",
            "Guri Sidhu",
            "Mankrit Aulakh",
            "Jass manak",
            "Alan Walker",
            "Maroon 5",
            "Guri Sidhu",
            "Mankrit Aulakh",
            "Jass Manak"
    };

    private String songNames[] = {
            "Faded",
            "Payphone",
            "8 Parche",
            "Badnaam",
            "Lehnga",
            "Faded",
            "Payphone",
            "8 Parche",
            "Badnaam",
            "Lehnga"
    };

    public static int getCurrentSong() {
        return currentSong;
    }

    public static void setCurrentSong(int currentSong) {
        CommunicationHelper.currentSong = currentSong;
    }

    public static boolean isSongPlaying() {
        return isSongPlaying;
    }

    public static void setIsSongPlaying(boolean isSongPlaying) {
        CommunicationHelper.isSongPlaying = isSongPlaying;
    }

    public static Song getSong(int position) {
        ArrayList<Song> list = new CommunicationHelper().initSongList();
        return new Song(list.get(position).getClipart(), list.get(position).getSongName(), list.get(position).getArtistName());
    }

    public ArrayList<Song> initSongList() {
        ArrayList<Song> songs = new ArrayList<>();
        for (int i = 0; i < songNames.length; i++) {
            songs.add(new Song(cliparts[i], songNames[i], artistNames[i]));
        }

        return songs;
    }
}
