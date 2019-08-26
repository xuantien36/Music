package com.t3h.mp3music.fragment.album;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.t3h.mp3music.MainActivity;
import com.t3h.mp3music.R;
import com.t3h.mp3music.base.BaseAdapter;
import com.t3h.mp3music.base.BaseFragment;
import com.t3h.mp3music.base.BaseItemListener;
import com.t3h.mp3music.databinding.FragmentAlbumBinding;
import com.t3h.mp3music.model.Album;
import com.t3h.mp3music.utils.SystemDataUtils;

public class AlbumFragment extends BaseFragment<FragmentAlbumBinding> implements BaseItemListener {
    private BaseAdapter<Album> adapter;
    private SystemDataUtils dataUtils;
    private static final String TAG = AlbumFragment.class.getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_album;
    }

    @Override
    public String getTitle() {
        return "album";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseAdapter<>(getContext(), R.layout.item_album);
        dataUtils = new SystemDataUtils(getContext());
        adapter.setData(dataUtils.getAlbums());
        adapter.setListener(this);
        binding.lvAlbum.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        String albumID = dataUtils.getAlbums().get(position).getAlbumID();
        MainActivity main = (MainActivity) getActivity();
        main.addFragmentAlbumDetail(albumID);

    }

    @Override
    public void onItemLongClick(int position) {

    }
}
