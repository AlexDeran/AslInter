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

public class Ex3ppsActivity extends AppCompatActivity implements View.OnClickListener {

    //public static final long COUNTDOWN_IN_MILLIS = 11000;

    private TextView mPpsQuestion;
    private Button mPpsAnswer1;
    private Button mPpsAnswer2;
    private Button mPpsAnswer3;
    private Button mPpsAnswer4;
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
        setContentView(R.layout.activity_expps3);


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

        mPpsQuestion = findViewById(R.id.Pps_question_txt);
        mPpsAnswer1 = findViewById(R.id.pps_answer1_btn);
        mPpsAnswer2 = findViewById(R.id.pps_answer2_btn);
        mPpsAnswer3 = findViewById(R.id.pps_answer3_btn);
        mPpsAnswer4 = findViewById(R.id.pps_answer4_btn);
        mImage = findViewById(R.id.pps_image);

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
        int taganswer1 = (int) mPpsAnswer1.getTag();
        int taganswer2 = (int) mPpsAnswer2.getTag();
        int taganswer3 = (int) mPpsAnswer3.getTag();
        int taganswer4 = (int) mPpsAnswer4.getTag();

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
                mPpsAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }

            else if(taganswer2 == mImgQuestion.getAnswerIndex()){
                mPpsAnswer2.setBackgroundColor(Color.parseColor("#008000"));;
            }

            else if(taganswer3 == mImgQuestion.getAnswerIndex()){
                mPpsAnswer3.setBackgroundColor(Color.parseColor("#008000"));

            }

            else if(taganswer4 == mImgQuestion.getAnswerIndex()){
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

                    mImgQuestion = mImgBank.getImgQuestion();
                    displayQuestion(mImgQuestion);
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

    private void displayQuestion(final ImgQuestion imgQuestion) {
        mPpsQuestion.setText(imgQuestion.getImgQuestion());
        mImage.setImageResource(imgQuestion.getImage());
        mPpsAnswer1.setText(imgQuestion.getChoiceList().get(0));
        mPpsAnswer2.setText(imgQuestion.getChoiceList().get(1));
        mPpsAnswer3.setText(imgQuestion.getChoiceList().get(2));
        mPpsAnswer4.setText(imgQuestion.getChoiceList().get(3));
    }

    private ImgBank generateQuestions() {
        ImgQuestion imgQuestion1 = new ImgQuestion("Léa et sa maman", R.drawable.leamaman,
                Arrays.asList("Je", "Tu", "Ils", "Elles"),
                3);

        ImgQuestion imgQuestion2 = new ImgQuestion("La joueuse de flûte",R.drawable.flute,
                Arrays.asList("Il", "Elle", "Nous", "Je"),
                1);

        ImgQuestion imgQuestion3 = new ImgQuestion("L'enfant fatigué",R.drawable.enfantfatigue,
                Arrays.asList("Il", "Elle", "Ils", "Je"),
                0);

        ImgQuestion imgQuestion4 = new ImgQuestion("Les gros nuages",R.drawable.nuage,
                Arrays.asList("Ils", "Tu", "Je", "Nous"),
                0);

        ImgQuestion imgQuestion5 = new ImgQuestion("Rémi et Anna",R.drawable.remianna,
                Arrays.asList("Je", "Tu", "Ils", "Elle"),
                2);

        ImgQuestion imgQuestion6 = new ImgQuestion("La tortue et sa salade",R.drawable.saladetortue,
                Arrays.asList("Il", "Vous", "Je", "Elles"),
                3);

        return new ImgBank(Arrays.asList(imgQuestion1,
                imgQuestion2,
                imgQuestion3,
                imgQuestion4,
                imgQuestion5,
                imgQuestion6
        ));
    }
}

