package com.ankit.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ankit.music.R;
import com.ankit.music.adapter.SongAdapter;
import com.ankit.music.communication.CommunicationHelper;
import com.ankit.music.model.Song;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Song> songs;

    private RecyclerView artistList, songList, trendingSongList, favoriteSongList, albumList;
    private SongAdapter artistAdapter, songAdapter, trendingAdapter, favoriteAdapter, albumAdapter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        artistList.setLayoutManager(layoutManager);
        songList.setLayoutManager(layoutManager1);
        trendingSongList.setLayoutManager(layoutManager2);
        favoriteSongList.setLayoutManager(layoutManager3);
        albumList.setLayoutManager(layoutManager4);

        artistList.setAdapter(artistAdapter);
        songList.setAdapter(songAdapter);
        albumList.setAdapter(albumAdapter);
        favoriteSongList.setAdapter(favoriteAdapter);
        trendingSongList.setAdapter(trendingAdapter);
        return view;
    }

    private void init(View view) {
        songList = view.findViewById(R.id.song_list);
        artistList = view.findViewById(R.id.artist_list);
        albumList = view.findViewById(R.id.album_list);
        trendingSongList = view.findViewById(R.id.trending_songs_list);
        favoriteSongList = view.findViewById(R.id.favorite_song_list);
        songs = new CommunicationHelper().initSongList();
        songAdapter = new SongAdapter(getContext(), songs);
        albumAdapter = new SongAdapter(getContext(), songs);
        favoriteAdapter = new SongAdapter(getContext(), songs);
        trendingAdapter = new SongAdapter(getContext(), songs);
        artistAdapter = new SongAdapter(getContext(), songs);
    }
}