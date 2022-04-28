package com.mjcasl.aslintermediaire.controller.lecon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mjcasl.aslintermediaire.R;
import com.mjcasl.aslintermediaire.controller.lecon.pps.LeconPpsActivity;
import com.mjcasl.aslintermediaire.controller.lecon.present.LeconPresentMenuActivity;
import com.mjcasl.aslintermediaire.controller.vocabulaire.VocActivity;

;


public class LeconMenuActivity extends AppCompatActivity {
    private Button mPPS;
    private Button mPresent;
   // private Button mImparfait;
   // private Button mPC;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leconmenu);

        mToolbar = findViewById(R.id.asl_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mPPS = findViewById(R.id.pps_btn);
        mPresent = findViewById(R.id.present_btn);
       // mImparfait = findViewById(R.id.imparfait_btn);
       // mPC = findViewById(R.id.pc_btn);

        mPPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PPSActivityIntent = new Intent(LeconMenuActivity.this, LeconPpsActivity.class);
                startActivity(PPSActivityIntent);
            }
        });

        mPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PresentActivityIntent = new Intent(LeconMenuActivity.this, LeconPresentMenuActivity.class);
                startActivity(PresentActivityIntent);
            }
        });

       /* mImparfait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ImparfaitActivityIntent = new Intent(LeconMenuActivity.this, LeconMenuActivity.class);
                startActivity(ImparfaitActivityIntent);
            }
        });*/

       /* mPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PCActivityIntent = new Intent(LeconMenuActivity.this, VocActivity.class);
                startActivity(PCActivityIntent);
            }
        });*/
    }
}
