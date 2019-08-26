package com.t3h.mp3music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.t3h.mp3music.base.BaseActivity;
import com.t3h.mp3music.base.BaseAdapter;
import com.t3h.mp3music.base.BaseItemListener;
import com.t3h.mp3music.base.BaseSongItemListener;
import com.t3h.mp3music.dao.AppDatabase;
import com.t3h.mp3music.databinding.ActivitySongBinding;
import com.t3h.mp3music.model.Song;
import com.t3h.mp3music.utils.SystemDataUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SongActivity extends BaseActivity<ActivitySongBinding> implements BaseSongItemListener {
    private SystemDataUtils dataUtils;
    private MediaPlayer mPlayer;
    private int mPosition;
    private int mSongPosition;
    private Song song;
    boolean isCheck;
    private int pos;
//    private ArrayList<Song> data;



    @Override
    protected void initAct() {
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataUtils = new SystemDataUtils(this);
        Intent intent = getIntent();
        song = (Song) intent.getSerializableExtra("name");
        mSongPosition = getIntent().getIntExtra("position", 0);
        isCheck = getIntent().getBooleanExtra("isCheck", false);
        binding.tvTitle.setText(song.getTitle());
        binding.tvArtist.setText(song.getArtist());
        binding.imPlay.setImageResource(R.drawable.ic_pause);
        mPlayer = MediaPlayer.create(SongActivity.this, Uri.parse(song.getData()));

        mPlayer.start();
        mPlayer.setOnCompletionListener(mediaPlayer -> {
            binding.imPlay.setImageResource(R.drawable.ic_play);
        });
        setTimeTotal();
        setTimeSong();
        binding.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        binding.skbarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayer.seekTo(binding.skbarSong.getProgress());

            }
        });
        binding.imPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    binding.imPlay.setImageResource(R.drawable.ic_play);
                } else {
                    mPlayer.start();
                    binding.imPlay.setImageResource(R.drawable.ic_pause);
                }
            }
        });
        binding.imNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCheck = false;
                if (mPosition < dataUtils.getSongs().size() - 1) {
                    mPosition++;
                    binding.tvTitle.setText(dataUtils.getSongs().get(mPosition).getTitle());
                    binding.tvArtist.setText(dataUtils.getSongs().get(mPosition).getArtist());

                    if (mPlayer.isPlaying()) {
                        mPlayer.stop();
                    }
                    mPlayer = MediaPlayer.create(SongActivity.this, Uri.parse(dataUtils.getSongs().get(mPosition).getData()));
                    mPlayer.start();
                    binding.imPlay.setImageResource(R.drawable.ic_pause);
                    setTimeTotal();
                    setTimeSong();
                    mPlayer.setOnCompletionListener(mediaPlayer -> binding.imPlay.setImageResource(R.drawable.ic_play));
                } else {
                    Toast.makeText(SongActivity.this, "Khong the next", Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.imgPresious.setOnClickListener(view -> {
            isCheck = false;
            if (mPosition <= 0) {
                Toast.makeText(SongActivity.this, "khong the back", Toast.LENGTH_SHORT).show();
            } else {
                mPosition--;
                binding.tvTitle.setText(dataUtils.getSongs().get(mPosition).getTitle());
                binding.tvArtist.setText(dataUtils.getSongs().get(mPosition).getArtist());
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                }
                mPlayer = MediaPlayer.create(this, Uri.parse(dataUtils.getSongs().get(mPosition).getData()));
                mPlayer.start();
                binding.imPlay.setImageResource(R.drawable.ic_pause);
                setTimeTotal();
                setTimeSong();
                mPlayer.setOnCompletionListener(mediaPlayer -> binding.imPlay.setImageResource(R.drawable.ic_play));
            }
        });
        binding.imgRandom.setOnClickListener(view -> {
            int pos = new Random().nextInt(dataUtils.getSongs().size());
            binding.tvTitle.setText(dataUtils.getSongs().get(pos).getTitle());
            binding.tvArtist.setText(dataUtils.getSongs().get(pos).getArtist());
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
            mPlayer = MediaPlayer.create(SongActivity.this, Uri.parse(dataUtils.getSongs().get(pos).getData()));
            mPlayer.start();
            binding.imPlay.setImageResource(R.drawable.ic_pause);
            binding.imgRandom.setImageResource(R.drawable.rotate_3d_variant);
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
            binding.tvTimeTotal.setText(dateFormat.format(dataUtils.getSongs().get(pos).getDuration()));
            binding.skbarSong.setMax(mPlayer.getDuration());
            setTimeSong();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    binding.imPlay.setImageResource(R.drawable.ic_play);
                    binding.imgRandom.setImageResource(R.drawable.shuffle_variant);

                }
            });

        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.stop();
                finish();

            }
        });
        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApp(getApplicationContext());
            }
        });
        binding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSong(mSongPosition);

            }
        });
        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);

            }
        });


    }
    public void showMenu(View view){
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_song,popupMenu.getMenu());
        popupMenu.show();
    }


    public void addSong(int mSongPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SongActivity.this);
        builder.setTitle("Thông báo");
        builder.setIcon(R.drawable.ic_favorite);
        builder.setMessage("Bạn có muốn thêm bài hát vào yêu thích không? ");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                long id = dataUtils.getSongs().get(mSongPosition).getId();
                AppDatabase.getInstance(SongActivity.this).getSongDao().insert(song);

                //MainActivity.getFmfavorite().initData();
                Toast.makeText(SongActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();



            }
        });

        builder.setNegativeButton("Không", (dialogInterface, i) -> {

        });

        builder.show();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_song;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_song, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mPlayer.stop();
                finish();
                break;

            case R.id.share:
