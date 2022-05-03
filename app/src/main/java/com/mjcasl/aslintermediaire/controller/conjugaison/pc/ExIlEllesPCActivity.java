package com.mjcasl.aslintermediaire.controller.conjugaison.pc;

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

public class ExIlEllesPCActivity extends AppCompatActivity implements View.OnClickListener {

    public static final long COUNTDOWN_IN_MILLIS = 11000;

    private TextView mpcQuestion;
    private Button mpcAnswer1;
    private Button mpcAnswer2;
    private Button mpcAnswer3;
    private Button mpcAnswer4;

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
        setContentView(R.layout.expcilelles);


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

        mpcQuestion = findViewById(R.id.pc_question_txt);
        mpcAnswer1 = findViewById(R.id.pc_answer1_btn);
        mpcAnswer2 = findViewById(R.id.pc_answer2_btn);
        mpcAnswer3 = findViewById(R.id.pc_answer3_btn);
        mpcAnswer4 = findViewById(R.id.pc_answer4_btn);

        mScoreDisplay = findViewById(R.id.pc_score);
        mNbrofQuestion = findViewById(R.id.questions_count);
        mProgressBar = findViewById(R.id.pc_progress_bar);

        // Use the tag property to 'name' the buttons
        mpcAnswer1.setTag(0);
        mpcAnswer2.setTag(1);
        mpcAnswer3.setTag(2);
        mpcAnswer4.setTag(3);

        mpcAnswer1.setOnClickListener(this);
        mpcAnswer2.setOnClickListener(this);
        mpcAnswer3.setOnClickListener(this);
        mpcAnswer4.setOnClickListener(this);

        mQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mQuestion);

        mQuestionTotal = 5;
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
        int taganswer1 = (int) mpcAnswer1.getTag();
        int taganswer2 = (int) mpcAnswer2.getTag();
        int taganswer3 = (int) mpcAnswer3.getTag();
        int taganswer4 = (int) mpcAnswer4.getTag();

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
                mpcAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }

            else if(taganswer2 == mQuestion.getAnswerIndex()){
                mpcAnswer2.setBackgroundColor(Color.parseColor("#008000"));;
            }

            else if(taganswer3 == mQuestion.getAnswerIndex()){
                mpcAnswer3.setBackgroundColor(Color.parseColor("#008000"));

            }

            else if(taganswer4 == mQuestion.getAnswerIndex()){
                mpcAnswer4.setBackgroundColor(Color.parseColor("#008000"));
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

                    mpcAnswer1.setBackgroundColor(Color.parseColor("#000000"));
                    mpcAnswer2.setBackgroundColor(Color.parseColor("#000000"));
                    mpcAnswer3.setBackgroundColor(Color.parseColor("#000000"));
                    mpcAnswer4.setBackgroundColor(Color.parseColor("#000000"));
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
        mpcQuestion.setText(Question.getQuestion());
        mpcAnswer1.setText(Question.getChoiceList().get(0));
        mpcAnswer2.setText(Question.getChoiceList().get(1));
        mpcAnswer3.setText(Question.getChoiceList().get(2));
        mpcAnswer4.setText(Question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question Question1 = new Question("Mon frère (aller) chez le dentiste ce matin.",
                Arrays.asList("est allais", "a aller", "est allé", "a allé"),
                2);

        Question Question2 = new Question(" Les voisins m' (dire) que le radiateur a chauffé toute la nuit.",
                Arrays.asList("a dit", "est dit", "ont dit", "sont dit"),
                2);

        Question Question3 = new Question(" Ils (visiter) des sites extraordinaires pendant ce séjour en Grèce.",
                Arrays.asList("est visité", "ont visité", "ont visités", "est visités"),
                1);

        Question Question4 = new Question("Quand Marie (naître), mes parents n'avaient que vingt ans.",
                Arrays.asList("est née", "est nait", "a née", "a nait"),
                0);

        Question Question5 = new Question("Connaissait-t-il les chansons qu'il (entendre) ?",
                Arrays.asList("as entendu", "a entendu", "est entendus", "est entendu"),
                1);

        Question Question6 = new Question("Delphine et Marinette (se regarder) en riant.",
                Arrays.asList("se sont regardées", "se ont regardé", "se a regardé", "se sont regardé"),
                0);

        Question Question7 = new Question("Les femmes qu'il (rencontrer) venaient toutes du Chili.",
                Arrays.asList("est rencontré", "a rencontré", "a rencontrer", "est rencontrer"),
                1);

        Question Question8 = new Question("Les deux petites filles (mourir) de rire.",
                Arrays.asList("sont mortes", "ont morts", "sont mort", "ont mortes"),
                0);

        Question Question9 = new Question("Il faudra faire examiner les champignons qu'il (cueillir).",
                Arrays.asList("a cueilli", "a cueillit", "est cueillit", "est cueilli"),
                0);

        Question Question10 = new Question("Les troupeaux de brebis (descendre) de la montagne.",
                Arrays.asList("ont descendus", "sont descendus", "est descendu", "a descendu"),
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
                Question10
        ));
    }
}

