package com.mjcasl.aslintermediaire.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mjcasl.aslintermediaire.MainActivity;
import com.mjcasl.aslintermediaire.R;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView ivTitle, ivLogo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivLogo = findViewById(R.id.ivSplashLogo);

        Animation animRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_rotate);
        animRotate.setRepeatMode(ValueAnimator.REVERSE);
        ivLogo.startAnimation(animRotate);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        },4000);

    }
}