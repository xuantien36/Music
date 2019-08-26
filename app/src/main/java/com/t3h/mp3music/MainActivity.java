package com.t3h.mp3music;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;

import com.t3h.mp3music.base.BaseActivity;
import com.t3h.mp3music.base.BaseAdapter;
import com.t3h.mp3music.base.BaseSongItemListener;
import com.t3h.mp3music.dao.AppDatabase;
import com.t3h.mp3music.databinding.ActivityMainBinding;
import com.t3h.mp3music.fragment.FragmentFavorite;
import com.t3h.mp3music.fragment.album.AlbumDetailFragment;
import com.t3h.mp3music.fragment.album.AlbumFragment;
import com.t3h.mp3music.fragment.artist.ArtistFragment;
import com.t3h.mp3music.fragment.song.SongFragment;
import com.t3h.mp3music.model.Song;
import com.t3h.mp3music.utils.SystemDataUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<ActivityMainBinding> implements BaseSongItemListener {
    private final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private SongFragment fmsong = new SongFragment();
    private ArtistFragment fmartist = new ArtistFragment();
    private AlbumFragment fmalbum = new AlbumFragment();
    public static FragmentFavorite fmfavorite = new FragmentFavorite();
    private AlbumDetailFragment fmdtail = new AlbumDetailFragment();
    private Mp3PagerAdapter pagerAdapter;
    private int mPosition;
    private SystemDataUtils dataUtils;
    private MediaPlayer mediaPlayer;
    private BaseAdapter adapter;
    private static callBackSearch callBackSearch;


    public static FragmentFavorite getFmfavorite() {
        return fmfavorite;
    }



    @Override
    protected void initAct() {
        if (checkPermission() == false) {
            return;
        }
        List<Song> ar=new ArrayList<>();
        ar=AppDatabase.getInstance(this).getSongDao().getAll();

        ar.size();
        int a=3;

        getSupportActionBar().setElevation(0);
        pagerAdapter = new Mp3PagerAdapter(getSupportFragmentManager(), fmsong, fmalbum, fmartist, fmfavorite);
        binding.pager.setAdapter(pagerAdapter);
        adapter = new BaseAdapter<>(this, R.layout.item_song);
        dataUtils = new SystemDataUtils(this);

        binding.tab.setupWithViewPager(binding.pager);
        SongFragment.setBaseSongItemListener(this);
        binding.imFmplay.setOnClickListener(view -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                binding.imFmplay.setImageResource(R.drawable.ic_play);
            } else {
                mediaPlayer.start();
                binding.imFmplay.setImageResource(R.drawable.ic_pause);
            }
        });

        Log.d("favorite:::", "selectedFavorite: " + dataUtils.getSongs().toString());
        binding.imgFmpresious.setOnClickListener(view -> {
            if (mPosition <= 0) {
                Toast.makeText(MainActivity.this, "khong the back", Toast.LENGTH_SHORT).show();
            } else {
                mPosition--;
                binding.tvTitle.setText(dataUtils.getSongs().get(mPosition).getTitle());
                binding.tvArtist.setText(dataUtils.getSongs().get(mPosition).getArtist());
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                mediaPlayer = MediaPlayer.create(this, Uri.parse(dataUtils.getSongs().get(mPosition).getData()));
                mediaPlayer.start();
                binding.imFmplay.setImageResource(R.drawable.ic_pause);
                mediaPlayer.setOnCompletionListener(mediaPlayer -> binding.imFmplay.setImageResource(R.drawable.ic_play));
            }

        });
        binding.imFmnext.setOnClickListener(view -> {
            if (mPosition < dataUtils.getSongs().size() - 1) {
                mPosition++;
                binding.tvTitle.setText(dataUtils.getSongs().get(mPosition).getTitle());
                binding.tvArtist.setText(dataUtils.getSongs().get(mPosition).getArtist());
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                mediaPlayer = MediaPlayer.create(this, Uri.parse(dataUtils.getSongs().get(mPosition).getData()));
                mediaPlayer.start();
                binding.imFmplay.setImageResource(R.drawable.ic_pause);
                mediaPlayer.setOnCompletionListener(mediaPlayer -> binding.imFmplay.setImageResource(R.drawable.ic_play));
            } else {
                Toast.makeText(MainActivity.this, "Khong the next", Toast.LENGTH_SHORT).show();
            }

        });

        binding.layoutMain.setOnClickListener(view -> {
            if (mediaPlayer != null & mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                binding.imFmplay.setImageResource(R.drawable.ic_play);
//            } else {
//                mediaPlayer.start();
//                binding.imFmplay.setImageResource(R.drawable.ic_pause);
            }
            Intent intent = new Intent(MainActivity.this, SongActivity.class);
            intent.putExtra("name", dataUtils.getSongs().get(mPosition));
            intent.putExtra("position", mPosition);
            intent.putExtra("isCheck", true);
            startActivity(intent);
        });
//        for (Song song : dataUtils.getSongs()) {
//            AppDatabase.getInstance(this).getSongDao().insert(song);
//        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : PERMISSIONS) {
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(PERMISSIONS, 0);
                    return false;
                }
            }
        }
        return true;
    }

    public void addFragmentAlbumDetail(String albumID) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fmsong);
        transaction.hide(fmalbum);
        transaction.hide(fmartist);
        getSupportActionBar().hide();
        transaction.add(R.id.INT, fmdtail);
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.addToBackStack("addFragmentAlbumDetail");
        transaction.commit();
        fmdtail.setData(albumID, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermission()) {
            initAct();
        } else {
            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callBackSearch.onQuerySearch(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onItemSongClick(int position) {
        mPosition = position;
        binding.tvTitle.setText(dataUtils.getSongs().get(position).getTitle());
        binding.tvArtist.setText(dataUtils.getSongs().get(position).getArtist());
        binding.imFmplay.setImageResource(R.drawable.ic_pause);
        mediaPlayer = MediaPlayer.create(this, Uri.parse(dataUtils.getSongs().get(position).getData()));
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                binding.imFmplay.setImageResource(R.drawable.ic_play);

            }

        });
    }

    @Override
    public void onItemLongSongClick(int position) {
        Intent intent = new Intent(this, SongActivity.class);
        intent.putExtra("name", dataUtils.getSongs().get(position));
        intent.putExtra("position", position);
        intent.putExtra("isCheck", true);

        mediaPlayer = MediaPlayer.create(this, Uri.parse(dataUtils.getSongs().get(position).getData()));
        mediaPlayer.start();
        startActivity(intent);
    }

    public interface callBackSearch {
        void onQuerySearch(String text);
    }

    public static void setCallBackSearch(callBackSearch mCallBack) {
        callBackSearch = mCallBack;
    }
}




