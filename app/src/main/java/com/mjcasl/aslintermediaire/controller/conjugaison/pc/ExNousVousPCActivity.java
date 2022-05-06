package com.mjcasl.aslintermediaire.controller.conjugaison.pc;
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

public class ExNousVousPCActivity extends AppCompatActivity implements View.OnClickListener {

    //public static final long COUNTDOWN_IN_MILLIS = 11000;

    private TextView mpcQuestion;
    private Button mpcAnswer1;
    private Button mpcAnswer2;
    private Button mpcAnswer3;
    private Button mpcAnswer4;
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
        setContentView(R.layout.expcimg);


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

        mpcQuestion = findViewById(R.id.pc_question_txt);
        mpcAnswer1 = findViewById(R.id.pc_answer1_btn);
        mpcAnswer2 = findViewById(R.id.pc_answer2_btn);
        mpcAnswer3 = findViewById(R.id.pc_answer3_btn);
        mpcAnswer4 = findViewById(R.id.pc_answer4_btn);
        mImage = findViewById(R.id.pc_image);

        mScoreDisplay = findViewById(R.id.pc_score);
        mNbrofQuestion = findViewById(R.id.questions_count);
        mProgressBar =findViewById(R.id.pc_progress_bar);

        // Use the tag property to 'name' the buttons
        mpcAnswer1.setTag(0);
        mpcAnswer2.setTag(1);
        mpcAnswer3.setTag(2);
        mpcAnswer4.setTag(3);

        mpcAnswer1.setOnClickListener(this);
        mpcAnswer2.setOnClickListener(this);
        mpcAnswer3.setOnClickListener(this);
        mpcAnswer4.setOnClickListener(this);

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
        int taganswer1 = (int) mpcAnswer1.getTag();
        int taganswer2 = (int) mpcAnswer2.getTag();
        int taganswer3 = (int) mpcAnswer3.getTag();
        int taganswer4 = (int) mpcAnswer4.getTag();

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
                mpcAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }

            else if(taganswer2 == mImgQuestion.getAnswerIndex()){
                mpcAnswer2.setBackgroundColor(Color.parseColor("#008000"));;
            }

            else if(taganswer3 == mImgQuestion.getAnswerIndex()){
                mpcAnswer3.setBackgroundColor(Color.parseColor("#008000"));

            }

            else if(taganswer4 == mImgQuestion.getAnswerIndex()){
                mpcAnswer4.setBackgroundColor(Color.parseColor("#008000"));
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

                    mpcAnswer1.setBackgroundColor(Color.parseColor("#000000"));
                    mpcAnswer2.setBackgroundColor(Color.parseColor("#000000"));
                    mpcAnswer3.setBackgroundColor(Color.parseColor("#000000"));
                    mpcAnswer4.setBackgroundColor(Color.parseColor("#000000"));
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
        mpcQuestion.setText(imgQuestion.getImgQuestion());
        mImage.setImageResource(imgQuestion.getImage());
        mpcAnswer1.setText(imgQuestion.getChoiceList().get(0));
        mpcAnswer2.setText(imgQuestion.getChoiceList().get(1));
        mpcAnswer3.setText(imgQuestion.getChoiceList().get(2));
        mpcAnswer4.setText(imgQuestion.getChoiceList().get(3));
    }

    private ImgBank generateQuestions() {
        ImgQuestion imgQuestion1 = new ImgQuestion("Vous (chercher) vos clés partout.", R.drawable.cles,
                Arrays.asList("avais cherché", "avez cherché", "êtes cherché", "êtes chercher"),
                1);

        ImgQuestion imgQuestion2 = new ImgQuestion("Nous (préparer) nos bagages.",R.drawable.bagages,
                Arrays.asList("avez préparer", "avons préparé", "sommes préparés", "sommes préparer"),
                1);

        ImgQuestion imgQuestion3 = new ImgQuestion("Nous (danser) toute la nuit.",R.drawable.dansernuit,
                Arrays.asList("avez dansé", "sommes dansé", "avons dansé", "êtes dansé"),
                2);

        ImgQuestion imgQuestion4 = new ImgQuestion("Vous (balayer) la classe.",R.drawable.balayer,
                Arrays.asList("avons balayer", "sommes balayé", "avez balayé", "êtes balayé"),
                2);

        ImgQuestion imgQuestion5 = new ImgQuestion("Nous (préparer) un gâteau.",R.drawable.gateau,
                Arrays.asList("avez préparer", "avons préparé", "sommes préparé", "êtes préparer"),
                1);

        ImgQuestion imgQuestion6 = new ImgQuestion("Vous (tricoter) un pull.",R.drawable.tricoter,
                Arrays.asList("avez tricoté", "avons tricoté", "sommes tricoté", "êtes tricoté"),
                0);

        ImgQuestion imgQuestion7 = new ImgQuestion("Vous (aller) à la montagne.",R.drawable.montagnevs,
                Arrays.asList("sommes allés", "avons allé", "êtes allés", "avez allé"),
                2);

        ImgQuestion imgQuestion8 = new ImgQuestion("Vous (aimer) aller au cinéma.",R.drawable.cinema,
                Arrays.asList("avons aimé", "êtes aimés", "avez aimé", "sommes aimés"),
                2);

        ImgQuestion imgQuestion9 = new ImgQuestion("Vous (avoir) peur de la sorciére.",R.drawable.sorciere,
                Arrays.asList("avons eu", "avez eu", "sommes eu", "êtes eu"),
                1);

        ImgQuestion imgQuestion10 = new ImgQuestion("Nous (acheter) un poisson rouge.",R.drawable.poissonrouge,
                Arrays.asList("sommes acheté", "êtes acheté", "avons acheté", "avez acheté"),
                2);


        return new ImgBank(Arrays.asList(imgQuestion1,
                imgQuestion2,
                imgQuestion3,
                imgQuestion4,
                imgQuestion5,
                imgQuestion6,
                imgQuestion7,
                imgQuestion8,
                imgQuestion9,
                imgQuestion10
        ));
    }
}

