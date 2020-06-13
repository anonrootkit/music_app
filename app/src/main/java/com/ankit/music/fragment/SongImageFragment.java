package com.ankit.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.ankit.music.R;
import com.ankit.music.model.Song;

public class SongImageFragment extends Fragment {

    private ImageView image;

    public SongImageFragment() {
    }

    public static SongImageFragment newInstance(Song song) {
        SongImageFragment fragment = new SongImageFragment();
        Bundle args = new Bundle();
        args.putSerializable("song", song);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.song_image_fragment, container, false);
        Song song = (Song) this.getArguments().get("song");
        image = view.findViewById(R.id.song_image);
        image.setImageDrawable(getContext().getDrawable(song.getClipart()));
        return view;
    }

}