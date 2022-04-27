package com.mjcasl.aslintermediaire.controller.conjugaison.imparfait;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mjcasl.aslintermediaire.R;

public class ImparfaitMenuActivity extends AppCompatActivity {
    private Button mExImparfait1;
    private Button mExImparfait2;
    private Button mExImparfait3;
    private Button mExImparfait4;
    private Button mExEtreAvoir;
    private Button mQuizFinal;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imparfaitmenu);

        mToolbar = findViewById(R.id.asl_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mExImparfait1 = findViewById(R.id.exImparfait1_btn);
        mExImparfait2 = findViewById(R.id.exImparfait2_btn);
        mExImparfait3 = findViewById(R.id.exImparfait3_btn);
        mExImparfait4 = findViewById(R.id.exImparfait4_btn);
        mExEtreAvoir = findViewById(R.id.etreavoirImparfait_btn);
        mQuizFinal = findViewById(R.id.quizfinalImparfait_btn);

        mExImparfait1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ImparfaitActivity1Intent = new Intent(ImparfaitMenuActivity.this, Ex1ImparfaitActivity.class);
                startActivity(ImparfaitActivity1Intent);
            }
        });

        mExImparfait2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ImparfaitActivity2Intent = new Intent(ImparfaitMenuActivity.this, Ex2ImparfaitActivity.class);
                startActivity(ImparfaitActivity2Intent);
            }
        });

        mExImparfait3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ImparfaitActivity3Intent = new Intent(ImparfaitMenuActivity.this, Ex3ImparfaitActivity.class);
                startActivity(ImparfaitActivity3Intent);
            }
        });

        mExImparfait4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ImparfaitActivity4Intent = new Intent(ImparfaitMenuActivity.this, Ex4ImparfaitActivity.class);
                startActivity(ImparfaitActivity4Intent);
            }
        });

        mExEtreAvoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ImparfaitEtreAvoirActivityIntent = new Intent(ImparfaitMenuActivity.this, ExEtreAvoirImparfaitActivity.class);
                startActivity(ImparfaitEtreAvoirActivityIntent);
            }
        });

        mQuizFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QFImparfaitActivityIntent = new Intent(ImparfaitMenuActivity.this, QFImparfaitActivity.class);
                startActivity(QFImparfaitActivityIntent);
            }
        });
    }
}
