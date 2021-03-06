package com.mjcasl.aslintermediaire.controller.conjugaison.futur;

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
import com.mjcasl.aslintermediaire.model.Question;
import com.mjcasl.aslintermediaire.model.QuestionBank;

import java.util.Arrays;

public class Ex3FuturActivity extends AppCompatActivity implements View.OnClickListener {

    public static final long COUNTDOWN_IN_MILLIS = 11000;

    private TextView mfuturQuestion;
    private Button mfuturAnswer1;
    private Button mfuturAnswer2;
    private Button mfuturAnswer3;
    private Button mfuturAnswer4;

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
        setContentView(R.layout.exfuturtxt);


        mQuestionBank = this.generateQuestions();
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

        mfuturQuestion = findViewById(R.id.futur_question_txt);
        mfuturAnswer1 = findViewById(R.id.futur_answer1_btn);
        mfuturAnswer2 = findViewById(R.id.futur_answer2_btn);
        mfuturAnswer3 = findViewById(R.id.futur_answer3_btn);
        mfuturAnswer4 = findViewById(R.id.futur_answer4_btn);

        mScoreDisplay = findViewById(R.id.futur_score);
        mNbrofQuestion = findViewById(R.id.questions_count);
        mProgressBar = findViewById(R.id.futur_progress_bar);

        // Use the tag property to 'name' the buttons
        mfuturAnswer1.setTag(0);
        mfuturAnswer2.setTag(1);
        mfuturAnswer3.setTag(2);
        mfuturAnswer4.setTag(3);

        mfuturAnswer1.setOnClickListener(this);
        mfuturAnswer2.setOnClickListener(this);
        mfuturAnswer3.setOnClickListener(this);
        mfuturAnswer4.setOnClickListener(this);

        mQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mQuestion);

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
        int taganswer1 = (int) mfuturAnswer1.getTag();
        int taganswer2 = (int) mfuturAnswer2.getTag();
        int taganswer3 = (int) mfuturAnswer3.getTag();
        int taganswer4 = (int) mfuturAnswer4.getTag();

        if(responseIndex == mQuestion.getAnswerIndex()){
            // Bon
            Toast toast =  Toast.makeText(this, "Correct !", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM,0,100);
            toast.show();

            v.setBackgroundColor(Color.parseColor("#008000"));

            mScore++;
        } else {
            // Mauvais
            Toast toast = Toast.makeText(this, "Mauvaise r??ponse !",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM,0,100);
            toast.show();

            v.setBackgroundColor(Color.parseColor("#830000"));

            if(taganswer1 == mQuestion.getAnswerIndex()){
                mfuturAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }

            else if(taganswer2 == mQuestion.getAnswerIndex()){
                mfuturAnswer2.setBackgroundColor(Color.parseColor("#008000"));;
            }

            else if(taganswer3 == mQuestion.getAnswerIndex()){
                mfuturAnswer3.setBackgroundColor(Color.parseColor("#008000"));

            }

            else if(taganswer4 == mQuestion.getAnswerIndex()){
                mfuturAnswer4.setBackgroundColor(Color.parseColor("#008000"));
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

                    mQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mQuestion);
                    mQuestionCounter++;

                    mScoreDisplay.setText("Score : " + mScore);
                    mNbrofQuestion.setText(mQuestionCounter + "/10");

                    mProgressBar.setProgress(mQuestionCounter);

                    mfuturAnswer1.setBackgroundColor(Color.parseColor("#000000"));
                    mfuturAnswer2.setBackgroundColor(Color.parseColor("#000000"));
                    mfuturAnswer3.setBackgroundColor(Color.parseColor("#000000"));
                    mfuturAnswer4.setBackgroundColor(Color.parseColor("#000000"));
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
        builder.setTitle("Bien jou?? !")
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

    private void displayQuestion(final Question Question) {
        mfuturQuestion.setText(Question.getQuestion());
        mfuturAnswer1.setText(Question.getChoiceList().get(0));
        mfuturAnswer2.setText(Question.getChoiceList().get(1));
        mfuturAnswer3.setText(Question.getChoiceList().get(2));
        mfuturAnswer4.setText(Question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question Question1 = new Question("Je (sentir) un grand manque apr??s ton d??part.",
                Arrays.asList("sentirais", "sentirai", "sentiras", "sentira"),
                1);

        Question Question2 = new Question("Tu (v??tir) cette poup??e des habits que tu as cousus.",
                Arrays.asList("v??tirais", "v??tiras", "v??tira", "v??tirai"),
                1);

        Question Question3 = new Question("Il (couvrir) le livre avant la rentr??e.",
                Arrays.asList("couvrirai", "couvrira", "couvriras", "couvrirez"),
                1);

        Question Question4 = new Question("Nous (cueillir) des marguerites dans le champ voisin.",
                Arrays.asList("cueillera", "cueillerai", "cueillerons", "cueillerez"),
                2);

        Question Question5 = new Question("Vous (cuire) les p??tes ?? feu doux.",
                Arrays.asList("cuirez ", "cuiras", "cuirons", "cuira"),
                0);

        Question Question6 = new Question("Ils (??crire) une lettre de motivation.",
                Arrays.asList("??crirai", "??criras", "??criront", "??crirez"),
                2);

        Question Question7 = new Question("Vous (rire) plus tard, maintenant il faut r??viser.",
                Arrays.asList("rirons", "rirez", "rira", "rirai"),
                1);

        Question Question8 = new Question("Nous (dire) la v??rit?? avec simplicit??.",
                Arrays.asList("dirons", "direz", "dira ", "diras"),
                0);

        Question Question9 = new Question("Il (lire) ce journal demain, tant pis !",
                Arrays.asList("lirons", "lirez", "lira", "lirai"),
                2);

        Question Question10 = new Question("Tu (voir) bien ce qui se passera ensuite.",
                Arrays.asList("verrons", "verras", "verrai", "verrez"),
                1);

        return new QuestionBank(Arrays.asList(Question1,
                Question2,
                Question3,
                Question4,
                Question5,
                Question6,
                Question7,
                Question8,
                Question9,
                Question10
        ));
    }
}

