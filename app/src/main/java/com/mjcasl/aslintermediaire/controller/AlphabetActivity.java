package com.mjcasl.aslintermediaire.controller;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mjcasl.aslintermediaire.R;
import com.mjcasl.aslintermediaire.model.SoundObject;
import com.mjcasl.aslintermediaire.model.SoundboxRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlphabetActivity extends AppCompatActivity {

    Toolbar mToolbar;

    ArrayList<SoundObject> soundList = new ArrayList<>();

    RecyclerView mSoundView;

    SoundboxRecyclerAdapter mSoundAdapter = new SoundboxRecyclerAdapter(soundList);

    RecyclerView.LayoutManager mSoundLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alphabet);
        mToolbar = findViewById(R.id.asl_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        List<String> nameList = Arrays.asList(getResources().getStringArray(R.array.alphabet));

        SoundObject[] soundItems = {
                new SoundObject(nameList.get(0), R.raw.a),
                new SoundObject(nameList.get(1), R.raw.b),
                new SoundObject(nameList.get(2), R.raw.c),
                new SoundObject(nameList.get(3), R.raw.d),
                new SoundObject(nameList.get(4), R.raw.e),
                new SoundObject(nameList.get(5), R.raw.f),
                new SoundObject(nameList.get(6), R.raw.g),
                new SoundObject(nameList.get(7), R.raw.h),
                new SoundObject(nameList.get(8), R.raw.i),
                new SoundObject(nameList.get(9), R.raw.j),
                new SoundObject(nameList.get(10), R.raw.k),
                new SoundObject(nameList.get(11), R.raw.l),
                new SoundObject(nameList.get(12), R.raw.m),
                new SoundObject(nameList.get(13), R.raw.n),
                new SoundObject(nameList.get(14), R.raw.o),
                new SoundObject(nameList.get(15), R.raw.p),
                new SoundObject(nameList.get(16), R.raw.q),
                new SoundObject(nameList.get(17), R.raw.r),
                new SoundObject(nameList.get(18), R.raw.s),
                new SoundObject(nameList.get(19), R.raw.t),
                new SoundObject(nameList.get(20), R.raw.u),
                new SoundObject(nameList.get(21), R.raw.v),
                new SoundObject(nameList.get(22), R.raw.w),
                new SoundObject(nameList.get(23), R.raw.x),
                new SoundObject(nameList.get(24), R.raw.y),
                new SoundObject(nameList.get(25), R.raw.z)
        };

        soundList.addAll(Arrays.asList(soundItems));

        mSoundView = findViewById(R.id.soundbox_recycler);

        mSoundLayoutManager = new GridLayoutManager(this, 4);

        mSoundView.setLayoutManager(mSoundLayoutManager);

        mSoundView.setAdapter(mSoundAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventHandlerClass.stopSound();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventHandlerClass.releaseSound();
    }


}