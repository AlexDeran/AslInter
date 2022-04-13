package com.mjcasl.aslintermediaire.controller.lecon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mjcasl.aslintermediaire.R;
import com.mjcasl.aslintermediaire.controller.grammaire.GramActivity;
import com.mjcasl.aslintermediaire.controller.lecon.pps.LeconPpsActivity;
import com.mjcasl.aslintermediaire.controller.vocabulaire.VocActivity;

;


public class LeconMenuActivity extends AppCompatActivity {
    private Button mPPS;
    private Button mGram;
    private Button mLecon;
    private Button mVoc;
    private Button mTBD;
    private Button mTBD2;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leconmenu);

        mToolbar = findViewById(R.id.asl_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mPPS = findViewById(R.id.pps_btn);
        mGram = findViewById(R.id.gram_btn);
        mLecon = findViewById(R.id.leçon_btn);
        mVoc = findViewById(R.id.voc_btn);
        mTBD = findViewById(R.id.tbd_btn);
        mTBD2 = findViewById(R.id.tbd2_btn);

        mPPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PPSActivityIntent = new Intent(LeconMenuActivity.this, LeconPpsActivity.class);
                startActivity(PPSActivityIntent);
            }
        });

        mGram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GramActivityIntent = new Intent(LeconMenuActivity.this, GramActivity.class);
                startActivity(GramActivityIntent);
            }
        });

        mLecon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LeconActivityIntent = new Intent(LeconMenuActivity.this, LeconMenuActivity.class);
                startActivity(LeconActivityIntent);
            }
        });

        mVoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VocActivityIntent = new Intent(LeconMenuActivity.this, VocActivity.class);
                startActivity(VocActivityIntent);
            }
        });

        mTBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TBDActivityIntent = new Intent(LeconMenuActivity.this, VocActivity.class);
                startActivity(TBDActivityIntent);
            }
        });

        mTBD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TBD2ActivityIntent = new Intent(LeconMenuActivity.this, VocActivity.class);
                startActivity(TBD2ActivityIntent);
            }
        });

    }
}
