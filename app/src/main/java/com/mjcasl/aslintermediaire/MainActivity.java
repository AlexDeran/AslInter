package com.mjcasl.aslintermediaire;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.mjcasl.aslintermediaire.controller.AlphabetActivity;
import com.mjcasl.aslintermediaire.controller.conjugaison.ConjugaisonActivity;
import com.mjcasl.aslintermediaire.controller.grammaire.GramActivity;
import com.mjcasl.aslintermediaire.controller.vocabulaire.VocActivity;
import com.mjcasl.aslintermediaire.controller.lecon.LeconMenuActivity;

public class MainActivity extends AppCompatActivity {
    private Button mConjugaison;
   // private Button mGram;
    private Button mLecon;
   private Button mAlphabet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConjugaison = findViewById(R.id.conjugaison_btn);
        //mGram = findViewById(R.id.gram_btn);
        mLecon = findViewById(R.id.leçon_btn);
        mAlphabet = findViewById(R.id.alphabet_btn);


        mConjugaison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ConjugaisonActivityIntent = new Intent(MainActivity.this, ConjugaisonActivity.class);
                startActivity(ConjugaisonActivityIntent);
            }
        });

        /*mGram.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent GramActivityIntent = new Intent(MainActivity.this, GramActivity.class);
                startActivity(GramActivityIntent);
           }
        });*/

        mLecon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LeconActivityIntent = new Intent(MainActivity.this, LeconMenuActivity.class);
                startActivity(LeconActivityIntent);
            }
        });

        mAlphabet.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               Intent AlphaActivityIntent = new Intent(MainActivity.this, AlphabetActivity.class);
                startActivity(AlphaActivityIntent);
            }
        });
    }
}
