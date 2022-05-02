package com.mjcasl.aslintermediaire.controller.conjugaison.present;

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

public class QFPREActivity extends AppCompatActivity implements View.OnClickListener {

    public static final long COUNTDOWN_IN_MILLIS = 21000;

    private TextView mpreQuestion;
    private Button mpreAnswer1;
    private Button mpreAnswer2;
    private Button mpreAnswer3;
    private Button mpreAnswer4;
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
        setContentView(R.layout.qfpresent);

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

        mpreQuestion = findViewById(R.id.pre_question_txt);
        mImage = findViewById(R.id.pre_image);
        mpreAnswer1 = findViewById(R.id.pre_answer1_btn);
        mpreAnswer2 = findViewById(R.id.pre_answer2_btn);
        mpreAnswer3 = findViewById(R.id.pre_answer3_btn);
        mpreAnswer4 = findViewById(R.id.pre_answer4_btn);

        mScoreDisplay = findViewById(R.id.pre_score);
        mNbrofQuestion = findViewById(R.id.questions_count);
        mCountDown = findViewById(R.id.pre_question_timer);
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

        int taganswer1 = (int) mpreAnswer1.getTag();
        int taganswer2 = (int) mpreAnswer2.getTag();
        int taganswer3 = (int) mpreAnswer3.getTag();
        int taganswer4 = (int) mpreAnswer4.getTag();

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

