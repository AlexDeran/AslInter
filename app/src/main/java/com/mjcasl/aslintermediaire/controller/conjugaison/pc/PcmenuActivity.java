package com.mjcasl.aslintermediaire.controller.conjugaison.pc;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mjcasl.aslintermediaire.R;

public class PcmenuActivity extends AppCompatActivity {
    private Button mExpcIlElles;
    private Button mExpcJeTu;
    private Button mExpcNousVous;
    private Button mExpcEtreAvoir;
    private Button mQuizFinal;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcmenu);

        mToolbar = findViewById(R.id.asl_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mExpcIlElles = findViewById(R.id.exfutur1_btn);
        mExpcJeTu = findViewById(R.id.exfutur2_btn);
        mExpcNousVous = findViewById(R.id.exfutur3_btn);
        mExpcEtreAvoir = findViewById(R.id.exfutur4_btn);
        mQuizFinal = findViewById(R.id.quizfinalfutur_btn);

        mExpcIlElles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pcActivityIlEllesIntent = new Intent(PcmenuActivity.this, ExIlEllesPCActivity.class);
                startActivity(pcActivityIlEllesIntent);
            }
        });

        mExpcJeTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pcActivityJeTuIntent = new Intent(PcmenuActivity.this, ExJeTuPCActivity.class);
                startActivity(pcActivityJeTuIntent);
            }
        });

        mExpcNousVous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pcActivityNousVousIntent = new Intent(PcmenuActivity.this, ExNousVousPCActivity.class);
                startActivity(pcActivityNousVousIntent);
            }
        });

        mExpcEtreAvoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pcActivityEtreAvoirIntent = new Intent(PcmenuActivity.this, ExEtreAvoirPCActivity.class);
                startActivity(pcActivityEtreAvoirIntent);
            }
        });

        mQuizFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QFpcActivityIntent = new Intent(PcmenuActivity.this, QFPCActivity.class);
                startActivity(QFpcActivityIntent);
            }
        });
    }
}
