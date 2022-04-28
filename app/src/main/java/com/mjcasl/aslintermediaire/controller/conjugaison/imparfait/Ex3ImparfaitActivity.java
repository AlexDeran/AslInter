package com.mjcasl.aslintermediaire.controller.conjugaison.imparfait;
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

public class Ex3ImparfaitActivity extends AppCompatActivity implements View.OnClickListener {

    //public static final long COUNTDOWN_IN_MILLIS = 11000;

    private TextView mimparfaitQuestion;
    private Button mimparfaitAnswer1;
    private Button mimparfaitAnswer2;
    private Button mimparfaitAnswer3;
    private Button mimparfaitAnswer4;
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
        setContentView(R.layout.eximparfait3);


        mImgBank = this.generateQuestions();
        mScore = 0;
        mNumberOfQuestions = 5;

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 5;
        }

        mEnableTouchEvents = true;

        mimparfaitQuestion = findViewById(R.id.imparfait_question_txt);
        mimparfaitAnswer1 = findViewById(R.id.imparfait_answer1_btn);
        mimparfaitAnswer2 = findViewById(R.id.imparfait_answer2_btn);
        mimparfaitAnswer3 = findViewById(R.id.imparfait_answer3_btn);
        mimparfaitAnswer4 = findViewById(R.id.imparfait_answer4_btn);
        mImage = findViewById(R.id.imparfait_image);

        mScoreDisplay = findViewById(R.id.imparfait_score);
        mNbrofQuestion = findViewById(R.id.questions_count);
        mProgressBar =findViewById(R.id.imparfait_progress_bar);

        // Use the tag property to 'name' the buttons
        mimparfaitAnswer1.setTag(0);
        mimparfaitAnswer2.setTag(1);
        mimparfaitAnswer3.setTag(2);
        mimparfaitAnswer4.setTag(3);

        mimparfaitAnswer1.setOnClickListener(this);
        mimparfaitAnswer2.setOnClickListener(this);
        mimparfaitAnswer3.setOnClickListener(this);
        mimparfaitAnswer4.setOnClickListener(this);

        mImgQuestion = mImgBank.getImgQuestion();
        this.displayQuestion(mImgQuestion);

        mQuestionTotal = 5;
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
        int taganswer1 = (int) mimparfaitAnswer1.getTag();
        int taganswer2 = (int) mimparfaitAnswer2.getTag();
        int taganswer3 = (int) mimparfaitAnswer3.getTag();
        int taganswer4 = (int) mimparfaitAnswer4.getTag();

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
                mimparfaitAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }

            else if(taganswer2 == mImgQuestion.getAnswerIndex()){
                mimparfaitAnswer2.setBackgroundColor(Color.parseColor("#008000"));;
            }

            else if(taganswer3 == mImgQuestion.getAnswerIndex()){
                mimparfaitAnswer3.setBackgroundColor(Color.parseColor("#008000"));

            }

            else if(taganswer4 == mImgQuestion.getAnswerIndex()){
                mimparfaitAnswer4.setBackgroundColor(Color.parseColor("#008000"));
            }
        }

        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;
                if (--mNumberOfQuestions == 0 && mQuestionCounter <= 5) {
                    // End the game
                    endGame();
                } else {

                    mImgQuestion = mImgBank.getImgQuestion();
                    displayQuestion(mImgQuestion);
                    mQuestionCounter++;

                    mScoreDisplay.setText("Score : " + mScore);
                    mNbrofQuestion.setText(mQuestionCounter + "/5");

                    mProgressBar.setProgress(mQuestionCounter);

                    mimparfaitAnswer1.setBackgroundColor(Color.parseColor("#000000"));
                    mimparfaitAnswer2.setBackgroundColor(Color.parseColor("#000000"));
                    mimparfaitAnswer3.setBackgroundColor(Color.parseColor("#000000"));
                    mimparfaitAnswer4.setBackgroundColor(Color.parseColor("#000000"));
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
        .setMessage("Votre score est de " + mScore + "/5")
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
        mimparfaitQuestion.setText(imgQuestion.getImgQuestion());
        mImage.setImageResource(imgQuestion.getImage());
        mimparfaitAnswer1.setText(imgQuestion.getChoiceList().get(0));
        mimparfaitAnswer2.setText(imgQuestion.getChoiceList().get(1));
        mimparfaitAnswer3.setText(imgQuestion.getChoiceList().get(2));
        mimparfaitAnswer4.setText(imgQuestion.getChoiceList().get(3));
    }

    private ImgBank generateQuestions() {
        ImgQuestion imgQuestion1 = new ImgQuestion("Sagement,______ écoutaient une histoire.", R.drawable.ecouterhistoire,
                Arrays.asList("Je", "Tu", "Ils", "Elles"),
                2);

        ImgQuestion imgQuestion2 = new ImgQuestion("Quand j'étais petit, ______ sautais dans les flaques.",R.drawable.sauterflaque,
                Arrays.asList("Il", "Elle", "Nous", "Je"),
                3);

        ImgQuestion imgQuestion3 = new ImgQuestion("J'ai vu que _______ parlais avec une copine.",R.drawable.eleves2,
                Arrays.asList("Il", "Tu", "Ils", "Vous"),
                1);

        ImgQuestion imgQuestion4 = new ImgQuestion("Avant ______ voyagions en train.",R.drawable.train,
                Arrays.asList("Ils", "Tu", "Je", "Nous"),
                3);

        ImgQuestion imgQuestion5 = new ImgQuestion("Ce jour là, ______ mangiez une pizza.",R.drawable.pizza2,
                Arrays.asList("Je", "Tu", "Vous", "Elle"),
                2);

        ImgQuestion imgQuestion6 = new ImgQuestion("Cette nuit, ______ aboyait sous ma fenêtre.",R.drawable.chienaboie,
                Arrays.asList("Il", "Vous", "Je", "Elles"),
                0);

        return new ImgBank(Arrays.asList(imgQuestion1,
                imgQuestion2,
                imgQuestion3,
                imgQuestion4,
                imgQuestion5,
                imgQuestion6
        ));
    }
}

