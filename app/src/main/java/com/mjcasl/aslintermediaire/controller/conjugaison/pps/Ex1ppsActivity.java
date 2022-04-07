package com.mjcasl.aslintermediaire.controller.conjugaison.pps;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mjcasl.aslintermediaire.R;
import com.mjcasl.aslintermediaire.model.QuestionBank;
import com.mjcasl.aslintermediaire.model.Question;

import java.util.Arrays;

public class Ex1ppsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final long COUNTDOWN_IN_MILLIS = 11000;

    private TextView mPpsQuestion;
    private Button mPpsAnswer1;
    private Button mPpsAnswer2;
    private Button mPpsAnswer3;
    private Button mPpsAnswer4;

    private TextView mScoreDisplay;
    private TextView mNbrofQuestion;
    private ProgressBar mProgressBar;

    private QuestionBank mQuestionBank;
    private Question mQuestion;

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
        setContentView(R.layout.activity_expps1);


        mQuestionBank = this.generateQuestions();
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

        mPpsQuestion = findViewById(R.id.Pps_question_txt);
        mPpsAnswer1 = findViewById(R.id.pps_answer1_btn);
        mPpsAnswer2 = findViewById(R.id.pps_answer2_btn);
        mPpsAnswer3 = findViewById(R.id.pps_answer3_btn);
        mPpsAnswer4 = findViewById(R.id.pps_answer4_btn);

        mScoreDisplay = findViewById(R.id.pps_score);
        mNbrofQuestion = findViewById(R.id.questions_count);
        mProgressBar =findViewById(R.id.Pps_progress_bar);

        // Use the tag property to 'name' the buttons
        mPpsAnswer1.setTag(0);
        mPpsAnswer2.setTag(1);
        mPpsAnswer3.setTag(2);
        mPpsAnswer4.setTag(3);

        mPpsAnswer1.setOnClickListener(this);
        mPpsAnswer2.setOnClickListener(this);
        mPpsAnswer3.setOnClickListener(this);
        mPpsAnswer4.setOnClickListener(this);

        mQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mQuestion);

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
        int taganswer1 = (int) mPpsAnswer1.getTag();
        int taganswer2 = (int) mPpsAnswer2.getTag();
        int taganswer3 = (int) mPpsAnswer3.getTag();
        int taganswer4 = (int) mPpsAnswer4.getTag();

        if(responseIndex == mQuestion.getAnswerIndex()){
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

            if(taganswer1 == mQuestion.getAnswerIndex()){
                mPpsAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }

            else if(taganswer2 == mQuestion.getAnswerIndex()){
                mPpsAnswer2.setBackgroundColor(Color.parseColor("#008000"));;
            }

            else if(taganswer3 == mQuestion.getAnswerIndex()){
                mPpsAnswer3.setBackgroundColor(Color.parseColor("#008000"));

            }

            else if(taganswer4 == mQuestion.getAnswerIndex()){
                mPpsAnswer4.setBackgroundColor(Color.parseColor("#008000"));
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

                    mQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mQuestion);
                    mQuestionCounter++;

                    mScoreDisplay.setText("Score : " + mScore);
                    mNbrofQuestion.setText(mQuestionCounter + "/5");

                    mProgressBar.setProgress(mQuestionCounter);

                    mPpsAnswer1.setBackgroundColor(Color.parseColor("#000000"));
                    mPpsAnswer2.setBackgroundColor(Color.parseColor("#000000"));
                    mPpsAnswer3.setBackgroundColor(Color.parseColor("#000000"));
                    mPpsAnswer4.setBackgroundColor(Color.parseColor("#000000"));
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

    private void displayQuestion(final Question Question) {
        mPpsQuestion.setText(Question.getQuestion());
        mPpsAnswer1.setText(Question.getChoiceList().get(0));
        mPpsAnswer2.setText(Question.getChoiceList().get(1));
        mPpsAnswer3.setText(Question.getChoiceList().get(2));
        mPpsAnswer4.setText(Question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question Question1 = new Question("Quel est le pronom personnel sujet ?",
                Arrays.asList("Je", "Mon", "Dans", "Sur"),
                0);

        Question Question2 = new Question("Quel est le pronom personnel sujet ?",
                Arrays.asList("Tout", "Sur", "Tu", "Pour"),
                2);

        Question Question3 = new Question("Quel est le pronom personnel sujet ?",
                Arrays.asList("Par", "Dans", "Ta", "Il"),
                3);

        Question Question4 = new Question("Quel est le pronom personnel sujet ?",
                Arrays.asList("Sur", "Pour", "Elle", "Par"),
                2);

        Question Question5 = new Question("Quel est le pronom personnel sujet ?",
                Arrays.asList("Pour", "On", "Dans", "Sur"),
                1);

        Question Question6 = new Question("Quel est le pronom personnel sujet ?",
                Arrays.asList("Nous", "Ta", "Mon", "Sur"),
                0);

        Question Question7 = new Question("Quel est le pronom personnel sujet ?",
                Arrays.asList("Tout", "Dans", "Mon", "Vous"),
                3);

        Question Question8 = new Question("Quel est le pronom personnel sujet ?",
                Arrays.asList("Par", "Ils", "Tout", "Sur"),
                1);

        Question Question9 = new Question("Quel est le pronom personnel sujet ?",
                Arrays.asList("Mon", "Pour", "Sur", "Elles"),
                3);

        return new QuestionBank(Arrays.asList(Question1,
                Question2,
                Question3,
                Question4,
                Question5,
                Question6,
                Question7,
                Question8,
                Question9
        ));
    }
}

