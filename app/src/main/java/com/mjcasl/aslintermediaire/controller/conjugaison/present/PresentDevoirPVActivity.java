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

public class PresentDevoirPVActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.exdevoirpv);


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
        Question Question1 = new Question("Je ______ que tu sortes le chien chaque soir.(vouloir)",
                Arrays.asList("Veux", "Veut", "Voulons", "Veulent"),
                0);

        Question Question2 = new Question("Ne dis pas que tu ne ______ pas le faire.(pouvoir)",
                Arrays.asList("Peut", "Peux", "Peuvent", "Pouvons"),
                1);

        Question Question3 = new Question("Cet élève ______ apprendre à être autonome.(devoir)",
                Arrays.asList("Doivent", "Devons", "Doit", "Dois"),
                2);

        Question Question4 = new Question("Nous ______ offrir un voyage à nos parents.(vouloir)",
                Arrays.asList("Veut'", "Veux", "Voulez", "Voulons"),
                3);

        Question Question5 = new Question("Comment ______-vous exiger un entraînement aussi épuisant ? (pouvoir)",
                Arrays.asList("Pouvez", "Pouvons", "Peux", "Peuvent"),
                0);

        Question Question6 = new Question("______-vous des légumes et des fruits ? (vouloir)",
                Arrays.asList("Voulons", "Voulez", "Veulent", "Veux"),
                1);

        Question Question7 = new Question("Vous ______ terminer ce travail avant de sortir. (devoir)",
                Arrays.asList("Devons", "Dois", "Devez", "Doivent"),
                2);

        Question Question8 = new Question("Le directeur ______ vous voir dans son bureau. (vouloir)",
                Arrays.asList("Veulent", "Voulons", "Veux", "Veut"),
                3);

        Question Question9 = new Question("Il m'est impossible de venir cet après-midi, je ______ aller chez le dentiste. (devoir)",
                Arrays.asList("Dois", "Doivent", "Devont", "Devez"),
                0);

        Question Question10 = new Question("Les chasseurs ne ______ pas tuer le gibier toute l'année.(pouvoir)",
                Arrays.asList("Peut", "Peuvent", "Pouvons", "Pouvez"),
                1);

        Question Question11 = new Question("Nous ______ faire tout notre possible pour être de retour avant la tombée de la nuit.(devoir)",
                Arrays.asList("Dois", "Devez", "Devons", "Doivent"),
                2);

        Question Question12 = new Question("Tous les voisins ______ organiser une fête en l'honneur de René, vainqueur de la course.(vouloir)",
                Arrays.asList("Veut", "Veux", "Voulons", "Veulent"),
                3);

        Question Question13 = new Question("Nous ______ nous rencontrer dans la salle des fêtes. (pouvoir)",
                Arrays.asList("Pouvons", "Pouvez", "Peuvent", "Peux"),
                0);

        Question Question14 = new Question(" Les organisateurs de la fête ______ demander l'autorisation au maire pour occuper des locaux publics. (devoir)",
                Arrays.asList("Dois", "Doivent", "Devons", "Devez"),
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
                Question10,
                Question11,
                Question12,
                Question13,
                Question14
        ));
    }
}

