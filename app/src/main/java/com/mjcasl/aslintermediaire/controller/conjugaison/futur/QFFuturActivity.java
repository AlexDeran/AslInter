package com.mjcasl.aslintermediaire.controller.conjugaison.futur;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.Locale;

public class QFFuturActivity extends AppCompatActivity implements View.OnClickListener {

    public static final long COUNTDOWN_IN_MILLIS = 21000;

    private TextView mfuturQuestion;
    private Button mfuturAnswer1;
    private Button mfuturAnswer2;
    private Button mfuturAnswer3;
    private Button mfuturAnswer4;

    private TextView mScoreDisplay;
    private TextView mNbrofQuestion;
    private TextView mCountDown;;
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


    private ColorStateList CountDownColor;
    private CountDownTimer mCountDownTimer;
    private long timeLeftInMillis;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qffutur);


        mQuestionBank = this.generateQuestions();
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

        mfuturQuestion = findViewById(R.id.futur_question_txt);
        mfuturAnswer1 = findViewById(R.id.futur_answer1_btn);
        mfuturAnswer2 = findViewById(R.id.futur_answer2_btn);
        mfuturAnswer3 = findViewById(R.id.futur_answer3_btn);
        mfuturAnswer4 = findViewById(R.id.futur_answer4_btn);

        mScoreDisplay = findViewById(R.id.futur_score);
        mNbrofQuestion = findViewById(R.id.questions_count);
        mCountDown = findViewById(R.id.futur_question_timer);
        mProgressBar = findViewById(R.id.futur_progress_bar);

        // Use the tag property to 'name' the buttons
        mfuturAnswer1.setTag(0);
        mfuturAnswer2.setTag(1);
        mfuturAnswer3.setTag(2);
        mfuturAnswer4.setTag(3);

        mfuturAnswer1.setOnClickListener(this);
        mfuturAnswer2.setOnClickListener(this);
        mfuturAnswer3.setOnClickListener(this);
        mfuturAnswer4.setOnClickListener(this);

        mQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mQuestion);

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)


    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();
        int taganswer1 = (int) mfuturAnswer1.getTag();
        int taganswer2 = (int) mfuturAnswer2.getTag();
        int taganswer3 = (int) mfuturAnswer3.getTag();
        int taganswer4 = (int) mfuturAnswer4.getTag();

        mCountDownTimer.cancel();

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
                mfuturAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }

            else if(taganswer2 == mQuestion.getAnswerIndex()){
                mfuturAnswer2.setBackgroundColor(Color.parseColor("#008000"));;
            }

            else if(taganswer3 == mQuestion.getAnswerIndex()){
                mfuturAnswer3.setBackgroundColor(Color.parseColor("#008000"));

            }

            else if(taganswer4 == mQuestion.getAnswerIndex()){
                mfuturAnswer4.setBackgroundColor(Color.parseColor("#008000"));
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

                    mQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mQuestion);
                    mQuestionCounter++;

                    mScoreDisplay.setText("Score : " + mScore);
                    mNbrofQuestion.setText(mQuestionCounter + "/20");

                    mProgressBar.setProgress(mQuestionCounter);

                    mfuturAnswer1.setBackgroundColor(Color.parseColor("#000000"));
                    mfuturAnswer2.setBackgroundColor(Color.parseColor("#000000"));
                    mfuturAnswer3.setBackgroundColor(Color.parseColor("#000000"));
                    mfuturAnswer4.setBackgroundColor(Color.parseColor("#000000"));
                }
            }
        }, 2000);
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
                    mQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mQuestion);
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

    private void displayQuestion(final Question Question) {
        mfuturQuestion.setText(Question.getQuestion());
        mfuturAnswer1.setText(Question.getChoiceList().get(0));
        mfuturAnswer2.setText(Question.getChoiceList().get(1));
        mfuturAnswer3.setText(Question.getChoiceList().get(2));
        mfuturAnswer4.setText(Question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question Question1 = new Question("Je (dormir) chez ma tante le soir du mariage.",
                Arrays.asList("dormiras", "dormirai", "dormirons", "dormira"),
                1);

        Question Question2 = new Question(" Tu (courir) de plus en plus vite si tu t'entraînes.",
                Arrays.asList("courras", "courrai", "courrons", "courrez"),
                0);

        Question Question3 = new Question("Il (mourir) d'inquiétude si tu ne lui téléphones pas.",
                Arrays.asList("mourra", "mourrai", "mourras", "mourons"),
                0);

        Question Question4 = new Question("Nous (savoir) de quoi il retourne dans un instant.",
                Arrays.asList("sauras", "sauront", "saurons", "saurez"),
                2);

        Question Question5 = new Question("Vous (devoir) recommencer le traitement dès ce soir.",
                Arrays.asList("devrez ", "devrons", "devra", "devrai"),
                0);

        Question Question6 = new Question("Ils (pouvoir) revenir mercredi s'ils le désirent.",
                Arrays.asList("pourra", "pourront", "pourrez", "pourrai"),
                1);

        Question Question7 = new Question("Vous (rendre) compte de vos actions à vos moniteurs.",
                Arrays.asList("rendront", "rendrez", "rendra", "rendrons"),
                1);

        Question Question8 = new Question("Nous (prendre) la main des enfants pour traverser la rue.",
                Arrays.asList("prendrons", "prendrez", "prendrai ", "prendra"),
                0);

        Question Question9 = new Question("Il (faire) de son mieux mais je ne crois pas qu'il y parvienne.",
                Arrays.asList("ferons", "fera", "ferai", "feront"),
                1);

        Question Question10 = new Question("Tu (mettre) la table à 7 H, le repas sera prêt.",
                Arrays.asList("mettra", "mettras", "mettrons", "mettrai"),
                1);

        Question Question11 = new Question("J'(avoir) une bonne note à mon contrôle.",
                Arrays.asList("avais", "ai", "aurai", "avez"),
                2);

        Question Question12 = new Question("Tu (être) derrière moi lorsque je le rencontrerai.",
                Arrays.asList("est", "était", "seras", "êtez"),
                2);

        Question Question13 = new Question("Il (aimer) le film et te le conseillera, j'en suis certain.",
                Arrays.asList("aimait", "aimé", "aime", "aimera"),
                3);

        Question Question14 = new Question("Nous (placer) la chaise à droite de la table.",
                Arrays.asList("placera", "placerai", "placerons", "placerez"),
                2);

        Question Question15 = new Question("Vous (manger) des tomates lorsque ce sera la saison.",
                Arrays.asList("mangeait ", "mangez", "mangerez", "mangerai"),
                2);

        Question Question16 = new Question("Ils (peser) Nathan chez le pédiatre.",
                Arrays.asList("pesais", "pèseront", "pèsera", "pesèrent"),
                1);

        Question Question17 = new Question("Vous (céder) devant la menace.",
                Arrays.asList("céderez", "cédez", "céderons", "cédons"),
                0);

        Question Question18 = new Question("Nous (jeter) l'éponge pour cette fois-ci.",
                Arrays.asList("jetterons", "jettons", "jetez ", "jetterez"),
                0);

        Question Question19 = new Question("Il (modeler) la statuette avant de la mettre au four.",
                Arrays.asList("modèlera", "modèleront", "modelons", "modelez"),
                0);

        Question Question20 = new Question("Tu (créer) un lien amical avec eux, crois-moi.",
                Arrays.asList("créas", "créeras", "créé", "créras"),
                1);

        Question Question21 = new Question("J'(assiéger) son bureau jusqu'à ce que j'obtienne satisfaction.",
                Arrays.asList("assiégerai", "assiégerais", "assiégerez", "assiégerer"),
                0);

        Question Question22 = new Question("Tu (apprécier) le repos à venir",
                Arrays.asList("appréciera", "apprécierai", "apprécieras", "apprécierez"),
                2);

        Question Question23 = new Question("Il (payer) ses dettes avant de partir en vacances.",
                Arrays.asList("paiera", "paieras", "payeras", "payera"),
                0);

        Question Question24 = new Question("Nous (broyer) les feuilles mortes et les composterons.",
                Arrays.asList("broierai", "broierons", "broieras", "broierez"),
                1);

        Question Question25 = new Question("Ils (finir) leur repas et pourront aller jouer.",
                Arrays.asList("finira ", "finiras", "finirai", "finiront"),
                3);

        Question Question26 = new Question("Vous (envoyer) cette lettre demain avant la levée.",
                Arrays.asList("enverrai", "enverrais", "enverrez", "enverrer"),
                2);

        Question Question27 = new Question("Vous (haïr) cette maison comme je l'ai haïe.",
                Arrays.asList("haïrons", "haïrez", "haïrai", "haïrais"),
                1);

        Question Question28 = new Question("Nous (aller) lui rendre visite dans l'après-midi.",
                Arrays.asList("irons", "irez", "ira ", "iras"),
                0);

        Question Question29 = new Question("Il (tenir) tête, j'en suis sûr.",
                Arrays.asList("tiendrons", "tiendrez", "tiendra", "itendrai"),
                2);

        Question Question30 = new Question("Tu (acquérir) ce bien plus tard, sois patient.",
                Arrays.asList("acquerrai", "acquerras", "acquerrons", "acquerrez"),
                1);

        Question Question31 = new Question("Je (sentir) un grand manque après ton départ.",
                Arrays.asList("sentirais", "sentirai", "sentiras", "sentira"),
                1);

        Question Question32 = new Question("Tu (vêtir) cette poupée des habits que tu as cousus.",
                Arrays.asList("vêtirais", "vêtiras", "vêtira", "vêtirai"),
                1);

        Question Question33 = new Question("Il (couvrir) le livre avant la rentrée.",
                Arrays.asList("couvrirai", "couvrira", "couvriras", "couvrirez"),
                1);

        Question Question34 = new Question("Nous (cueillir) des marguerites dans le champ voisin.",
                Arrays.asList("cueillera", "cueillerai", "cueillerons", "cueillerez"),
                2);

        Question Question35 = new Question("Vous (cuire) les pâtes à feu doux.",
                Arrays.asList("cuirez ", "cuiras", "cuirons", "cuira"),
                0);

        Question Question36 = new Question("Ils (écrire) une lettre de motivation.",
                Arrays.asList("écrirai", "écriras", "écriront", "écrirez"),
                2);

        Question Question37 = new Question("Vous (rire) plus tard, maintenant il faut réviser.",
                Arrays.asList("rirons", "rirez", "rira", "rirai"),
                1);

        Question Question38 = new Question("Nous (dire) la vérité avec simplicité.",
                Arrays.asList("dirons", "direz", "dira ", "diras"),
                0);

        Question Question39 = new Question("Il (lire) ce journal demain, tant pis !",
                Arrays.asList("lirons", "lirez", "lira", "lirai"),
                2);

        Question Question40 = new Question("Tu (voir) bien ce qui se passera ensuite.",
                Arrays.asList("verrons", "verras", "verrai", "verrez"),
                1);

        Question Question41 = new Question("Peut-être que demain vous ______ du courrier.",
                Arrays.asList("avez", "aurez", "avait", "aurait"),
                1);

        Question Question42 = new Question(" Si tu te déguise en monstre, il ______ peur.",
                Arrays.asList("aurez", "aura", "sera", "seront"),
                1);

        Question Question43 = new Question("Pour mon anniversaire, j'______ un petit chat.",
                Arrays.asList("aurez", "aurai", "sera", "serez"),
                1);

        Question Question44 = new Question("Cet été, ils ______ en vacances à la mer.",
                Arrays.asList("aura", "auront", "seront", "sera"),
                2);

        Question Question45 = new Question("Je t'appelerai quand nous ______ à Paris.",
                Arrays.asList("avons ", "êtes", "serons", "étions"),
                2);

        Question Question46 = new Question("Plus tard, tu ______ une chanteuse célèbre.",
                Arrays.asList("sera", "seras", "aura", "auras"),
                1);

        Question Question47 = new Question("Nous ______ une glace pour le dessert.",
                Arrays.asList("avont", "étions", "aurons", "serons"),
                2);

        Question Question48 = new Question("Le train ______ là dans quelques minutes.",
                Arrays.asList("seras", "était", "seront ", "sera"),
                3);

        Question Question49 = new Question("Demain, j'______ des nouvelles lunettes.",
                Arrays.asList("ai", "avais", "aurai", "allais"),
                2);

        Question Question50 = new Question("Vous ______ le droit de jouer après l'école.",
                Arrays.asList("aurer", "aurait", "aurais", "aurez"),
                3);

        Question Question51 = new Question("Je crois que tu ______ le vainqueur de la course.",
                Arrays.asList("est", "es", "sera", "seras"),
                3);

        Question Question52 = new Question("Tes chaussettes ______ bientôt sèches.",
                Arrays.asList("êtes", "serons", "seront", "sera"),
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
                Question10,
                Question11,
                Question12,
                Question13,
                Question14,
                Question15,
                Question16,
                Question17,
                Question18,
                Question19,
                Question20,
                Question21,
                Question22,
                Question23,
                Question24,
                Question25,
                Question26,
                Question27,
                Question28,
                Question29,
                Question30,
                Question31,
                Question32,
                Question33,
                Question34,
                Question35,
                Question36,
                Question37,
                Question38,
                Question39,
                Question40,
                Question41,
                Question42,
                Question43,
                Question44,
                Question45,
                Question46,
                Question47,
                Question48,
                Question49,
                Question50,
                Question51,
                Question52
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

