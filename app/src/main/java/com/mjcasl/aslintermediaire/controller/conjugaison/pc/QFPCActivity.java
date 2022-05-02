package com.mjcasl.aslintermediaire.controller.conjugaison.pc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mjcasl.aslintermediaire.R;
import com.mjcasl.aslintermediaire.model.ImgBank;
import com.mjcasl.aslintermediaire.model.ImgQuestion;

import java.util.Arrays;
import java.util.Locale;

public class QFPCActivity extends AppCompatActivity implements View.OnClickListener {

    public static final long COUNTDOWN_IN_MILLIS = 21000;

    private TextView mPPSQuestion;
    private Button mPPSAnswer1;
    private Button mPPSAnswer2;
    private Button mPPSAnswer3;
    private Button mPPSAnswer4;
    private ImageView mImage;

    private TextView mScoreDisplay;
    private TextView mNbrofQuestion;
    private TextView mCountDown;
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

    private ColorStateList CountDownColor;
    private CountDownTimer mCountDownTimer;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qfpps);

        mImgBank = this.generateQuestions();
        mScore = 0;
        mNumberOfQuestions = 20;

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 20;
        }

        mEnableTouchEvents = true;

        mPPSQuestion = findViewById(R.id.Pps_question_txt);
        mImage = findViewById(R.id.pps_image);
        mPPSAnswer1 = findViewById(R.id.pps_answer1_btn);
        mPPSAnswer2 = findViewById(R.id.pps_answer2_btn);
        mPPSAnswer3 = findViewById(R.id.pps_answer3_btn);
        mPPSAnswer4 = findViewById(R.id.pps_answer4_btn);

        mScoreDisplay = findViewById(R.id.pps_score);
        mNbrofQuestion = findViewById(R.id.questions_count);
        mCountDown = findViewById(R.id.pps_question_timer);
        mProgressBar =findViewById(R.id.Pps_progress_bar);

        // Use the tag property to 'name' the buttons
        mPPSAnswer1.setTag(0);
        mPPSAnswer2.setTag(1);
        mPPSAnswer3.setTag(2);
        mPPSAnswer4.setTag(3);

        mPPSAnswer1.setOnClickListener(this);
        mPPSAnswer2.setOnClickListener(this);
        mPPSAnswer3.setOnClickListener(this);
        mPPSAnswer4.setOnClickListener(this);

        mImgQuestion = mImgBank.getImgQuestion();
        this.displayImgQuestion(mImgQuestion);

        mQuestionTotal = 20;
        mQuestionCounter = 1;

        CountDownColor = mCountDown.getTextColors();
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        int taganswer1 = (int) mPPSAnswer1.getTag();
        int taganswer2 = (int) mPPSAnswer2.getTag();
        int taganswer3 = (int) mPPSAnswer3.getTag();
        int taganswer4 = (int) mPPSAnswer4.getTag();

        mCountDownTimer.cancel();

        if(responseIndex == mImgQuestion.getAnswerIndex()){
            // Bon
            Toast toast =  Toast.makeText(this, "Correct !", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM,0,350);
            toast.show();

            v.setBackgroundColor(Color.parseColor("#008000"));

            mScore++;
        } else {
            // Mauvais
            Toast toast = Toast.makeText(this, "Mauvaise réponse !",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM,0,350);
            toast.show();

            v.setBackgroundColor(Color.parseColor("#830000"));

            if(taganswer1 == mImgQuestion.getAnswerIndex()){
                mPPSAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }

            else if(taganswer2 == mImgQuestion.getAnswerIndex()){
                mPPSAnswer2.setBackgroundColor(Color.parseColor("#008000"));;
            }

            else if(taganswer3 == mImgQuestion.getAnswerIndex()){
                mPPSAnswer3.setBackgroundColor(Color.parseColor("#008000"));

            }

            else if(taganswer4 == mImgQuestion.getAnswerIndex()){
                mPPSAnswer4.setBackgroundColor(Color.parseColor("#008000"));
            }
        }
        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;
                mCountDownTimer.cancel();
                timeLeftInMillis = COUNTDOWN_IN_MILLIS;
                startCountDown();
                if (--mNumberOfQuestions == 0 && mQuestionCounter <= 20) {
                    // End the game
                    endGame();
                } else {
                    mImgQuestion = mImgBank.getImgQuestion();
                    displayImgQuestion(mImgQuestion);
                    mQuestionCounter++;

                    mScoreDisplay.setText("Score : " + mScore);
                    mNbrofQuestion.setText(mQuestionCounter + "/20");

                    mProgressBar.setProgress(mQuestionCounter);

                    mPPSAnswer1.setBackgroundColor(Color.parseColor("#000000"));
                    mPPSAnswer2.setBackgroundColor(Color.parseColor("#000000"));
                    mPPSAnswer3.setBackgroundColor(Color.parseColor("#000000"));
                    mPPSAnswer4.setBackgroundColor(Color.parseColor("#000000"));
                }
            }
        }, 1000);
    }

    private void startCountDown() {
        mCountDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDown();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDown();
                mCountDownTimer.cancel();
                if (--mNumberOfQuestions == 0) {
                    // End the game
                    endGame();
                } else {
                    mImgQuestion = mImgBank.getImgQuestion();
                    displayImgQuestion(mImgQuestion);
                    mQuestionCounter++;
                    mScoreDisplay.setText("Score : " + mScore);
                    mNbrofQuestion.setText(mQuestionCounter + "/20");
                    timeLeftInMillis = COUNTDOWN_IN_MILLIS;
                    startCountDown();
                }
            }
        }.start();
    }

    private void  updateCountDown(){

        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mCountDown.setText(timeFormatted);

        if(timeLeftInMillis < 11000){
            mCountDown.setTextColor(Color.RED);
        }
        else{
            mCountDown.setTextColor(CountDownColor);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame(){
        mCountDownTimer.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bien joué !")
                .setMessage("Votre score est de " + mScore + "/20")
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

    private void displayImgQuestion(final ImgQuestion imgQuestion) {
        mPPSQuestion.setText(imgQuestion.getImgQuestion());
        mImage.setImageResource(imgQuestion.getImage());
        mPPSAnswer1.setText(imgQuestion.getChoiceList().get(0));
        mPPSAnswer2.setText(imgQuestion.getChoiceList().get(1));
        mPPSAnswer3.setText(imgQuestion.getChoiceList().get(2));
        mPPSAnswer4.setText(imgQuestion.getChoiceList().get(3));
    }
    private ImgBank generateQuestions() {

        ImgQuestion imgQuestion1 = new ImgQuestion("Les renards mangent de la viande mais ______ aiment aussi les fruits.",
                R.drawable.renard, Arrays.asList("Je", "Tu", "Ils", "Elles"),
                2);

        ImgQuestion imgQuestion2 = new ImgQuestion("Julie est contente car ______ a une nouvelle robe.",R.drawable.julie,
                Arrays.asList("Tu", "Elle", "Nous", "Je"),
                1);

        ImgQuestion imgQuestion3 = new ImgQuestion("Les cigognes partent vers le sud et ______ reviendront au printemps.",
                R.drawable.cigogne, Arrays.asList("Il", "Elle", "Ils", "Elles"),
                3);

        ImgQuestion imgQuestion4 = new ImgQuestion("Moi, ______ trouve que cet exercice est facile.",R.drawable.moi,
                Arrays.asList("Ils", "Tu", "Je", "Nous"),
                2);

        ImgQuestion imgQuestion5 = new ImgQuestion("Demain, mes amis et moi, ______ irons au cinéma.",R.drawable.demain,
                Arrays.asList("Il", "Nous", "Je", "Elle"),
                1);

        ImgQuestion imgQuestion6 = new ImgQuestion("Le soleil se lève à l'est et ______ se couche à l'ouest.",R.drawable.soleil,
                Arrays.asList("Il", "Vous", "Je", "Elles"),
                0);

        ImgQuestion imgQuestion7 = new ImgQuestion("Ton copain et toi, ______ êtes vraiment inséparables.",R.drawable.copain,
                Arrays.asList("Je", "Vous", "Tu", "Nous"),
                1);

        ImgQuestion imgQuestion8 = new ImgQuestion("Toi, ______ as l'air d'avoir fait une bêtise. ",R.drawable.toi,
                Arrays.asList("Il", "Tu", "Ils", "Je"),
                1);

        ImgQuestion imgQuestion9 = new ImgQuestion("Mon ami et moi, ______ allons à la piscine.",R.drawable.piscine,
                Arrays.asList("Elles", "Vous", "Je", "Nous"),
                3);

        ImgQuestion imgQuestion10 = new ImgQuestion("Sonia et ses copines se lèvent et ______ dansent.",R.drawable.sonia,
                Arrays.asList("Ils", "Elles", "Il", "Elle"),
                1);

        ImgQuestion imgQuestion11 = new ImgQuestion("Les oiseaux se posent sur l'arbre et ______ chantent.",R.drawable.oiseaux5,
                Arrays.asList("Ils", "Je", "Il", "Tu"),
                0);

        ImgQuestion imgQuestion12 = new ImgQuestion("La voiture freine et ______ s'arrête au feu rouge.",R.drawable.voiturefreine,
                Arrays.asList("Ils", "Elles", "Il", "Elle"),
                3);

        ImgQuestion imgQuestion13 = new ImgQuestion("Toi et ton chien, ______ devriez aller en promenade.",R.drawable.chienprom,
                Arrays.asList("Je", "Tu", "Vous", "Nous"),
                2);

        ImgQuestion imgQuestion14 = new ImgQuestion("Toi, ______ regardes trop la télévision.",R.drawable.toitv,
                Arrays.asList("Il", "Tu", "Je", "Nous"),
                1);

        ImgQuestion imgQuestion15 = new ImgQuestion("Le loup se gonfle, puis ______ souffle sur la maison.",R.drawable.loup,
                Arrays.asList("Nous", "Ils", "Vous", "Il"),
                3);

        ImgQuestion imgQuestion16 = new ImgQuestion("Quel est le pronom personnel sujet ?",R.drawable.logo_mjc,
                Arrays.asList("Je", "Mon", "Dans", "Sur"),
                0);

        ImgQuestion imgQuestion17 = new ImgQuestion("Quel est le pronom personnel sujet ?",R.drawable.logo_mjc,
                Arrays.asList("Tout", "Sur", "Tu", "Pour"),
                2);

        ImgQuestion imgQuestion18 = new ImgQuestion("Quel est le pronom personnel sujet ?",R.drawable.logo_mjc,
                Arrays.asList("Par", "Dans", "Ta", "Il"),
                3);

        ImgQuestion imgQuestion19 = new ImgQuestion("Quel est le pronom personnel sujet ?",R.drawable.logo_mjc,
                Arrays.asList("Sur", "Pour", "Elle", "Par"),
                2);

        ImgQuestion imgQuestion20 = new ImgQuestion("Quel est le pronom personnel sujet ?",R.drawable.logo_mjc,
                Arrays.asList("Pour", "On", "Dans", "Sur"),
                1);

        ImgQuestion imgQuestion21 = new ImgQuestion("Quel est le pronom personnel sujet ?",R.drawable.logo_mjc,
                Arrays.asList("Nous", "Ta", "Mon", "Sur"),
                0);

        ImgQuestion imgQuestion22 = new ImgQuestion("Quel est le pronom personnel sujet ?",R.drawable.logo_mjc,
                Arrays.asList("Tout", "Dans", "Mon", "Vous"),
                3);

        ImgQuestion imgQuestion23 = new ImgQuestion("Quel est le pronom personnel sujet ?",R.drawable.logo_mjc,
                Arrays.asList("Par", "Ils", "Tout", "Sur"),
                1);

        ImgQuestion imgQuestion24 = new ImgQuestion("Quel est le pronom personnel sujet ?",R.drawable.logo_mjc,
                Arrays.asList("Mon", "Pour", "Sur", "Elles"),
                3);

        ImgQuestion imgQuestion25 = new ImgQuestion("La guitare éléctrique", R.drawable.guitare,
                Arrays.asList("Il", "Elle", "Ils", "Elles"),
                1);

        ImgQuestion imgQuestion26 = new ImgQuestion("Le bouquet de fleurs",R.drawable.bouquet,
                Arrays.asList("Il", "Elle", "Ils", "Elles"),
                0);

        ImgQuestion imgQuestion27 = new ImgQuestion("Le petit chat",R.drawable.chat,
                Arrays.asList("Il", "Elle", "Ils", "Elles"),
                0);

        ImgQuestion imgQuestion28 = new ImgQuestion("La peinture",R.drawable.peinture,
                Arrays.asList("Il", "Elles", "Elle", "Ils"),
                2);

        ImgQuestion imgQuestion29 = new ImgQuestion("Les oiseaux",R.drawable.oiseaux,
                Arrays.asList("Il", "Elles", "Ils", "Elle"),
                2);

        ImgQuestion imgQuestion30 = new ImgQuestion("Les chaussettes",R.drawable.chaussettes,
                Arrays.asList("Elles", "Ils", "Il", "Elle"),
                0);

        ImgQuestion imgQuestion31 = new ImgQuestion("Les voitures rouges",R.drawable.voiture,
                Arrays.asList("Ils", "Elle", "Il", "Elles"),
                3);

        ImgQuestion imgQuestion32 = new ImgQuestion("Les arbres",R.drawable.arbre,
                Arrays.asList("Il", "Elle", "Ils", "Elles"),
                2);

        ImgQuestion imgQuestion33 = new ImgQuestion("Les étoiles brillantes",R.drawable.etoiles,
                Arrays.asList("Il", "Elle", "Ils", "Elles"),
                3);

        ImgQuestion imgQuestion34 = new ImgQuestion("Les jolis jouets",R.drawable.jouets,
                Arrays.asList("Il", "Elle", "Ils", "Elles"),
                2);

        ImgQuestion imgQuestion35 = new ImgQuestion("L'épi de blé",R.drawable.epi,
                Arrays.asList("Il", "Elle", "Ils", "Elles"),
                0);

        ImgQuestion imgQuestion36 = new ImgQuestion("Léa et sa maman", R.drawable.leamaman,
                Arrays.asList("Je", "Tu", "Ils", "Elles"),
                3);

        ImgQuestion imgQuestion37 = new ImgQuestion("La joueuse de flûte",R.drawable.flute,
                Arrays.asList("Il", "Elle", "Nous", "Je"),
                1);

        ImgQuestion imgQuestion38 = new ImgQuestion("L'enfant fatigué",R.drawable.enfantfatigue,
                Arrays.asList("Il", "Elle", "Ils", "Je"),
                0);

        ImgQuestion imgQuestion39 = new ImgQuestion("Les gros nuages",R.drawable.nuage,
                Arrays.asList("Ils", "Tu", "Je", "Nous"),
                0);

        ImgQuestion imgQuestion40 = new ImgQuestion("Rémi et Anna",R.drawable.remianna,
                Arrays.asList("Je", "Tu", "Ils", "Elle"),
                2);

        ImgQuestion imgQuestion41 = new ImgQuestion("La tortue et sa salade",R.drawable.saladetortue,
                Arrays.asList("Il", "Vous", "Je", "Elles"),
                3);

        ImgQuestion imgQuestion42 = new ImgQuestion("Les bougies d'anniversaire",R.drawable.bougies,
                Arrays.asList("Je", "Nous", "Tu", "Elles"),
                3);

        ImgQuestion imgQuestion43 = new ImgQuestion("Thomas et son chien ",R.drawable.thomasiench,
                Arrays.asList("Il", "Nous", "Ils", "Elles"),
                2);

        ImgQuestion imgQuestion44 = new ImgQuestion("L'affreuse sorcière",R.drawable.sorciere,
                Arrays.asList("Elle", "Ils", "Je", "Elles"),
                0);

        ImgQuestion imgQuestion45 = new ImgQuestion("Le poisson rouge",R.drawable.poissonrouge,
                Arrays.asList("Ils", "Tu", "Il", "Nous"),
                2);
        ImgQuestion imgQuestion46 = new ImgQuestion("Vous",R.drawable.logo_mjc,
                Arrays.asList("Mes amis et moi", "Ton ami et toi", "Mon ami et moi", "Tes amis"),
                1);

        ImgQuestion imgQuestion47 = new ImgQuestion("Elles",R.drawable.logo_mjc,
                Arrays.asList("Les melons bien mûrs", "L'ananas", "La fraise rouge", "Les cerises rouges"),
                3);

        ImgQuestion imgQuestion48 = new ImgQuestion("Il",R.drawable.logo_mjc,
                Arrays.asList("Mes jouets favoris", "Mes chemises blanches", "Mon livre preféré", "Ma veste usée"),
                2);

        ImgQuestion imgQuestion49 = new ImgQuestion("Elle",R.drawable.logo_mjc,
                Arrays.asList("Les échelles", "Le camion de pompier", "La grande échelle", "Les pompiers"),
                2);

        ImgQuestion imgQuestion50 = new ImgQuestion("Ils",R.drawable.logo_mjc,
                Arrays.asList("Les moutons blancs", "Le chien du berger", "Les brebis", "La laine du mouton"),
                0);

        ImgQuestion imgQuestion51 = new ImgQuestion("Nous",R.drawable.logo_mjc,
                Arrays.asList("Ta mère et toi", "Son père et lui", "Sa mère et elle", "Mon père et moi"),
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
                imgQuestion18,
                imgQuestion19,
                imgQuestion20,
                imgQuestion21,
                imgQuestion22,
                imgQuestion23,
                imgQuestion24,
                imgQuestion25,
                imgQuestion26,
                imgQuestion27,
                imgQuestion28,
                imgQuestion29,
                imgQuestion30,
                imgQuestion31,
                imgQuestion32,
                imgQuestion33,
                imgQuestion34,
                imgQuestion35,
                imgQuestion36,
                imgQuestion37,
                imgQuestion38,
                imgQuestion39,
                imgQuestion40,
                imgQuestion41,
                imgQuestion42,
                imgQuestion43,
                imgQuestion44,
                imgQuestion45,
                imgQuestion46,
                imgQuestion47,
                imgQuestion48,
                imgQuestion49,
                imgQuestion50,
                imgQuestion51

        ));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCountDownTimer != null){
            mCountDownTimer.cancel();
        }
    }
}