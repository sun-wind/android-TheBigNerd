package com.jexample.android.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.jexample.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.jexample.android.geoquiz.answer_shown";

    private TextView mAnswerTextView;
    private Button mAnswerButton;

    private boolean mAnswerIsTrue;

    /**
     * Decode answer
     *
     * @param intent
     * @return
     */
    public static boolean wasAnswerShown(Intent intent){
        return intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mAnswerButton = (Button) findViewById(R.id.show_answer_button);

        mAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }

                setAnswerToParentActivity(true);
            }
        });
    }


    private void setAnswerToParentActivity(boolean isAnswerWasShown) {
        Intent intentBack = new Intent();
        intentBack.putExtra(EXTRA_ANSWER_SHOWN, isAnswerWasShown);
        setResult(Activity.RESULT_OK, intentBack);
    }

    /**
     * Create new instance
     *
     * @param content
     * @param answerIsTrue
     * @return
     */
    public static Intent newIntent(Context content, boolean answerIsTrue) {
        Intent intent = new Intent(content, CheatActivity.class);
        intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }
}