                    mpreAnswer1.setBackgroundColor(Color.parseColor("#000000"));
                    mpreAnswer2.setBackgroundColor(Color.parseColor("#000000"));
                    mpreAnswer3.setBackgroundColor(Color.parseColor("#000000"));
                    mpreAnswer4.setBackgroundColor(Color.parseColor("#000000"));
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
        mpreQuestion.setText(imgQuestion.getImgQuestion());
        mImage.setImageResource(imgQuestion.getImage());
        mpreAnswer1.setText(imgQuestion.getChoiceList().get(0));
        mpreAnswer2.setText(imgQuestion.getChoiceList().get(1));
        mpreAnswer3.setText(imgQuestion.getChoiceList().get(2));
        mpreAnswer4.setText(imgQuestion.getChoiceList().get(3));
    }
    private ImgBank generateQuestions() {

        ImgQuestion imgQuestion1 = new ImgQuestion("Vous (discuter) autour de la table.", R.drawable.discuter,
                Arrays.asList("Discute", "Discutent", "Discutez", "Discutons"),
                2);

        ImgQuestion imgQuestion2 = new ImgQuestion("Les feuilles (tomber) de l'arbre.",R.drawable.feuillestombent,
                Arrays.asList("Tombes", "Tombent", "Tombons", "Tombe"),
                1);

        ImgQuestion imgQuestion3 = new ImgQuestion("Tu (préparer) un gâteau.",R.drawable.amelie,
                Arrays.asList("Prépares", "Prépare", "Préparent", "Préparez"),
                0);

        ImgQuestion imgQuestion4 = new ImgQuestion("Nous (regarder) la télévision.",R.drawable.television,
                Arrays.asList("Regardes", "Regardons", "Regardez", "Regardent"),
                1);

        ImgQuestion imgQuestion5 = new ImgQuestion("Le Père Noël (apporter) des cadeaux.",R.drawable.perenoel,
                Arrays.asList("Apporte", "Apportent", "Apportes", "Apportons"),
                0);

        ImgQuestion imgQuestion6 = new ImgQuestion("Les fleurs (pousser) au printemps",R.drawable.fleurs,
                Arrays.asList("Poussent", "Pousse", "Poussons", "Poussez"),
                0);

        ImgQuestion imgQuestion7 = new ImgQuestion(" Nous (acheter) des bonbons.",R.drawable.bonbons,
                Arrays.asList("Achetons", "Achètes", "Achètent", "Achetez"),
                0);

        ImgQuestion imgQuestion8 = new ImgQuestion("Vous (jouer) aux cartes.",R.drawable.cartes,
                Arrays.asList("Joue", "Joues", "Jouons", "Jouez"),
                3);

        ImgQuestion imgQuestion9 = new ImgQuestion("Mes amis (arriver) à l'école",R.drawable.amis,
                Arrays.asList("Arrive", "Arrivent", "Arrivons", "Arrivez"),
                1);

        ImgQuestion imgQuestion10 = new ImgQuestion("Tu (chanter) trop fort.",R.drawable.chanteur,
                Arrays.asList("Chantons", "Chante", "Chantes", "Chantez"),
                2);

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

        ImgQuestion imgQuestion16 = new ImgQuestion("Tu ______ à l'école.", R.drawable.ecole2,
                Arrays.asList("Va", "Vas", "Vont", "Vais"),
                1);

        ImgQuestion imgQuestion17 = new ImgQuestion("Vous ______ à la montagne.",R.drawable.montagne,
                Arrays.asList("Allons", "Vont", "Allez", "Va"),
                2);

        ImgQuestion imgQuestion18 = new ImgQuestion("Ils ______ au cinéma.",R.drawable.cinema,
                Arrays.asList("Vont", "Allez", "Allons", "Vais"),
                0);

        ImgQuestion imgQuestion19 = new ImgQuestion("Nous ______ au restaurant.",R.drawable.restaurant,
                Arrays.asList("Vais", "Va", "Vas", "Allons"),
                3);

        ImgQuestion imgQuestion20 = new ImgQuestion("Il ______ dans la fôret.",R.drawable.foret,
                Arrays.asList("Vont", "Va", "Allez", "Allons"),
                1);

        ImgQuestion imgQuestion21 = new ImgQuestion("Je ______ au marché.",R.drawable.marche,
                Arrays.asList("Vais", "Va", "Vont", "Allez"),
                0);

        ImgQuestion imgQuestion22 = new ImgQuestion("Sam ______ à la piscine le dimanche.",R.drawable.piscine,
                Arrays.asList("Vont", "Allons", "Va", "Vais"),
                2);

        ImgQuestion imgQuestion23 = new ImgQuestion("Tu ______ trop vite avec ton vélo.",R.drawable.velo,
                Arrays.asList("Vont", "Vais", "Allons", "Vas"),
                3);

        ImgQuestion imgQuestion24 = new ImgQuestion("Ce soir, nous ______ à la patinoire",R.drawable.patinoire,
                Arrays.asList("Allons", "Vais", "Allez", "Vont"),
                0);

        ImgQuestion imgQuestion25 = new ImgQuestion("Les élèves ______ au zoo avec leur classe.",R.drawable.zoo,
                Arrays.asList("Vais", "Vont", "Allons", "Va"),
                1);

        ImgQuestion imgQuestion26 = new ImgQuestion("Quel est le pronom personnel sujet ?",R.drawable.logo_mjc,
                Arrays.asList("Mon", "Pour", "Sur", "Elles"),
                3);

        ImgQuestion imgQuestion27 = new ImgQuestion("Vous ______ perdu, Monsieur ?",R.drawable.perdu,
                Arrays.asList("Avez", "A", "Êtes", "Est"),
                2);

        ImgQuestion imgQuestion28 = new ImgQuestion("Nous ______ des jumelles.",R.drawable.jumelles,
                Arrays.asList("Avez", "Avons", "Êtes", "Sommes"),
                3);

        ImgQuestion imgQuestion29 = new ImgQuestion("Les étoiles ______ loin dans le ciel.",R.drawable.etoiles,
                Arrays.asList("Est", "As", "Sont", "Ont"),
                2);

        ImgQuestion imgQuestion30 = new ImgQuestion("Le lionceau ______ le petit du lion.",R.drawable.lionceau,
                Arrays.asList("A", "As", "Es", "Est"),
                3);

        ImgQuestion imgQuestion31 = new ImgQuestion("Nous ______ un train à prendre.",R.drawable.train,
                Arrays.asList("Est", "Ont", "Avons", "Sommes"),
                2);


        ImgQuestion imgQuestion32 = new ImgQuestion("______ félicitons le gagnant de la course.",R.drawable.logo_mjc,
                Arrays.asList("Je", "Nous", "Tu", "Il"),
                1);

        ImgQuestion imgQuestion33 = new ImgQuestion("______ adore les dauphins et Harry Potter.",R.drawable.logo_mjc,
                Arrays.asList("Nous", "Vous", "Ils", "J'"),
                3);

        ImgQuestion imgQuestion34 = new ImgQuestion("______ prennent place sur la ligne de départ.",R.drawable.logo_mjc,
                Arrays.asList("Nous", "Il", "Tu", "Ils"),
                3);

        ImgQuestion imgQuestion35 = new ImgQuestion("______ applaudis tous les participants.",R.drawable.logo_mjc,
                Arrays.asList("J'", "Vous", "Elle", "Nous"),
                0);

        ImgQuestion imgQuestion36 = new ImgQuestion("______ poursuivez les prisonniers qui se sont échappés.",R.drawable.logo_mjc,
                Arrays.asList("Je", "Tu", "Nous", "Vous"),
                3);

        ImgQuestion imgQuestion37 = new ImgQuestion("______ pratiquent la gymnastique depuis plusieurs années.",R.drawable.logo_mjc,
                Arrays.asList("Ils", "Je", "Nous", "Elle"),
                0);

        ImgQuestion imgQuestion38 = new ImgQuestion("______ accordes beaucoup trop d'importance à cette nouvelle.",R.drawable.logo_mjc,
                Arrays.asList("Je", "Nous", "Tu", "Ils"),
                2);

        ImgQuestion imgQuestion39 = new ImgQuestion("______ fleurit au printemps.",R.drawable.logo_mjc,
                Arrays.asList("Les capucines", "Le lilas", "Les violettes", "Les roses"),
                1);

        ImgQuestion imgQuestion40 = new ImgQuestion("______ crient très fort quand ______ jouent.",R.drawable.logo_mjc,
                Arrays.asList("Il", "Ils", "Je", "Vous"),
                1);

        ImgQuestion imgQuestion41 = new ImgQuestion("______ peux participer à cette compétition car j'ai enfin l'âge nécessaire.",R.drawable.logo_mjc,
                Arrays.asList("Il", "Ils", "Je", "Vous"),
                2);

        ImgQuestion imgQuestion42 = new ImgQuestion("Je ______ que tu sortes le chien chaque soir.(vouloir)",R.drawable.logo_mjc,
                Arrays.asList("Veux", "Veut", "Voulons", "Veulent"),
                0);

        ImgQuestion imgQuestion43 = new ImgQuestion("Ne dis pas que tu ne ______ pas le faire.(pouvoir)",R.drawable.logo_mjc,
                Arrays.asList("Peut", "Peux", "Peuvent", "Pouvons"),
                1);

        ImgQuestion imgQuestion44 = new ImgQuestion("Cet élève ______ apprendre à être autonome.(devoir)",R.drawable.logo_mjc,
                Arrays.asList("Doivent", "Devons", "Doit", "Dois"),
                2);

        ImgQuestion imgQuestion45 = new ImgQuestion("Nous ______ offrir un voyage à nos parents.(vouloir)",R.drawable.logo_mjc,
                Arrays.asList("Veut'", "Veux", "Voulez", "Voulons"),
                3);

        ImgQuestion imgQuestion46 = new ImgQuestion("Comment ______-vous exiger un entraînement aussi épuisant ? (pouvoir)",R.drawable.logo_mjc,
                Arrays.asList("Pouvez", "Pouvons", "Peux", "Peuvent"),
                0);

        ImgQuestion imgQuestion47 = new ImgQuestion("______-vous des légumes et des fruits ? (vouloir)",R.drawable.logo_mjc,
                Arrays.asList("Voulons", "Voulez", "Veulent", "Veux"),
                1);

        ImgQuestion imgQuestion48 = new ImgQuestion("Vous ______ terminer ce travail avant de sortir. (devoir)",R.drawable.logo_mjc,
                Arrays.asList("Devons", "Dois", "Devez", "Doivent"),
                2);

        ImgQuestion imgQuestion49 = new ImgQuestion("Le directeur ______ vous voir dans son bureau. (vouloir)",R.drawable.logo_mjc,
                Arrays.asList("Veulent", "Voulons", "Veux", "Veut"),
                3);

        ImgQuestion imgQuestion50 = new ImgQuestion("Il m'est impossible de venir cet après-midi, je ______ aller chez le dentiste. (devoir)",R.drawable.logo_mjc,
                Arrays.asList("Dois", "Doivent", "Devont", "Devez"),
                0);

        ImgQuestion imgQuestion51 = new ImgQuestion("Les chasseurs ne ______ pas tuer le gibier toute l'année.(pouvoir)",R.drawable.logo_mjc,
                Arrays.asList("Peut", "Peuvent", "Pouvons", "Pouvez"),
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