package com.ankit.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ankit.music.R;
import com.ankit.music.communication.CommunicationHelper;
import com.ankit.music.interfaces.OpenMusicFragment;
import com.ankit.music.model.Song;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private ArrayList<Song> songs;
    private Context context;

    public SongAdapter(Context context, ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, final int position) {
        holder.clipart.setImageDrawable(context.getDrawable(songs.get(position).getClipart()));
        holder.songName.setText(songs.get(position).getSongName());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommunicationHelper.setIsSongPlaying(true);
                CommunicationHelper.setCurrentSong(position);
                OpenMusicFragment openMusicFragment = (OpenMusicFragment) context;
                openMusicFragment.openMusicFragment();
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView clipart;
        public TextView songName;
        public CardView container;

        public ViewHolder(View view) {
            super(view);
            clipart = view.findViewById(R.id.clipart);
            songName = view.findViewById(R.id.song_name);
            container = view.findViewById(R.id.song_item_container);
        }
    }


}
