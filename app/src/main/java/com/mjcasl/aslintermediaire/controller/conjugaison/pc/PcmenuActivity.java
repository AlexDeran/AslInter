package com.mjcasl.aslintermediaire.controller.conjugaison.pc;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mjcasl.aslintermediaire.R;

public class PcmenuActivity extends AppCompatActivity {
    private Button mExpc1;
    private Button mExpc2;
    private Button mExpc3;
    private Button mExpc4;
    private Button mExpc5;
    private Button mQuizFinal;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pcmenu);

        mToolbar = findViewById(R.id.asl_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mExpc1 = findViewById(R.id.expc1_btn);
        mExpc2 = findViewById(R.id.expc2_btn);
        mExpc3 = findViewById(R.id.expc3_btn);
        mQuizFinal = findViewById(R.id.quizfinalpc_btn);

        mExpc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pcActivity1Intent = new Intent(PcmenuActivity.this, Ex1pcActivity.class);
                startActivity(pcActivity1Intent);
            }
        });

        mExpc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pcActivity2Intent = new Intent(PcmenuActivity.this, Ex2pcActivity.class);
                startActivity(pcActivity2Intent);
            }
        });

        mExpc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pcActivity3Intent = new Intent(PcmenuActivity.this, Ex3pcActivity.class);
                startActivity(pcActivity3Intent);
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
