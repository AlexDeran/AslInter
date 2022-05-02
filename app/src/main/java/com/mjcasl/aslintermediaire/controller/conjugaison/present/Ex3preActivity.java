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

public class Ex3preActivity extends AppCompatActivity implements View.OnClickListener {

    //public static final long COUNTDOWN_IN_MILLIS = 11000;

    private TextView mPreQuestion;
    private Button mPreAnswer1;
    private Button mPreAnswer2;
    private Button mPreAnswer3;
    private Button mPreAnswer4;
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
        setContentView(R.layout.expresent3);


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

        mPreQuestion = findViewById(R.id.Pre_question_txt);
        mPreAnswer1 = findViewById(R.id.Pre_answer1_btn);
        mPreAnswer2 = findViewById(R.id.Pre_answer2_btn);
        mPreAnswer3 = findViewById(R.id.Pre_answer3_btn);
        mPreAnswer4 = findViewById(R.id.Pre_answer4_btn);
        mImage = findViewById(R.id.Pre_image);

        mScoreDisplay = findViewById(R.id.Pre_score);
        mNbrofQuestion = findViewById(R.id.questions_count);
        mProgressBar =findViewById(R.id.Pre_progress_bar);

        // Use the tag property to 'name' the buttons
        mPreAnswer1.setTag(0);
        mPreAnswer2.setTag(1);
        mPreAnswer3.setTag(2);
        mPreAnswer4.setTag(3);

        mPreAnswer1.setOnClickListener(this);
        mPreAnswer2.setOnClickListener(this);
        mPreAnswer3.setOnClickListener(this);
        mPreAnswer4.setOnClickListener(this);

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
        int taganswer1 = (int) mPreAnswer1.getTag();
        int taganswer2 = (int) mPreAnswer2.getTag();
        int taganswer3 = (int) mPreAnswer3.getTag();
        int taganswer4 = (int) mPreAnswer4.getTag();

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
                mPreAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }

            else if(taganswer2 == mImgQuestion.getAnswerIndex()){
                mPreAnswer2.setBackgroundColor(Color.parseColor("#008000"));;
            }

            else if(taganswer3 == mImgQuestion.getAnswerIndex()){
                mPreAnswer3.setBackgroundColor(Color.parseColor("#008000"));

            }

            else if(taganswer4 == mImgQuestion.getAnswerIndex()){
                mPreAnswer4.setBackgroundColor(Color.parseColor("#008000"));
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

                    mPreAnswer1.setBackgroundColor(Color.parseColor("#000000"));
                    mPreAnswer2.setBackgroundColor(Color.parseColor("#000000"));
                    mPreAnswer3.setBackgroundColor(Color.parseColor("#000000"));
                    mPreAnswer4.setBackgroundColor(Color.parseColor("#000000"));
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
        mPreQuestion.setText(imgQuestion.getImgQuestion());
        mImage.setImageResource(imgQuestion.getImage());
        mPreAnswer1.setText(imgQuestion.getChoiceList().get(0));
        mPreAnswer2.setText(imgQuestion.getChoiceList().get(1));
        mPreAnswer3.setText(imgQuestion.getChoiceList().get(2));
        mPreAnswer4.setText(imgQuestion.getChoiceList().get(3));
    }

    private ImgBank generateQuestions() {
        ImgQuestion imgQuestion1 = new ImgQuestion("Lucas (marcher) en écoutant de la musique.", R.drawable.lucas,
                Arrays.asList("Marches", "Marche", "Marchent", "Marchons"), 2);

        ImgQuestion imgQuestion2 = new ImgQuestion("Mes parents et moi (passer) de bonnes vacances." ,R.drawable.parents,
                Arrays.asList("Passent", "Passons", "Passez", "Passe"),
                1);

        ImgQuestion imgQuestion3 = new ImgQuestion("Théo et Jessica (aimer) jouer au Tennis.",R.drawable.theojessica,
                Arrays.asList("Aime", "Aimes", "Aiment", "Aimez"),
                2);

        ImgQuestion imgQuestion4 = new ImgQuestion("Ton papa et toi (planter) un petit arbre.",R.drawable.papamoi,
                Arrays.asList("Plantes", "Plantez", "Plantent", "Plantons"),
                1);

        ImgQuestion imgQuestion5 = new ImgQuestion("Tu (travailler) sur ordinateur.",R.drawable.travailordi,
                Arrays.asList("Travaille", "Travailles", "Travaillent", "Travaillons"),
                1);

        ImgQuestion imgQuestion6 = new ImgQuestion("Les fleurs (pousser) au printemps.",R.drawable.fleurs,
                Arrays.asList("Poussent", "Pousse", "Poussons", "Poussez"),
                0);

        ImgQuestion imgQuestion7 = new ImgQuestion(" Nous (acheter) des bonbons.",R.drawable.bonbons,
                Arrays.asList("Achetons", "Achètes", "Achètent", "Achetez"),
                0);

        ImgQuestion imgQuestion8 = new ImgQuestion("Vous (jouer) aux cartes.",R.drawable.cartes,
                Arrays.asList("Joue", "Joues", "Jouons", "Jouez"),
                3);

        ImgQuestion imgQuestion9 = new ImgQuestion("Mes amis (arriver) à l'école.",R.drawable.amis,
                Arrays.asList("Arrive", "Arrivent", "Arrivons", "Arrivez"),
                1);

        ImgQuestion imgQuestion10 = new ImgQuestion("Tu (chanter) trop fort.",R.drawable.chanteur,
                Arrays.asList("Chantons", "Chante", "Chantes", "Chantez"),
                2);

        ImgQuestion imgQuestion11 = new ImgQuestion("Ma cousine (écouter) de la musique.",R.drawable.cousine,
                Arrays.asList("Écoute", "Écoutes", "Écoutons", "Écoutent"),
                0);

        ImgQuestion imgQuestion12 = new ImgQuestion("Je (terminer) mon travail.",R.drawable.travail,
                Arrays.asList("Termines", "Termine", "Terminent", "Terminons"),
                1);

        ImgQuestion imgQuestion13 = new ImgQuestion("Joey (choisir) toujours des vêtements qui lui vont à merveille !",R.drawable.logo_mjc,
                Arrays.asList("Choisi", "Choisis", "Choissient", "Choisit"),
                3);

        ImgQuestion imgQuestion14 = new ImgQuestion("Nous (accomplir) notre travail avec dextérité.",R.drawable.logo_mjc,
                Arrays.asList("Accomplissent", "Accomplissons", "Accomplit", "Accomplis"),
                1);

        ImgQuestion imgQuestion15 = new ImgQuestion("Dans les films, le héros (surgir) généralement de nulle part pour sauver sa bien-aimée.",R.drawable.logo_mjc,
                Arrays.asList("Surgissons", "Surgissez", "Surgit", "Surgis"),
                2);

        ImgQuestion imgQuestion16 = new ImgQuestion("Je (s'épanouir) dans cet environnement.",R.drawable.logo_mjc,
                Arrays.asList("M'épanouis", "S'épanouis", "S'épanouit", "M'épanouit"),
                0);

        ImgQuestion imgQuestion17 = new ImgQuestion("Mes grands-parents nous (réunir) chaque année.",R.drawable.logo_mjc,
                Arrays.asList("Réunis", "Réunissent", "Réunit", "Réunissons"),
                1);

        ImgQuestion imgQuestion18 = new ImgQuestion(" Nous (enrichir) notre vocabulaire en lisant le journal.",R.drawable.logo_mjc,
                Arrays.asList("Enrichis", "Enrichissez", "Enrichissent", "Enrichissons"),
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

