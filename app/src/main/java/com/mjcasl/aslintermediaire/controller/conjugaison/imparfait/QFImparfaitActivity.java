package com.mjcasl.aslintermediaire.controller.conjugaison.imparfait;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
import com.mjcasl.aslintermediaire.model.Question;

import java.util.Arrays;
import java.util.Locale;

public class QFImparfaitActivity extends AppCompatActivity implements View.OnClickListener {

    public static final long COUNTDOWN_IN_MILLIS = 21000;

    private TextView mimparfaitQuestion;
    private Button mimparfaitAnswer1;
    private Button mimparfaitAnswer2;
    private Button mimparfaitAnswer3;
    private Button mimparfaitAnswer4;
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
        setContentView(R.layout.qfimparfait);

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

        mimparfaitQuestion = findViewById(R.id.imparfait_question_txt);
        mImage = findViewById(R.id.imparfait_image);
        mimparfaitAnswer1 = findViewById(R.id.imparfait_answer1_btn);
        mimparfaitAnswer2 = findViewById(R.id.imparfait_answer2_btn);
        mimparfaitAnswer3 = findViewById(R.id.imparfait_answer3_btn);
        mimparfaitAnswer4 = findViewById(R.id.imparfait_answer4_btn);

        mScoreDisplay = findViewById(R.id.imparfait_score);
        mNbrofQuestion = findViewById(R.id.questions_count);
        mCountDown = findViewById(R.id.imparfait_question_timer);
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

        int taganswer1 = (int) mimparfaitAnswer1.getTag();
        int taganswer2 = (int) mimparfaitAnswer2.getTag();
        int taganswer3 = (int) mimparfaitAnswer3.getTag();
        int taganswer4 = (int) mimparfaitAnswer4.getTag();

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

