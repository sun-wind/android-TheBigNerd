package com.jexample.android.geoquiz;

/**
 * Created by str
 * 08.06.16.
 */
class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(boolean answerTrue, int textResId) {
        mAnswerTrue = answerTrue;
        mTextResId = textResId;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

}



