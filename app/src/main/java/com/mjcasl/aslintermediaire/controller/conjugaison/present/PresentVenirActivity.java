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

public class PresentVenirActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.exvenir);


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
        ImgQuestion imgQuestion1 = new ImgQuestion("Moi je  ______ à l'école en vélo.", R.drawable.velo2,
                Arrays.asList("Viens", "Venez", "Viennent", "Venons"),
                0);

        ImgQuestion imgQuestion2 = new ImgQuestion("Ils ______ pique-niquer.",R.drawable.piquenique,
                Arrays.asList("Viens", "Viennent", "Venons", "Venez"),
                1);

        ImgQuestion imgQuestion3 = new ImgQuestion("Nous ______ manger chez vous.",R.drawable.manger,
                Arrays.asList("Venez", "Viennent", "Venons", "Viens"),
                2);

        ImgQuestion imgQuestion4 = new ImgQuestion("Tu ______ à dix heures.",R.drawable.dixheures,
                Arrays.asList("Venez", "Venons", "Viennent", "Viens"),
                3);

        ImgQuestion imgQuestion5 = new ImgQuestion("Elle ______ vient déguisée en sorcière.",R.drawable.sorciere,
                Arrays.asList("Vient", "Viens", "Viennent", "Venons"),
                0);

        ImgQuestion imgQuestion6 = new ImgQuestion("Nous ______ à l'école en bus.",R.drawable.bus,
                Arrays.asList("Venez", "Venons", "Viennent", "Viens"),
                1);

        ImgQuestion imgQuestion7 = new ImgQuestion("La girafe ______ d'Afrique.",R.drawable.giraffe,
                Arrays.asList("Viens", "Viennent", "Vient", "Venons"),
                2);

        ImgQuestion imgQuestion8 = new ImgQuestion("Tu ______ faire un tour en forêt ?",R.drawable.foret,
                Arrays.asList("Vient", "Venez", "Venons", "Viens"),
                3);

        ImgQuestion imgQuestion9 = new ImgQuestion("Je ______ de recevoir une lettre.",R.drawable.lettre,
                Arrays.asList("Viens", "Vient", "Venons", "Viennent"),
                0);

        ImgQuestion imgQuestion10 = new ImgQuestion("Les enfants ______ voir les marionnettes.",R.drawable.marrionettes,
                Arrays.asList("Viens", "Viennent", "Venons", "Venez"),
                1);

        ImgQuestion imgQuestion11 = new ImgQuestion("Es-ce que vous ______ à la piscine demain ?",R.drawable.piscine,
                Arrays.asList("Venons", "Viens", "Venez", "Viennent"),
                2);

        ImgQuestion imgQuestion12 = new ImgQuestion("Vous ______ avec nous à la plage.",R.drawable.plage2,
                Arrays.asList("Viens", "Viennent", "Venons", "Venez"),
                3);

        ImgQuestion imgQuestion13 = new ImgQuestion("Je ______ de terminer mon dessin.",R.drawable.dessin,
                Arrays.asList("Viens", "Vient", "Venez", "Venons"),
                0);

        ImgQuestion imgQuestion14 = new ImgQuestion("On dirait que tu ______ de te reveiller.",R.drawable.reveiller,
                Arrays.asList("Vont", "Viens", "Venez", "Venons"),
                1);

        ImgQuestion imgQuestion15 = new ImgQuestion("Ma copine ______  chez moi pour jouer à cache-cache.",R.drawable.cachecache,
                Arrays.asList("Viens", "Venez", "Vient", "Viennent"),
                2);

        ImgQuestion imgQuestion16 = new ImgQuestion("Les kangourous ______ d'Australie.",R.drawable.kangourou,
                Arrays.asList("Venez", "Viens", "Vient", "Viennent"),
                3);

        ImgQuestion imgQuestion17 = new ImgQuestion("Nous ______ pour faire de la musique.",R.drawable.musique,
                Arrays.asList("Venons", "Venez", "Viens", "Vient"),
                0);

        ImgQuestion imgQuestion18 = new ImgQuestion("Ce soir, vous ______ observer les étoiles ?.",R.drawable.observeretoiles,
                Arrays.asList("Viens", "Venez", "Venons", "Viennent"),
                1);


        return new ImgBank(Arrays.asList(imgQuestion1,
                imgQuestion2,
                imgQuestion3,
                imgQuestion4,
                imgQuestion10,
                imgQuestion6,
                imgQuestion7,
                imgQuestion8,
                imgQuestion9,
                imgQuestion10,
                imgQuestion11,
                imgQuestion12,
                imgQuestion13,
                imgQuestion14,
                imgQuestion15,
                imgQuestion16,
                imgQuestion17,
                imgQuestion18
        ));
    }
}

