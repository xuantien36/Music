package com.t3h.mp3music;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.t3h.mp3music.base.BaseActivity;
import com.t3h.mp3music.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    private Handler handler;

    @Override
    protected void initAct() {
        binding.prGPlash.setVisibility(View.VISIBLE);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               binding.prGPlash.setVisibility(View.INVISIBLE);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));

            }
        }, 2000);

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

}
