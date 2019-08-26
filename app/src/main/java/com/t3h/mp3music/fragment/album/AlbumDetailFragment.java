package com.t3h.mp3music.fragment.album;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.t3h.mp3music.MainActivity;
import com.t3h.mp3music.R;
import com.t3h.mp3music.base.BaseAdapter;
import com.t3h.mp3music.base.BaseFragment;
import com.t3h.mp3music.databinding.FragmentAlbumDetailBinding;
import com.t3h.mp3music.model.Song;
import com.t3h.mp3music.utils.SystemDataUtils;

import java.util.ArrayList;

public class AlbumDetailFragment extends BaseFragment<FragmentAlbumDetailBinding> {
    private SystemDataUtils dataUtils;
    private BaseAdapter<Song> adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        binding.recyclerAlbumDetail.setAdapter(adapter);
    }

    public void setData(String albumID, Context context) {
        dataUtils = new SystemDataUtils(context);
        ArrayList<Song> arr = dataUtils.getSongByAlbum(albumID);
        adapter = new BaseAdapter<>(context, R.layout.item_song);
        adapter.setData(arr);
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity main = (MainActivity) getActivity();
        main.getSupportActionBar().show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_album_detail;
    }

    @Override
    public String getTitle() {
        return "album detail";
    }
}