                    mimparfaitAnswer1.setBackgroundColor(Color.parseColor("#000000"));
                    mimparfaitAnswer2.setBackgroundColor(Color.parseColor("#000000"));
                    mimparfaitAnswer3.setBackgroundColor(Color.parseColor("#000000"));
                    mimparfaitAnswer4.setBackgroundColor(Color.parseColor("#000000"));
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
        mimparfaitQuestion.setText(imgQuestion.getImgQuestion());
        mImage.setImageResource(imgQuestion.getImage());
        mimparfaitAnswer1.setText(imgQuestion.getChoiceList().get(0));
        mimparfaitAnswer2.setText(imgQuestion.getChoiceList().get(1));
        mimparfaitAnswer3.setText(imgQuestion.getChoiceList().get(2));
        mimparfaitAnswer4.setText(imgQuestion.getChoiceList().get(3));
    }
    private ImgBank generateQuestions() {

        ImgQuestion imgQuestion1 = new ImgQuestion("Maryline (jouer) avec son frère pendant que leurs parents regardaient la télévision.",R.drawable.logo_mjc,
                Arrays.asList("Jouer", "Jouai", "Jouais", "Jouait"),
                3);

        ImgQuestion imgQuestion2 = new ImgQuestion("Nous (aller) à la mer chaque matin avec le club de plongée.",R.drawable.logo_mjc,
                Arrays.asList("Allons", "Alliont", "Allions", "Allion"),
                2);

        ImgQuestion imgQuestion3 = new ImgQuestion("Je (revenir) chaque jour en autobus.",R.drawable.logo_mjc,
                Arrays.asList("Revener", "Revenais", "Revenez", "Revenait"),
                1);

        ImgQuestion imgQuestion4 = new ImgQuestion(" Vous (être) au bord des larmes en apprenant la nouvelle.",R.drawable.logo_mjc,
                Arrays.asList("Etiez", "Etier", "Etier", "Etez"),
                0);

        ImgQuestion imgQuestion5 = new ImgQuestion("Tu (attendre) cette promotion avec impatience.",R.drawable.logo_mjc,
                Arrays.asList("Attender", "Attendais", "Attendez", "Attendait"),
                1);

        ImgQuestion imgQuestion6 = new ImgQuestion("Le jardin (paraître) plus beau qu'avant.",R.drawable.logo_mjc,
                Arrays.asList("Paraissaient", "Paraissais", "Paraissait", "Paraisser"),
                2);

        ImgQuestion imgQuestion7 = new ImgQuestion("Le téléphone (sonner) toutes les cinq minutes.",R.drawable.logo_mjc,
                Arrays.asList("Sonner", "Sonnait", "Sonnais", "Sonnaient"),
                1);

        ImgQuestion imgQuestion8 = new ImgQuestion("Mon chien (faire) des trous dans le sable et je riais.",R.drawable.logo_mjc,
                Arrays.asList("Faiser", "Faisaient", "Faisait", "Faisez"),
                2);

        ImgQuestion imgQuestion9 = new ImgQuestion("Mes parents (s'arrêter) toujours à mi-chemin pour se reposer.",R.drawable.logo_mjc,
                Arrays.asList("S'arrêtai", "S'arrêtez", "S'arrêtaient", "S'arrêtait"),
                2);

        ImgQuestion imgQuestion10 = new ImgQuestion("Mon professeur de maths m'(appeler) par mon prénom.",R.drawable.logo_mjc,
                Arrays.asList("Appelez", "Appelaient", "Appelait", "Appelais"),
                2);

        ImgQuestion imgQuestion11 = new ImgQuestion("J'______ des tas de bonbons.", R.drawable.bonbons2,
                Arrays.asList("Acheter", "Achetais", "Achetait", "Achetez"),
                1);

        ImgQuestion imgQuestion12 = new ImgQuestion("Nous ______ sur la glace.",R.drawable.patiner,
                Arrays.asList("Patinons", "Patinions", "Patinion", "Patignons"),
                1);

        ImgQuestion imgQuestion13 = new ImgQuestion("Vous ______ des légumes.",R.drawable.marche,
                Arrays.asList("Achetiez", "Achetez", "Achetier", "Achetiée"),
                0);

        ImgQuestion imgQuestion14 = new ImgQuestion("Les enfants ______ à la balançoire.",R.drawable.balancoire,
                Arrays.asList("Jouait", "Jouer", "Jouaient", "Jouais"),
                2);

        ImgQuestion imgQuestion15 = new ImgQuestion("Je ______ à la marelle.",R.drawable.marelle,
                Arrays.asList("Jouait", "Jouer", "Jouaient", "Jouais"),
                3);

        ImgQuestion imgQuestion16 = new ImgQuestion("Tu ______ avec le ballon.",R.drawable.attraperballon,
                Arrays.asList("Jouais", "Jouait", "Jouaient", "Jouez"),
                0);

        ImgQuestion imgQuestion17 = new ImgQuestion("Ma mère ______ une paire de chaussures.",R.drawable.acheterchaussures,
                Arrays.asList("Achetais", "Achetez", "Achetait", "Achetaient"),
                2);

        ImgQuestion imgQuestion18 = new ImgQuestion("Il ______ des papillons.",R.drawable.chasserpapillon,
                Arrays.asList("Attrapais", "Attrapait", "Attrapez", "Attraper"),
                1);

        ImgQuestion imgQuestion19 = new ImgQuestion("Vous ______ aux cartes toute la soirée.",R.drawable.jouercartes,
                Arrays.asList("Jouiez", "Jouer", "Jouez", "Jouier"),
                0);

        ImgQuestion imgQuestion20 = new ImgQuestion("La jeune fille ______ comme une championne.",R.drawable.championne,
                Arrays.asList("Patinais", "Patinait", "Patiner", "Patinez"),
                1);

        ImgQuestion imgQuestion21 = new ImgQuestion("Sagement,______ écoutaient une histoire.", R.drawable.ecouterhistoire,
                Arrays.asList("Je", "Tu", "Ils", "Elles"),
                2);

        ImgQuestion imgQuestion22 = new ImgQuestion("Quand j'étais petit, ______ sautais dans les flaques.",R.drawable.sauterflaque,
                Arrays.asList("Il", "Elle", "Nous", "Je"),
                3);

        ImgQuestion imgQuestion23 = new ImgQuestion("J'ai vu que _______ parlais avec une copine.",R.drawable.eleves2,
                Arrays.asList("Il", "Tu", "Ils", "Vous"),
                1);

        ImgQuestion imgQuestion24 = new ImgQuestion("Avant ______ voyagions en train.",R.drawable.train,
                Arrays.asList("Ils", "Tu", "Je", "Nous"),
                3);

        ImgQuestion imgQuestion25 = new ImgQuestion("Ce jour là, ______ mangiez une pizza.",R.drawable.pizza2,
                Arrays.asList("Je", "Tu", "Vous", "Elle"),
                2);

        ImgQuestion imgQuestion26 = new ImgQuestion("Cette nuit, ______ aboyait sous ma fenêtre.",R.drawable.chienaboie,
                Arrays.asList("Il", "Vous", "Je", "Elles"),
                0);

        ImgQuestion imgQuestion27 = new ImgQuestion("Tu vas au cours de danse. =>                        Tu ______ au cours de danse.", R.drawable.danseuse,
                Arrays.asList("Allais", "Allez", "Aller", "Allé"),
                0);

        ImgQuestion imgQuestion28 = new ImgQuestion("Nous racontons des histoires drôles. => Nous ______ des histoires drôles.",R.drawable.histoiresdroles,
                Arrays.asList("Racontons", "Racontions", "Racontion", "Racontiez"),
                1);

        ImgQuestion imgQuestion29 = new ImgQuestion("Il tremble de froid. =>                                         Il ______ de froid.",R.drawable.trembler,
                Arrays.asList("Trembler", "Tremblais", "Tremblez", "Tremblait"),
                3);

        ImgQuestion imgQuestion30 = new ImgQuestion("Je porte des lunettes noires =>                     Je ______ des lunettes noires.",R.drawable.garslunettespiscine,
                Arrays.asList("Porter", "Portais", "Portait", "Portez"),
                1);

        ImgQuestion imgQuestion31 = new ImgQuestion("Il grimpe à l'arbre =>                                          Il ______ à l'arbre.",R.drawable.grimperarbre,
                Arrays.asList("Grimpais", "Grimper", "Grimpait", "Grimpez"),
                2);

        ImgQuestion imgQuestion32 = new ImgQuestion("Vous nagez dans la mer =>                               Vous ______ dans la mer.",R.drawable.nageurs,
                Arrays.asList("Nagez", "Nagier", "Nagiez", "Nageaient"),
                2);

        ImgQuestion imgQuestion33 = new ImgQuestion("Vous dansiez avec vos amis. =>                  Nous ______ avec nos amis.", R.drawable.danseramis,
                Arrays.asList("Dansais", "Dansaient", "Dansions", "Dansait"),
                2);

        ImgQuestion imgQuestion34 = new ImgQuestion("Mon copain jouait au yoyo. =>                           Tu ______ au yoyo.",R.drawable.yoyo,
                Arrays.asList("Jouions", "Jouiez", "Jouais", "Jouait"),
                2);

        ImgQuestion imgQuestion35 = new ImgQuestion("Manon sautait sur le trampoline. =>                 Je ______ sur le trampoline.",R.drawable.trampoline,
                Arrays.asList("Sautaient", "Sautiez", "Sautait", "Sautais"),
                3);

        ImgQuestion imgQuestion36 = new ImgQuestion("Les musiciens défilaient dans la rue. =>   La fanfare ______ dans la rue.",R.drawable.fanfare,
                Arrays.asList("Défilais", "Défilait", "Défilaient", "Défiliez"),
                1);

        ImgQuestion imgQuestion37 = new ImgQuestion("Tu mangeais à la cantine. =>                   Nous ______ à la cantine.",R.drawable.cantine,
                Arrays.asList("Mangeais", "Mangions", "Mangiez", "Mangeait"),
                2);

        ImgQuestion imgQuestion38 = new ImgQuestion("Nous nous cachions derrière un arbre. => Les enfants se  ______ derrière un arbre.",R.drawable.nageurs,
                Arrays.asList("Cachaient", "Cachait", "Cachais", "Cachiez"),
                0);

        ImgQuestion imgQuestion39 = new ImgQuestion("Tu as les cheveux longs. =>                        Tu ______ les cheveux longs.", R.drawable.cheveuxlongs,
                Arrays.asList("Étais", "Était", "Avais", "Avait"),
                2);

        ImgQuestion imgQuestion40 = new ImgQuestion("Mon chien à des puces. =>                               Mon chien ______ des puces.",R.drawable.chienpuces,
                Arrays.asList("Était", "Étais", "Avais", "Avait"),
                3);

        ImgQuestion imgQuestion41 = new ImgQuestion("Nous sommes de bonnes copines. =>                                        Nous ______ de bonnes copines.",R.drawable.bonnesamies,
                Arrays.asList("Avions", "Aviez", "Étions", "Étiez"),
                2);

        ImgQuestion imgQuestion42 = new ImgQuestion("Je suis un bon joueur de basket. =>                     J' ______ un bon joueur de basket.",R.drawable.basket,
                Arrays.asList("Avais", "Étais", "Était", "Avait"),
                1);

        ImgQuestion imgQuestion43 = new ImgQuestion("L'été dernier, nous ______ en vacances à la mer.",R.drawable.mer,
                Arrays.asList("Étions", "Étiez", "Avions", "Aviez"),
                0);

        ImgQuestion imgQuestion44 = new ImgQuestion("Hier soir, j'______ hâte d'aller me coucher.",R.drawable.reveiller,
                Arrays.asList("Était", "Avais", "Avait", "Étais"),
                1);

        ImgQuestion imgQuestion45 = new ImgQuestion("Hier, il ______ de la fièvre.", R.drawable.fievre,
                Arrays.asList("Était", "Étais", "Avait", "Avais"),
                2);

        ImgQuestion imgQuestion46 = new ImgQuestion("Autrefois, j'_______ un tricycle rouge.",R.drawable.tricycle,
                Arrays.asList("Étais", "Avais", "Était", "Avait"),
                1);

        ImgQuestion imgQuestion47 = new ImgQuestion(" L'an dernier, tu ______ déjà un bon judoka.",R.drawable.judoka,
                Arrays.asList("Avaient", "Étaient", "Avais", "Était"),
                3);

        ImgQuestion imgQuestion48 = new ImgQuestion("En ce temps là, vous ______ un jardin plein de fleurs.",R.drawable.brouette,
                Arrays.asList("Avais", "Aviez", "Était", "Étiez"),
                1);

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
                imgQuestion48
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