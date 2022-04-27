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

public class Ex1ImparfaitActivity extends AppCompatActivity implements View.OnClickListener {

    public static final long COUNTDOWN_IN_MILLIS = 11000;

    private TextView mImparfaitQuestion;
    private Button mImparfaitAnswer1;
    private Button mImparfaitAnswer2;
    private Button mImparfaitAnswer3;
    private Button mImparfaitAnswer4;

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
        setContentView(R.layout.eximparfait1);


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

        mImparfaitQuestion = findViewById(R.id.Imparfait_question_txt);
        mImparfaitAnswer1 = findViewById(R.id.Imparfait_answer1_btn);
        mImparfaitAnswer2 = findViewById(R.id.Imparfait_answer2_btn);
        mImparfaitAnswer3 = findViewById(R.id.Imparfait_answer3_btn);
        mImparfaitAnswer4 = findViewById(R.id.Imparfait_answer4_btn);

        mScoreDisplay = findViewById(R.id.Imparfait_score);
        mNbrofQuestion = findViewById(R.id.questions_count);
        mProgressBar =findViewById(R.id.Imparfait_progress_bar);

        // Use the tag property to 'name' the buttons
        mImparfaitAnswer1.setTag(0);
        mImparfaitAnswer2.setTag(1);
        mImparfaitAnswer3.setTag(2);
        mImparfaitAnswer4.setTag(3);

        mImparfaitAnswer1.setOnClickListener(this);
        mImparfaitAnswer2.setOnClickListener(this);
        mImparfaitAnswer3.setOnClickListener(this);
        mImparfaitAnswer4.setOnClickListener(this);

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
        int taganswer1 = (int) mImparfaitAnswer1.getTag();
        int taganswer2 = (int) mImparfaitAnswer2.getTag();
        int taganswer3 = (int) mImparfaitAnswer3.getTag();
        int taganswer4 = (int) mImparfaitAnswer4.getTag();

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
                mImparfaitAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }

            else if(taganswer2 == mQuestion.getAnswerIndex()){
                mImparfaitAnswer2.setBackgroundColor(Color.parseColor("#008000"));;
            }

            else if(taganswer3 == mQuestion.getAnswerIndex()){
                mImparfaitAnswer3.setBackgroundColor(Color.parseColor("#008000"));

            }

            else if(taganswer4 == mQuestion.getAnswerIndex()){
                mImparfaitAnswer4.setBackgroundColor(Color.parseColor("#008000"));
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

                    mImparfaitAnswer1.setBackgroundColor(Color.parseColor("#000000"));
                    mImparfaitAnswer2.setBackgroundColor(Color.parseColor("#000000"));
                    mImparfaitAnswer3.setBackgroundColor(Color.parseColor("#000000"));
                    mImparfaitAnswer4.setBackgroundColor(Color.parseColor("#000000"));
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

    private void displayQuestion(final Question Question) {
        mImparfaitQuestion.setText(Question.getQuestion());
        mImparfaitAnswer1.setText(Question.getChoiceList().get(0));
        mImparfaitAnswer2.setText(Question.getChoiceList().get(1));
        mImparfaitAnswer3.setText(Question.getChoiceList().get(2));
        mImparfaitAnswer4.setText(Question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question Question1 = new Question("Maryline (jouer) avec son frère pendant que leurs parents regardaient la télévision.",
                Arrays.asList("Jouer", "Jouai", "Jouais", "Jouait"),
                3);

        Question Question2 = new Question("Nous (aller) à la mer chaque matin avec le club de plongée.",
                Arrays.asList("Allons", "Alliont", "Allions", "Allion"),
                2);

        Question Question3 = new Question("Je (revenir) chaque jour en autobus.",
                Arrays.asList("Revener", "Revenais", "Revenez", "Revenait"),
                1);

        Question Question4 = new Question(" Vous (être) au bord des larmes en apprenant la nouvelle.",
                Arrays.asList("Etiez", "Etier", "Etier", "Etez"),
                0);

        Question Question5 = new Question("Tu (attendre) cette promotion avec impatience.",
                Arrays.asList("Attender", "Attendais", "Attendez", "Attendait"),
                1);

        Question Question6 = new Question("Le jardin (paraître) plus beau qu'avant.",
                Arrays.asList("Paraissaient", "Paraissais", "Paraissait", "Paraisser"),
                2);

        Question Question7 = new Question("Le téléphone (sonner) toutes les cinq minutes.",
                Arrays.asList("Sonner", "Sonnait", "Sonnais", "Sonnaient"),
                1);

        Question Question8 = new Question("Mon chien (faire) des trous dans le sable et je riais.",
                Arrays.asList("Faiser", "Faisaient", "Faisait", "Faisez"),
                2);

        Question Question9 = new Question("Mes parents (s'arrêter) toujours à mi-chemin pour se reposer.",
                Arrays.asList("S'arrêtai", "S'arrêtez", "S'arrêtaient", "S'arrêtait"),
                2);

        Question Question10 = new Question("Mon professeur de maths m'(appeler) par mon prénom.",
                Arrays.asList("Appelez", "Appelaient", "Appelait", "Appelais"),
                2);

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

