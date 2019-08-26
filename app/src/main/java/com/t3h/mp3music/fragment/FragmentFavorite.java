package com.t3h.mp3music.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.mp3music.R;
import com.t3h.mp3music.SongActivity;
import com.t3h.mp3music.base.BaseAdapter;
import com.t3h.mp3music.base.BaseFragment;
import com.t3h.mp3music.base.NewsAdapter;
import com.t3h.mp3music.dao.AppDatabase;
import com.t3h.mp3music.databinding.FragmentFavoriteBinding;
import com.t3h.mp3music.model.Song;
import com.t3h.mp3music.utils.SystemDataUtils;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavorite extends BaseFragment<FragmentFavoriteBinding> {
    private BaseAdapter adapter;
    private List<Song> arr;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        adapter = new BaseAdapter(getContext(), R.layout.item_song);
        binding.lvFavorite.setAdapter(adapter);
    }

    public void initData() {
        arr = AppDatabase.getInstance(getContext()).getSongDao().getAll();
        Log.d("FragmentFavorite::", "initData: " + arr.toString());
        if (arr != null) {
            adapter.setData(arr);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void onResume() {
        super.onResume();
        //initData();
    }

    @Override
    public String getTitle() {
        return "favorite";
    }
}
