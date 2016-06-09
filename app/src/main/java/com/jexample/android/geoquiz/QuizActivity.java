package com.jexample.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Logger;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;

    private TextView mQuestionTextView;

    private Question[] mQuestionBlank = new Question[]{
            new Question(true, R.string.question_paris),
            new Question(false, R.string.question_madagascar),
            new Question(true, R.string.question_nizhnevartovsk)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logger.getAnonymousLogger().info("Hello!");
                checkAnsver(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logger.getLogger("").info("Ta Da");
                checkAnsver(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBlank.length;

                updateQuestion();
            }
        });


        // first update
        updateQuestion();

    }

    private void updateQuestion() {
        int questionResId = mQuestionBlank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(questionResId);

    }

    private void checkAnsver(boolean answerUser) {
        Question question = mQuestionBlank[mCurrentIndex];
        int checkAnswerId;
        if (question.isAnswerTrue() == answerUser) {
            checkAnswerId = R.string.correct_toast;
        } else {
            checkAnswerId = R.string.uncorrect_toast;
        }

        Toast.makeText(QuizActivity.this, checkAnswerId, Toast.LENGTH_SHORT).show();
    }
}
