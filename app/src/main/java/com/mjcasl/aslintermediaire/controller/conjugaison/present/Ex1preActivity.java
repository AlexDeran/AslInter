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

public class Ex1preActivity extends AppCompatActivity implements View.OnClickListener {

    public static final long COUNTDOWN_IN_MILLIS = 11000;

    private TextView mpreQuestion;
    private Button mpreAnswer1;
    private Button mpreAnswer2;
    private Button mpreAnswer3;
    private Button mpreAnswer4;

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
        setContentView(R.layout.expresenttxt);


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

        mpreQuestion = findViewById(R.id.pre_question_txt);
        mpreAnswer1 = findViewById(R.id.pre_answer1_btn);
        mpreAnswer2 = findViewById(R.id.pre_answer2_btn);
        mpreAnswer3 = findViewById(R.id.pre_answer3_btn);
        mpreAnswer4 = findViewById(R.id.pre_answer4_btn);

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
        int taganswer1 = (int) mpreAnswer1.getTag();
        int taganswer2 = (int) mpreAnswer2.getTag();
        int taganswer3 = (int) mpreAnswer3.getTag();
        int taganswer4 = (int) mpreAnswer4.getTag();

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
                mpreAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }

            else if(taganswer2 == mQuestion.getAnswerIndex()){
                mpreAnswer2.setBackgroundColor(Color.parseColor("#008000"));;
            }

            else if(taganswer3 == mQuestion.getAnswerIndex()){
                mpreAnswer3.setBackgroundColor(Color.parseColor("#008000"));

            }

            else if(taganswer4 == mQuestion.getAnswerIndex()){
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

                    mQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mQuestion);
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

    private void displayQuestion(final Question Question) {
        mpreQuestion.setText(Question.getQuestion());
        mpreAnswer1.setText(Question.getChoiceList().get(0));
        mpreAnswer2.setText(Question.getChoiceList().get(1));
        mpreAnswer3.setText(Question.getChoiceList().get(2));
        mpreAnswer4.setText(Question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question Question1 = new Question("______ félicitons le gagnant de la course.",
                Arrays.asList("Je", "Nous", "Tu", "Il"),
                1);

        Question Question2 = new Question("______ adore les dauphins et Harry Potter.",
                Arrays.asList("Nous", "Vous", "Ils", "J'"),
                3);

        Question Question3 = new Question("______ prennent place sur la ligne de départ.",
                Arrays.asList("Nous", "Il", "Tu", "Ils"),
                3);

        Question Question4 = new Question("______ applaudis tous les participants.",
                Arrays.asList("J'", "Vous", "Elle", "Nous"),
                0);

        Question Question5 = new Question("______ poursuivez les prisonniers qui se sont échappés.",
                Arrays.asList("Je", "Tu", "Nous", "Vous"),
                3);

        Question Question6 = new Question("______ pratiquent la gymnastique depuis plusieurs années.",
                Arrays.asList("Ils", "Je", "Nous", "Elle"),
                0);

        Question Question7 = new Question("______ accordes beaucoup trop d'importance à cette nouvelle.",
                Arrays.asList("Je", "Nous", "Tu", "Ils"),
                2);

        Question Question8 = new Question("______ fleurit au printemps.",
                Arrays.asList("Les capucines", "Le lilas", "Les violettes", "Les roses"),
                1);

        Question Question9 = new Question("______ crient très fort quand ______ jouent.",
                Arrays.asList("Il", "Ils", "Je", "Vous"),
                1);

        Question Question10 = new Question("______ peux participer à cette compétition car j'ai enfin l'âge nécessaire.",
                Arrays.asList("Il", "Ils", "Je", "Vous"),
                2);

        Question Question11 = new Question("______ bavarde avec un ami.",
                Arrays.asList("Je", "Tu", "Nous", "Vous"),
                0);

        Question Question12 = new Question("______ envoyez une lettre.",
                Arrays.asList("Tu", "Ils", "Je", "Vous"),
                3);

        Question Question13 = new Question("______ regardent la télévision.",
                Arrays.asList("Il", "Ils", "Je", "Vous"),
                1);

        Question Question14 = new Question("______ porte un pantalon bleu.",
                Arrays.asList("Il", "Nous", "Tu", "Vous"),
                0);

        Question Question15 = new Question("______ achetons des bonbons.",
                Arrays.asList("Tu", "Nous", "Elle", "Vous"),
                1);

        Question Question16 = new Question("______ laves la voiture.",
                Arrays.asList("Tu", "Nous", "Je", "Elles"),
                0);

        return new QuestionBank(Arrays.asList(Question1,
                Question2,
                Question3,
                Question4,
                Question5,
                Question6,
                Question7,
                Question8,
                Question9,
                Question10,
                Question11,
                Question12,
                Question13,
                Question14,
                Question15,
                Question16
        ));
    }
}

