package com.mjcasl.aslintermediaire.controller.conjugaison.pps;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mjcasl.aslintermediaire.R;

public class PpsmenuActivity extends AppCompatActivity {
    private Button mExPps1;
    private Button mExPps2;
    private Button mExPps3;
    private Button mExPps4;
    private Button mExPps5;
    private Button mQuizFinal;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppsmenu);

        mToolbar = findViewById(R.id.asl_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mExPps1 = findViewById(R.id.expps1_btn);
        mExPps2 = findViewById(R.id.expps2_btn);
        mExPps3 = findViewById(R.id.expps3_btn);
        mExPps4 = findViewById(R.id.expps4_btn);
        mExPps5 = findViewById(R.id.expps5_btn);
        mQuizFinal = findViewById(R.id.quizfinalpps_btn);

        mExPps1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PpsActivity1Intent = new Intent(PpsmenuActivity.this, Ex1ppsActivity.class);
                startActivity(PpsActivity1Intent);
            }
        });

        mExPps2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PpsActivity2Intent = new Intent(PpsmenuActivity.this, Ex2ppsActivity.class);
                startActivity(PpsActivity2Intent);
            }
        });

        mExPps3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PpsActivity3Intent = new Intent(PpsmenuActivity.this, Ex3ppsActivity.class);
                startActivity(PpsActivity3Intent);
            }
        });
    }
}
