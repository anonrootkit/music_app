package com.ankit.music.fragment;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ankit.music.R;
import com.ankit.music.adapter.CustomPagerAdapter;
import com.ankit.music.communication.CommunicationHelper;
import com.ankit.music.model.Song;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicFragment extends Fragment {

    private ImageView back;
    private ViewPager viewPager;
    private TextView songName, artistName;
    private CustomPagerAdapter adapter;
    private MediaPlayer music;
    private ImageView startStop, previous, next;
    private TextView startTime, endTime;
    private SeekBar seekBar;
    private int sTime, eTime;
    private Handler handler = new Handler();
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            sTime = music.getCurrentPosition() / 1000;
            int secs = (int) (sTime - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(sTime)));
            startTime.setText(String.format("%d:%s", TimeUnit.SECONDS.toMinutes(sTime), secs < 10 ? String.format("0%d", secs) : String.valueOf(secs)));

            seekBar.setProgress(sTime);
            handler.postDelayed(this, 100);
        }
    };

    public MusicFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        initViews(view);
        initViewpager();
        startPlayer();
        initSeekbar();
        songName.setText(CommunicationHelper.getSong(CommunicationHelper.getCurrentSong()).getSongName());
        artistName.setText(CommunicationHelper.getSong(CommunicationHelper.getCurrentSong()).getArtistName());
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                switchToNextSong();
            }
        });
        return view;
    }

    private void initViewpager() {
        ArrayList<Song> list = new CommunicationHelper().initSongList();
        adapter = new CustomPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        for (int i = 0; i < list.size(); i++) {
            adapter.addFragment(SongImageFragment.newInstance(CommunicationHelper.getSong(i)));
        }
        viewPager.setSaveFromParentEnabled(false);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(CommunicationHelper.getCurrentSong(), true);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(80, 0, 80, 0);
        viewPager.setPageMargin(40);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                stopPlayer();
                songName.setText(CommunicationHelper.getSong(position).getSongName());
                artistName.setText(CommunicationHelper.getSong(position).getArtistName());
                startPlayer();
                music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        switchToNextSong();
                    }
                });
                handler = new Handler();
                handler.postDelayed(UpdateSongTime, 100);
                CommunicationHelper.setCurrentSong(position);
                startStop.setImageDrawable(getContext().getDrawable(R.drawable.pause_button));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initViews(View view) {
        back = view.findViewById(R.id.song_fragment_back);
        viewPager = view.findViewById(R.id.song_viewpager);
        artistName = view.findViewById(R.id.artist_name_title);
        songName = view.findViewById(R.id.song_name_title);
        startStop = view.findViewById(R.id.start_stop);
        previous = view.findViewById(R.id.previous_song);
        next = view.findViewById(R.id.next_song);
        previous = view.findViewById(R.id.previous_song);
        next = view.findViewById(R.id.next_song);
        startTime = view.findViewById(R.id.start_time);
        endTime = view.findViewById(R.id.end_time);
        seekBar = view.findViewById(R.id.song_seekbar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (music != null && b) {
                    music.seekTo(i * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).onBackPressed();
            }
        });

        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (music.isPlaying()) {
                    music.pause();
                    startStop.setImageDrawable(getContext().getDrawable(R.drawable.play_button));
                } else {
                    music.start();
                    startStop.setImageDrawable(getContext().getDrawable(R.drawable.pause_button));
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToNextSong();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToPreviousSong();
            }
        });
    }

    private void switchToPreviousSong() {
        if (CommunicationHelper.getCurrentSong() == 0) {
            CommunicationHelper.setCurrentSong(new CommunicationHelper().initSongList().size() - 1);
            viewPager.setCurrentItem(CommunicationHelper.getCurrentSong(), true);
        } else if (CommunicationHelper.getCurrentSong() > 0) {
            CommunicationHelper.setCurrentSong(CommunicationHelper.getCurrentSong() - 1);
            viewPager.setCurrentItem(CommunicationHelper.getCurrentSong(), true);
        }
    }

    private void switchToNextSong() {
        if (CommunicationHelper.getCurrentSong() == new CommunicationHelper().initSongList().size() - 1) {
            CommunicationHelper.setCurrentSong(0);
            viewPager.setCurrentItem(0, true);
        } else if (CommunicationHelper.getCurrentSong() >= 0) {
            CommunicationHelper.setCurrentSong(CommunicationHelper.getCurrentSong() + 1);
            viewPager.setCurrentItem(CommunicationHelper.getCurrentSong(), true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumePlayer();
    }

    private void resumePlayer() {
        if (music != null && !music.isPlaying()) {
            music.start();
            startStop.setImageDrawable(getContext().getDrawable(R.drawable.pause_button));
        }
    }

    private void initSeekbar() {
        sTime = music.getCurrentPosition() / 1000;
        eTime = music.getDuration() / 1000;

        int secs = (int) (sTime - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(sTime)));
        startTime.setText(String.format("%d:%s", TimeUnit.SECONDS.toMinutes(sTime), secs < 10 ? String.format("0%d", secs) : String.valueOf(secs)));

        endTime.setText(String.format("%d:%d",
                TimeUnit.SECONDS.toMinutes(eTime),
                eTime - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(eTime))));
        seekBar.setMax(eTime);
        seekBar.setProgress(sTime);
        handler.removeCallbacks(UpdateSongTime);
        handler.postDelayed(UpdateSongTime, 100);
    }

    private void startPlayer() {
        music = MediaPlayer.create(getContext(), R.raw.song);
        music.start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        stopPlayer();
    }

    private void stopPlayer() {
        if (music != null) {
            music.release();
            music = null;
            handler.removeCallbacks(UpdateSongTime);
            handler = null;
        }
    }

    private void pausePlayer() {
        if (music != null) {
            music.pause();
        }
    }
}