package com.mjcasl.aslintermediaire.controller.conjugaison.present;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mjcasl.aslintermediaire.R;
import com.mjcasl.aslintermediaire.model.ImgBank;
import com.mjcasl.aslintermediaire.model.ImgQuestion;

import java.util.Arrays;

public class EtreAvoirActivity extends AppCompatActivity implements View.OnClickListener {

    //public static final long COUNTDOWN_IN_MILLIS = 11000;

    private TextView mpreQuestion;
    private Button mpreAnswer1;
    private Button mpreAnswer2;
    private Button mpreAnswer3;
    private Button mpreAnswer4;
    private ImageView mImage;

    private TextView mScoreDisplay;
    private TextView mNbrofQuestion;
    private ProgressBar mProgressBar;

    private ImgBank mImgBank;
    private ImgQuestion mImgQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    private boolean mEnableTouchEvents;
    private int mQuestionTotal;
    private int mQuestionCounter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exetreavoir);


        mImgBank = this.generateQuestions();
        mScore = 0;
        mNumberOfQuestions = 10;

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 10;
        }

        mEnableTouchEvents = true;

        mpreQuestion = findViewById(R.id.pre_question_txt);
        mpreAnswer1 = findViewById(R.id.pre_answer1_btn);
        mpreAnswer2 = findViewById(R.id.pre_answer2_btn);
        mpreAnswer3 = findViewById(R.id.pre_answer3_btn);
        mpreAnswer4 = findViewById(R.id.pre_answer4_btn);
        mImage = findViewById(R.id.pre_image);

        mScoreDisplay = findViewById(R.id.pre_score);
        mNbrofQuestion = findViewById(R.id.questions_count);
        mProgressBar =findViewById(R.id.pre_progress_bar);

        // Use the tag property to 'name' the buttons
        mpreAnswer1.setTag(0);
        mpreAnswer2.setTag(1);
        mpreAnswer3.setTag(2);
        mpreAnswer4.setTag(3);

        mpreAnswer1.setOnClickListener(this);
        mpreAnswer2.setOnClickListener(this);
        mpreAnswer3.setOnClickListener(this);
        mpreAnswer4.setOnClickListener(this);

        mImgQuestion = mImgBank.getImgQuestion();
        this.displayQuestion(mImgQuestion);

        mQuestionTotal = 10;
        mQuestionCounter = 1;
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);

        super.onSaveInstanceState(outState);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)


    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();
        int taganswer1 = (int) mpreAnswer1.getTag();
        int taganswer2 = (int) mpreAnswer2.getTag();
        int taganswer3 = (int) mpreAnswer3.getTag();
        int taganswer4 = (int) mpreAnswer4.getTag();

        if(responseIndex == mImgQuestion.getAnswerIndex()){
            // Bon
            Toast toast =  Toast.makeText(this, "Correct !", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM,0,100);
            toast.show();

            v.setBackgroundColor(Color.parseColor("#008000"));

            mScore++;
        } else {
            // Mauvais
            Toast toast = Toast.makeText(this, "Mauvaise réponse !",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM,0,100);
            toast.show();

            v.setBackgroundColor(Color.parseColor("#830000"));

            if(taganswer1 == mImgQuestion.getAnswerIndex()){
                mpreAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }

            else if(taganswer2 == mImgQuestion.getAnswerIndex()){
                mpreAnswer2.setBackgroundColor(Color.parseColor("#008000"));;
            }

            else if(taganswer3 == mImgQuestion.getAnswerIndex()){
                mpreAnswer3.setBackgroundColor(Color.parseColor("#008000"));

            }

            else if(taganswer4 == mImgQuestion.getAnswerIndex()){
                mpreAnswer4.setBackgroundColor(Color.parseColor("#008000"));
            }
        }

        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;
                if (--mNumberOfQuestions == 0 && mQuestionCounter <= 10) {
                    // End the game
                    endGame();
                } else {

                    mImgQuestion = mImgBank.getImgQuestion();
                    displayQuestion(mImgQuestion);
                    mQuestionCounter++;

                    mScoreDisplay.setText("Score : " + mScore);
                    mNbrofQuestion.setText(mQuestionCounter + "/10");

                    mProgressBar.setProgress(mQuestionCounter);

                    mpreAnswer1.setBackgroundColor(Color.parseColor("#000000"));
                    mpreAnswer2.setBackgroundColor(Color.parseColor("#000000"));
                    mpreAnswer3.setBackgroundColor(Color.parseColor("#000000"));
                    mpreAnswer4.setBackgroundColor(Color.parseColor("#000000"));
                }
            }
        }, 2000);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bien joué !")
                .setMessage("Votre score est de " + mScore + "/10")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // End the activity
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    private void displayQuestion(final ImgQuestion imgQuestion) {
        mpreQuestion.setText(imgQuestion.getImgQuestion());
        mImage.setImageResource(imgQuestion.getImage());
        mpreAnswer1.setText(imgQuestion.getChoiceList().get(0));
        mpreAnswer2.setText(imgQuestion.getChoiceList().get(1));
        mpreAnswer3.setText(imgQuestion.getChoiceList().get(2));
        mpreAnswer4.setText(imgQuestion.getChoiceList().get(3));
    }

    private ImgBank generateQuestions() {
        ImgQuestion imgQuestion1 = new ImgQuestion("Tu ______ meilleur que moi aux échecs.", R.drawable.echecs,
                Arrays.asList("Sont", "Es", "As", "Avons"),
                1);

        ImgQuestion imgQuestion2 = new ImgQuestion("Je ______ bon cavalier.",R.drawable.cavalier,
                Arrays.asList("Sommes", "Avons", "Suis", "Ont"),
                2);

        ImgQuestion imgQuestion3 = new ImgQuestion("Vous ______ perdu, Monsieur ?",R.drawable.perdu,
                Arrays.asList("Avez", "A", "Êtes", "Est"),
                2);

        ImgQuestion imgQuestion4 = new ImgQuestion("Nous ______ des jumelles.",R.drawable.jumelles,
                Arrays.asList("Avez", "Avons", "Êtes", "Sommes"),
                3);

        ImgQuestion imgQuestion5 = new ImgQuestion("Les étoiles ______ loin dans le ciel.",R.drawable.etoiles,
                Arrays.asList("Est", "As", "Sont", "Ont"),
                2);

        ImgQuestion imgQuestion6 = new ImgQuestion("Le lionceau ______ le petit du lion.",R.drawable.lionceau,
                Arrays.asList("A", "As", "Es", "Est"),
                3);

        ImgQuestion imgQuestion7 = new ImgQuestion("Nous ______ un train à prendre.",R.drawable.train,
                Arrays.asList("Est", "Ont", "Avons", "Sommes"),
                2);

        ImgQuestion imgQuestion8 = new ImgQuestion("L'éléphant ______ des oreilles énormes.",R.drawable.elephant,
                Arrays.asList("Ont", "Est", "A", "Sont"),
                2);

        ImgQuestion imgQuestion9 = new ImgQuestion("Vous ______ une belle voiture rouge",R.drawable.voiture,
                Arrays.asList("Ont", "Avez", "Est", "Suis"),
                1);

        ImgQuestion imgQuestion10 = new ImgQuestion("Les enfants ______ peur du loup.",R.drawable.loup2,
                Arrays.asList("Sont", "Est", "Avons", "Ont"),
                3);

        ImgQuestion imgQuestion11 = new ImgQuestion("J'______ un bouton sur la joue.",R.drawable.bouton,
                Arrays.asList("Ai", "As", "Es", "Suis"),
                0);

        ImgQuestion imgQuestion12 = new ImgQuestion("Tu ______ cinq ans aujourd'hui.",R.drawable.cinqans,
                Arrays.asList("Es", "Est", "A", "As"),
                3);


        return new ImgBank(Arrays.asList(imgQuestion1,
                imgQuestion2,
                imgQuestion3,
                imgQuestion4,
                imgQuestion5,
                imgQuestion6,
                imgQuestion7,
                imgQuestion8,
                imgQuestion9,
                imgQuestion10,
                imgQuestion11,
                imgQuestion12
        ));
    }
}