//               shareApp(getApplicationContext());
                break;
            case R.id.favorite:

//                selectedFavorite(item);
        }
        return super.onOptionsItemSelected(item);

    }
//
//    Song newSong;
//
//    private void selectedFavorite(MenuItem item) {
//        if (song.isFavorite()) {
//            isFavorite = false;
//        } else {
//            isFavorite = true;
//        }
//        if (isFavorite) {
//            item.setIcon(R.drawable.ic_favorite);
//        } else {
//            item.setIcon(R.drawable.ic_uncheck_favorite);
//        }
//        Song song1 = AppDatabase.getInstance(SongActivity.this).getSongDao().getSongById(song.getId());
//        newSong = new Song();
//        newSong.setId(song1.getId());
//        newSong.setTitle(song1.getTitle());
//        newSong.setSize(song1.getSize());
//        newSong.setFavorite(isFavorite);
//        newSong.setDuration(song1.getDuration());
//        newSong.setData(song1.getData());
//        newSong.setArtist(song1.getArtist());
//        newSong.setAlbum(song1.getAlbum());
//        AppDatabase.getInstance(SongActivity.this).getSongDao().updateSong(newSong);
//        //getInstance().initData();
//    }

    public void shareApp(Context context) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, dataUtils.getSongs().get(mSongPosition).getData());
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    @Override
    public void onItemSongClick(int position) {
        mPosition = position;
    }

    @Override
    public void onItemLongSongClick(int position) {

    }

    public void setTimeTotal() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        if (isCheck) {
            binding.tvTimeTotal.setText(dateFormat.format(dataUtils.getSongs().get(mSongPosition).getDuration()));
        } else {
            binding.tvTimeTotal.setText(dateFormat.format(dataUtils.getSongs().get(mPosition).getDuration()));
        }
        binding.skbarSong.setMax(mPlayer.getDuration());
    }

    public void setTimeSong() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                binding.tvTimesong.setText(dateFormat.format(mPlayer.getCurrentPosition()));
                binding.skbarSong.setProgress(mPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);

            }
        }, 100);


    }
}
