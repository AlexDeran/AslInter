package com.mjcasl.aslintermediaire.controller.conjugaison;;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mjcasl.aslintermediaire.R;
import com.mjcasl.aslintermediaire.controller.conjugaison.imparfait.ImparfaitMenuActivity;
import com.mjcasl.aslintermediaire.controller.conjugaison.pc.PcmenuActivity;
import com.mjcasl.aslintermediaire.controller.conjugaison.pps.PpsmenuActivity;
import com.mjcasl.aslintermediaire.controller.conjugaison.present.PremenuActivity;
import com.mjcasl.aslintermediaire.controller.lecon.LeconMenuActivity;
import com.mjcasl.aslintermediaire.controller.vocabulaire.VocActivity;


public class ConjugaisonActivity  extends AppCompatActivity {
    private Button mPps;
    private Button mPresent;
    private Button mImparfait;
    private Button mPC;
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
        mImparfait = findViewById(R.id.imparfait_btn);
        mPC = findViewById(R.id.pc_btn);

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

        mImparfait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ImparfaitActivityIntent = new Intent(ConjugaisonActivity.this, ImparfaitMenuActivity.class);
                startActivity(ImparfaitActivityIntent);
            }
        });

        mPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PCActivityIntent = new Intent(ConjugaisonActivity.this, PcmenuActivity.class);
                startActivity(PCActivityIntent);
            }
        });
    }
}
