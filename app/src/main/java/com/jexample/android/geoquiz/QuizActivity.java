package com.jexample.android.geoquiz;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;


public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";

    private static final String CURRENT_INDEX = "index";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;

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
        Log.d(TAG, ">> Create");

        setContentView(R.layout.activity_quiz);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(CURRENT_INDEX, 0);
        }
        View.OnClickListener listenerNext = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBlank.length;
                updateQuestion();
            }
        };

        mNextButton.setOnClickListener(listenerNext);
        mQuestionTextView.setOnClickListener(listenerNext);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCurrentIndex == 0) {
                    Toast.makeText(QuizActivity.this, "This is First Question!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBlank.length;
                    updateQuestion();
                }
            }
        });

        // first update
        updateQuestion();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, ">> On Save Inastance <<");
        outState.putInt(CURRENT_INDEX, mCurrentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, ">> On Start");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, ">> Restart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, ">> Stop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, ">> Pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, ">> Destroyyyy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, ">> Resume");
    }

    private void updateQuestion() {
        Question q = null;
        try {
            q = mQuestionBlank[mCurrentIndex];
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "Index was out");
        }
        int questionResId = q.getTextResId();
        mQuestionTextView.setText(questionResId);

    }

    private void checkAnswer(boolean answerUser) {
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
