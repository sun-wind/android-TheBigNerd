package com.jexample.android.geoquiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;


public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";

    private static final String CURRENT_INDEX = "index";
    private static final String CURRENT_CHEATS = "cheatsIndex";

    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private final Question[] mQuestionBlank = new Question[]{
            new Question(true, R.string.question_paris),
            new Question(false, R.string.question_madagascar),
            new Question(true, R.string.question_nizhnevartovsk)
    };

    private int mCurrentIndex = 0;
    @Deprecated
    private boolean isCheater;
    private ArrayList<Integer> cheatedIndexes = new ArrayList<>();

   // @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, ">> Create");

        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);

        /* restore data from rotate*/
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(CURRENT_INDEX, REQUEST_CODE_CHEAT);
            cheatedIndexes = savedInstanceState.getIntegerArrayList(CURRENT_CHEATS);
//            isCheater = savedInstanceState.getBoolean(CURRENT_CHEAT, false);
        }

        /* listeners */
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        View.OnClickListener listenerNext = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBlank.length;
                updateQuestion();
//                isCheater = false;
            }
        };

        mNextButton.setOnClickListener(listenerNext);
        mQuestionTextView.setOnClickListener(listenerNext);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCurrentIndex == 0) {
                    Toast.makeText(QuizActivity.this, "This is First Question!", Toast.LENGTH_SHORT).show();
                } else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBlank.length;
                    updateQuestion();
                }
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBlank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        // first update
        updateQuestion();

    }

    /**
     * Handle Back from Child Activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, ">> Result Code " + resultCode);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            boolean isCheater = CheatActivity.wasAnswerShown(data);
            if(isCheater) {
                cheatedIndexes.add(mCurrentIndex);
            }
            //Log.d(TAG, ">> Was Answer Shown: " + isCheater);
        }
    }

    /**
     * Save data from rotate
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, ">> On Save Instance <<");
//        outState.putBoolean(CURRENT_CHEAT, isCheater);
        outState.putIntegerArrayList(CURRENT_CHEATS, cheatedIndexes);
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
        Log.d(TAG, ">> Destroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, ">> Resume");
    }

    private void updateQuestion() {
        //    Log.d(TAG, "UpdateQuestion index="+mCurrentIndex, new Exception());
        Question q = null;
        try {
            q = mQuestionBlank[mCurrentIndex];
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "Index was out");
        }
        int questionResId = q.getTextResId();
        mQuestionTextView.setText(questionResId);

    }

    private void checkAnswer(boolean userPressed) {

        int checkAnswerId;
        if (cheatedIndexes.contains(mCurrentIndex)) {
            checkAnswerId = R.string.judgment_toast;
        } else {
            Question question = mQuestionBlank[mCurrentIndex];
            if (question.isAnswerTrue() == userPressed) {
                checkAnswerId = R.string.correct_toast;
            } else {
                checkAnswerId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(QuizActivity.this, checkAnswerId, Toast.LENGTH_SHORT).show();
    }
}
