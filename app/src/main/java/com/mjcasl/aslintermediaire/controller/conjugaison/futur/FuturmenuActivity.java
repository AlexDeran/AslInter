package com.mjcasl.aslintermediaire.controller.conjugaison.futur;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mjcasl.aslintermediaire.R;

public class FuturmenuActivity extends AppCompatActivity {
    private Button mEx1Futur;
    private Button mEx2Futur;
    private Button mEx3Futur;
    private Button mEx4Futur;
    private Button mExFuturEtreAvoir;
    private Button mQuizFinal;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.futurmenu);

        mToolbar = findViewById(R.id.asl_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mEx1Futur = findViewById(R.id.exfutur1_btn);
        mEx2Futur = findViewById(R.id.exfutur2_btn);
        mEx3Futur = findViewById(R.id.exfutur3_btn);
        mEx4Futur = findViewById(R.id.exfutur4_btn);
        mExFuturEtreAvoir = findViewById(R.id.exfutur5_btn);
        mQuizFinal = findViewById(R.id.quizfinalfutur_btn);

        mEx1Futur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Futur1ActivityIntent = new Intent(FuturmenuActivity.this, Ex1FuturActivity.class);
                startActivity(Futur1ActivityIntent);
            }
        });

       mEx2Futur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Futur2ActivityIntent = new Intent(FuturmenuActivity.this, Ex2FuturActivity.class);
                startActivity(Futur2ActivityIntent);
            }
        });

         mEx3Futur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Futur3ActivityIntent = new Intent(FuturmenuActivity.this, Ex3FuturActivity.class);
                startActivity(Futur3ActivityIntent);
            }
        });

        mEx4Futur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Futur4ActivityIntent = new Intent(FuturmenuActivity.this, Ex4FuturActivity.class);
                startActivity(Futur4ActivityIntent);
            }
        });

       /* mExFuturEtreAvoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FuturActivityEtreAvoirIntent = new Intent(FuturmenuActivity.this, ExFuturEtreAvoirActivity.class);
                startActivity(FuturActivityEtreAvoirIntent);
            }
        });

        mQuizFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QFFuturActivityIntent = new Intent(FuturmenuActivity.this, QFFuturActivity.class);
                startActivity(QFFuturActivityIntent);
            }
        }); */
    }
}
