package com.t3h.mp3music.fragment.song;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.t3h.mp3music.MainActivity;
import com.t3h.mp3music.R;
import com.t3h.mp3music.SongActivity;
import com.t3h.mp3music.base.BaseAdapter;
import com.t3h.mp3music.base.BaseFragment;
import com.t3h.mp3music.base.BaseItemListener;
import com.t3h.mp3music.base.BaseSongItemListener;
import com.t3h.mp3music.dao.AppDatabase;
import com.t3h.mp3music.databinding.FragmentSongBinding;
import com.t3h.mp3music.model.Song;
import com.t3h.mp3music.utils.SystemDataUtils;

import java.util.ArrayList;

public class SongFragment extends BaseFragment<FragmentSongBinding> implements BaseItemListener, MainActivity.callBackSearch {
    private BaseAdapter<Song> adapter;
    private SystemDataUtils dataUtils;
    private ArrayList<Song> arrNewSong;
    private static BaseSongItemListener mBaseSongItemListener;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_song;
    }

    @Override
    public String getTitle() {
        return "song";
    }

    public static void setBaseSongItemListener(BaseSongItemListener baseSongItemListener) {
        mBaseSongItemListener = baseSongItemListener;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataUtils = new SystemDataUtils(getContext());
        MainActivity.setCallBackSearch(this);
        adapter = new BaseAdapter<>(getContext(), R.layout.item_song);
        adapter.setData(dataUtils.getSongs());
        adapter.setListener(this);
        binding.lvSong.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        mBaseSongItemListener.onItemSongClick(position);
    }


    @Override
    public void onItemLongClick(int position) {
        mBaseSongItemListener.onItemLongSongClick(position);
    }

    private void filter(String text) {
        arrNewSong = new ArrayList<>();
        for (Song song : dataUtils.getSongs()) {
            if (song.getTitle().toUpperCase().contains(text.toUpperCase())) {
                arrNewSong.add(song);
            }
        }
        adapter.setData(arrNewSong);
    }

    @Override
    public void onQuerySearch(String text) {
        filter(text);
    }
}

