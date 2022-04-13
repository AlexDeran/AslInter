package com.mjcasl.aslintermediaire.controller.conjugaison.present;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mjcasl.aslintermediaire.R;

public class PremenuActivity extends AppCompatActivity {
    private Button mExPre1;
    private Button mExPre2;
    private Button mExPre3;
    private Button mExPre4;
    private Button mExPre5;
    private Button mQuizFinal;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premenu);

        mToolbar = findViewById(R.id.asl_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mExPre1 = findViewById(R.id.exPre1_btn);
        mExPre2 = findViewById(R.id.exPre2_btn);
        mExPre3 = findViewById(R.id.exPre3_btn);
        mExPre4 = findViewById(R.id.exPre4_btn);
        mExPre5 = findViewById(R.id.exPre5_btn);
        mQuizFinal = findViewById(R.id.quizfinalPre_btn);

        mExPre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PreActivity1Intent = new Intent(PremenuActivity.this, Ex1preActivity.class);
                startActivity(PreActivity1Intent);
            }
        });

        mExPre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PreActivity2Intent = new Intent(PremenuActivity.this, Ex2preActivity.class);
                startActivity(PreActivity2Intent);
            }
        });

        mExPre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PreActivity3Intent = new Intent(PremenuActivity.this, Ex3preActivity.class);
                startActivity(PreActivity3Intent);
            }
        });

        mExPre4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PreActivity4Intent = new Intent(PremenuActivity.this, Ex4preActivity.class);
                startActivity(PreActivity4Intent);
            }
        });

        mExPre5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PreActivity5Intent = new Intent(PremenuActivity.this, Ex5preActivity.class);
                startActivity(PreActivity5Intent);
            }
        });

        mQuizFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QFPreActivityIntent = new Intent(PremenuActivity.this, QFPREActivity.class);
                startActivity(QFPreActivityIntent);
            }
        });
    }
}
