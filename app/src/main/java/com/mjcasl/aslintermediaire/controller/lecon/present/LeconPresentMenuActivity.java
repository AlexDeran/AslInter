package com.mjcasl.aslintermediaire.controller.lecon.present;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mjcasl.aslintermediaire.R;
import com.mjcasl.aslintermediaire.controller.vocabulaire.VocActivity;

public class LeconPresentMenuActivity extends AppCompatActivity {

    private Button m1erGpe;
    private Button m2ndGpe;
    private Button m3emeGpe;
    private Button mEtreEtAvoir;
    private Button mAller;
    private Button mDevoirPvVouloir;
    private Button mVenir;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leconpresentmenu);

        mToolbar = findViewById(R.id.asl_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        m1erGpe = findViewById(R.id.premiergpe_btn);
        m2ndGpe = findViewById(R.id.deuxiemegpe_btn);
        m3emeGpe = findViewById(R.id.trosiemegpe_btn);
        mEtreEtAvoir = findViewById(R.id.exEtreAvoir_btn);
        mAller = findViewById(R.id.exAller_btn);
        mDevoirPvVouloir = findViewById(R.id.exDevoirVouloirPouvoir_btn);
        mVenir = findViewById(R.id.exVenir_btn);

        m1erGpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PremierGpeActivityIntent = new Intent(LeconPresentMenuActivity.this, PremierGpeActivity.class);
                startActivity(PremierGpeActivityIntent);
            }
        });

        m2ndGpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DeuxiemeGpeActivityIntent = new Intent(LeconPresentMenuActivity.this, SecondGpeActivity.class);
                startActivity(DeuxiemeGpeActivityIntent);
            }
        });

        m3emeGpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TroisiemeGpeActivityIntent = new Intent(LeconPresentMenuActivity.this, TroisiemeGpeActivity.class);
                startActivity(TroisiemeGpeActivityIntent);
            }
        });

        mEtreEtAvoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EtreAvoirActivityIntent = new Intent(LeconPresentMenuActivity.this, LeconEtreAvoirActivity.class);
                startActivity(EtreAvoirActivityIntent);
            }
        });

        mAller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AllerActivityIntent = new Intent(LeconPresentMenuActivity.this, LeconAllerActivity.class);
                startActivity(AllerActivityIntent);
            }
        });

        mDevoirPvVouloir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DPVActivityIntent = new Intent(LeconPresentMenuActivity.this, LeconDevoirPVActivity.class);
                startActivity(DPVActivityIntent);
            }
        });

        mVenir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VenirActivityIntent = new Intent(LeconPresentMenuActivity.this, LeconVenirActivity.class);
                startActivity(VenirActivityIntent);
            }
        });

    }
}
