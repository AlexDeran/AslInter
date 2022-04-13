package com.mjcasl.aslintermediaire.controller.conjugaison;;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mjcasl.aslintermediaire.R;
import com.mjcasl.aslintermediaire.controller.conjugaison.pps.PpsmenuActivity;
import com.mjcasl.aslintermediaire.controller.conjugaison.present.PremenuActivity;
import com.mjcasl.aslintermediaire.controller.grammaire.GramActivity;
import com.mjcasl.aslintermediaire.controller.lecon.LeconMenuActivity;
import com.mjcasl.aslintermediaire.controller.vocabulaire.VocActivity;


public class ConjugaisonActivity  extends AppCompatActivity {
    private Button mPps;
    private Button mPresent;
    private Button mLecon;
    private Button mVoc;
    private Button mTBD;
    private Button mTBD2;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conjugaisonmenu);

        mToolbar = findViewById(R.id.asl_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mPps = findViewById(R.id.pps_btn);
        mPresent = findViewById(R.id.present_btn);
        mLecon = findViewById(R.id.leçon_btn);
        mVoc = findViewById(R.id.voc_btn);
        mTBD = findViewById(R.id.tbd_btn);
        mTBD2 = findViewById(R.id.tbd2_btn);

        mPps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PpsActivityIntent = new Intent(ConjugaisonActivity.this, PpsmenuActivity.class);
                startActivity(PpsActivityIntent);
            }
        });

        mPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GramActivityIntent = new Intent(ConjugaisonActivity.this, PremenuActivity.class);
                startActivity(GramActivityIntent);
            }
        });

        mLecon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LeconActivityIntent = new Intent(ConjugaisonActivity.this, LeconMenuActivity.class);
                startActivity(LeconActivityIntent);
            }
        });

        mVoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VocActivityIntent = new Intent(ConjugaisonActivity.this, VocActivity.class);
                startActivity(VocActivityIntent);
            }
        });

        mTBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TBDActivityIntent = new Intent(ConjugaisonActivity.this, VocActivity.class);
                startActivity(TBDActivityIntent);
            }
        });

        mTBD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TBD2ActivityIntent = new Intent(ConjugaisonActivity.this, VocActivity.class);
                startActivity(TBD2ActivityIntent);
            }
        });

    }
}
