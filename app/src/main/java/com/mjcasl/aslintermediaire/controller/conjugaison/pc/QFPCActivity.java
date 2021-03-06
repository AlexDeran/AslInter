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
            Toast toast = Toast.makeText(this, "Mauvaise r??ponse !",Toast.LENGTH_SHORT);
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
        builder.setTitle("Bien jou?? !")
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

        ImgQuestion imgQuestion1 = new ImgQuestion("Mon fr??re (aller) chez le dentiste ce matin.",R.drawable.logo_mjc,
                Arrays.asList("est allais", "a aller", "est all??", "a all??"),
                2);

        ImgQuestion imgQuestion2 = new ImgQuestion(" Les voisins m' (dire) que le radiateur a chauff?? toute la nuit.",R.drawable.logo_mjc,
                Arrays.asList("a dit", "est dit", "ont dit", "sont dit"),
                2);

        ImgQuestion imgQuestion3 = new ImgQuestion(" Ils (visiter) des sites extraordinaires pendant ce s??jour en Gr??ce.",R.drawable.logo_mjc,
                Arrays.asList("est visit??", "ont visit??", "ont visit??s", "est visit??s"),
                1);

        ImgQuestion imgQuestion4 = new ImgQuestion("Quand Marie (na??tre), mes parents n'avaient que vingt ans.",R.drawable.logo_mjc,
                Arrays.asList("est n??e", "est nait", "a n??e", "a nait"),
                0);

        ImgQuestion imgQuestion5 = new ImgQuestion("Connaissait-t-il les chansons qu'il (entendre) ?",R.drawable.logo_mjc,
                Arrays.asList("as entendu", "a entendu", "est entendus", "est entendu"),
                1);

        ImgQuestion imgQuestion6 = new ImgQuestion("Delphine et Marinette (se regarder) en riant.",R.drawable.logo_mjc,
                Arrays.asList("se sont regard??es", "se ont regard??", "se a regard??", "se sont regard??"),
                0);

        ImgQuestion imgQuestion7 = new ImgQuestion("Les femmes qu'il (rencontrer) venaient toutes du Chili.",R.drawable.logo_mjc,
                Arrays.asList("est rencontr??", "a rencontr??", "a rencontrer", "est rencontrer"),
                1);

        ImgQuestion imgQuestion8 = new ImgQuestion("Les deux petites filles (mourir) de rire.",R.drawable.logo_mjc,
                Arrays.asList("sont mortes", "ont morts", "sont mort", "ont mortes"),
                0);

        ImgQuestion imgQuestion9 = new ImgQuestion("Il faudra faire examiner les champignons qu'il (cueillir).",R.drawable.logo_mjc,
                Arrays.asList("a cueilli", "a cueillit", "est cueillit", "est cueilli"),
                0);

        ImgQuestion imgQuestion10 = new ImgQuestion("Les troupeaux de brebis (descendre) de la montagne.",R.drawable.logo_mjc,
                Arrays.asList("ont descendus", "sont descendus", "est descendu", "a descendu"),
                1);

        ImgQuestion imgQuestion11 = new ImgQuestion("Un jour j'(attraper) une grenouille", R.drawable.grenouille,
                Arrays.asList("est attrap??", "ai attrap??", "est attraper", "ai attraper"),
                1);

        ImgQuestion imgQuestion12 = new ImgQuestion("Tu (manger) toute la tarte !",R.drawable.mangertarte,
                Arrays.asList("a mang??", "est mang??", "as mang??", "est mang??"),
                2);

        ImgQuestion imgQuestion13 = new ImgQuestion("J'(envoyer) une lettre.",R.drawable.envoyerlettre,
                Arrays.asList("est envoy??", "ai envoy??", "est envoy??s", "ai envoy??s"),
                1);

        ImgQuestion imgQuestion14 = new ImgQuestion("Tu (gagner) la course ?? pied. ",R.drawable.coursepied,
                Arrays.asList("a gagn??", "est gagn??", "est gagn??s", "as gagn??"),
                3);

        ImgQuestion imgQuestion15 = new ImgQuestion("J'(acheter) du bon pain frais.",R.drawable.painachete,
                Arrays.asList("ai achet??", "est achet??", "ai achet??s", "est achet??s"),
                0);

        ImgQuestion imgQuestion16 = new ImgQuestion("Tu (couper) tes cheveux.",R.drawable.coupecheveux,
                Arrays.asList("as coup??", "est coup??", "as coup??s", "est coup??s"),
                0);

        ImgQuestion imgQuestion17 = new ImgQuestion("Je (montrer) en haut de la tour Eiffel.",R.drawable.toureiffel,
                Arrays.asList("est mont??", "ai mont??", "suis mont??", "a mont??"),
                2);

        ImgQuestion imgQuestion18 = new ImgQuestion("Tu (tomber) en rollers.",R.drawable.rollers,
                Arrays.asList("es tomber", "as tomb??", "es tomb??", "as tomber"),
                2);

        ImgQuestion imgQuestion19 = new ImgQuestion("Tu (regarder) les ??toiles cette nuit.",R.drawable.etoiles,
                Arrays.asList("as regard??", "as regarder", "est regarder", "es regard??"),
                0);

        ImgQuestion imgQuestion20 = new ImgQuestion("J'(avoir) de jolis jouets pour No??l.",R.drawable.jouets,
                Arrays.asList("est eu", "ai eu", "es eu", "a eu"),
                1);

        ImgQuestion imgQuestion21 = new ImgQuestion("Vous (chercher) vos cl??s partout.", R.drawable.cles,
                Arrays.asList("avais cherch??", "avez cherch??", "??tes cherch??", "??tes chercher"),
                1);

        ImgQuestion imgQuestion22 = new ImgQuestion("Nous (pr??parer) nos bagages.",R.drawable.bagages,
                Arrays.asList("avez pr??parer", "avons pr??par??", "sommes pr??par??s", "sommes pr??parer"),
                1);

        ImgQuestion imgQuestion23 = new ImgQuestion("Nous (danser) toute la nuit.",R.drawable.dansernuit,
                Arrays.asList("avez dans??", "sommes dans??", "avons dans??", "??tes dans??"),
                2);

        ImgQuestion imgQuestion24 = new ImgQuestion("Vous (balayer) la classe.",R.drawable.balayer,
                Arrays.asList("avons balayer", "sommes balay??", "avez balay??", "??tes balay??"),
                2);

        ImgQuestion imgQuestion25 = new ImgQuestion("Nous (pr??parer) un g??teau.",R.drawable.gateau,
                Arrays.asList("avez pr??parer", "avons pr??par??", "sommes pr??par??", "??tes pr??parer"),
                1);

        ImgQuestion imgQuestion26 = new ImgQuestion("Vous (tricoter) un pull.",R.drawable.tricoter,
                Arrays.asList("avez tricot??", "avons tricot??", "sommes tricot??", "??tes tricot??"),
                0);

        ImgQuestion imgQuestion27 = new ImgQuestion("Vous (aller) ?? la montagne.",R.drawable.montagnevs,
                Arrays.asList("sommes all??s", "avons all??", "??tes all??s", "avez all??"),
                2);

        ImgQuestion imgQuestion28 = new ImgQuestion("Vous (aimer) aller au cin??ma.",R.drawable.cinema,
                Arrays.asList("avons aim??", "??tes aim??s", "avez aim??", "sommes aim??s"),
                2);

        ImgQuestion imgQuestion29 = new ImgQuestion("Vous (avoir) peur de la sorci??re.",R.drawable.sorciere,
                Arrays.asList("avons eu", "avez eu", "sommes eu", "??tes eu"),
                1);

        ImgQuestion imgQuestion30 = new ImgQuestion("Nous (acheter) un poisson rouge.",R.drawable.poissonrouge,
                Arrays.asList("sommes achet??", "??tes achet??", "avons achet??", "avez achet??"),
                2);

        ImgQuestion imgQuestion31 = new ImgQuestion("Tu (??tre) une vraie championne.", R.drawable.championnepc,
                Arrays.asList("a ??t??", "est ??t??", "as ??t??", "es ??t??"),
                2);

        ImgQuestion imgQuestion32 = new ImgQuestion("Mon chien (avoir) une visite m??dicale.",R.drawable.visitemedicale,
                Arrays.asList("a eu", "as eu", "est eu", " es eu"),
                0);

        ImgQuestion imgQuestion33 = new ImgQuestion("Mes parents (??tre) de grands lecteurs.",R.drawable.bibliolecteurs,
                Arrays.asList("a ??t??", "sont ??t??", "ont ??t??", "as ??t??"),
                2);

        ImgQuestion imgQuestion34 = new ImgQuestion("Nous (??tre) de vrais fermiers !",R.drawable.ferme,
                Arrays.asList("avons ??t??", "sommes ??t??", "avez ??t??", "??tes ??t??"),
                0);

        ImgQuestion imgQuestion35 = new ImgQuestion("J'(avoir) une souris blanche.",R.drawable.sourisblanche,
                Arrays.asList("es eu", "ai eu", "est eu", "ais eu"),
                1);

        ImgQuestion imgQuestion36 = new ImgQuestion("Vous (avoir) des crayons de couleurs.",R.drawable.crayonscouleurs,
                Arrays.asList("avez eu", "avons eu", "sommes eu", "??tes eu"),
                0);

        ImgQuestion imgQuestion37 = new ImgQuestion("Nous (avoir) une grande maison.",R.drawable.maisonverte,
                Arrays.asList("sommes eu", "avons eu", "??tes eu", "avez eu"),
                1);

        ImgQuestion imgQuestion38 = new ImgQuestion("Mon oncle (??tre) un chanteur connu.",R.drawable.chanteur,
                Arrays.asList("est ??t??", "a ??t??", "as ??t??", "es ??t??"),
                1);

        ImgQuestion imgQuestion39 = new ImgQuestion("Vous (avoir) peur de la sorci??re.",R.drawable.sorciere,
                Arrays.asList("avons eu", "avez eu", "sommes eu", "??tes eu"),
                1);

        ImgQuestion imgQuestion40 = new ImgQuestion("Tu (??tre) le gardien de but.",R.drawable.gardienbut,
                Arrays.asList("a ??t??", "est ??t??", "es ??t??", "as ??t??"),
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
                imgQuestion40
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