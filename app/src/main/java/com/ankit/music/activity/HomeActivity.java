package com.ankit.music.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ankit.music.R;
import com.ankit.music.adapter.CustomPagerAdapter;
import com.ankit.music.communication.CommunicationHelper;
import com.ankit.music.fragment.HomeFragment;
import com.ankit.music.fragment.MusicFragment;
import com.ankit.music.interfaces.OpenMusicFragment;
import com.ankit.music.transformer.MainPagerTransformer;

public class HomeActivity extends AppCompatActivity implements OpenMusicFragment {

    private ViewPager viewPager;
    private CustomPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initViewpager();

    }

    private void initViewpager() {
        adapter = new CustomPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new HomeFragment());
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false, new MainPagerTransformer());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0)
                    closeFragment();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewpager);
    }


    @Override
    public void openMusicFragment() {
        adapter.addFragment(new MusicFragment());
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0)
            super.onBackPressed();
        else {
            viewPager.setCurrentItem(0);
            closeFragment();
        }
    }

    private void closeFragment() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.removeFragment(1);
                adapter.notifyDataSetChanged();
                CommunicationHelper.setIsSongPlaying(false);
            }
        }, 400);
    }
}