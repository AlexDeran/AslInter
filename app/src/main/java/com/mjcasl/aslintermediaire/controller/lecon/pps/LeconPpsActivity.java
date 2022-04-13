package com.mjcasl.aslintermediaire.controller.lecon.pps;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.mjcasl.aslintermediaire.R;

public class LeconPpsActivity extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leconpps);
        mToolbar = findViewById(R.id.asl_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
