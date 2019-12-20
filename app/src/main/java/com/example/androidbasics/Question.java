package com.example.androidbasics;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean AnsweredOrNot = false;
    private boolean TheAnswerIsTrue = false;
    private boolean TheAnswerIsFalse = false;

    public boolean isTheAnswerIsFalse() {
        return TheAnswerIsFalse;
    }

    public void setTheAnswerIsFalse(boolean theAnswerIsFalse) {
        TheAnswerIsFalse = theAnswerIsFalse;
    }

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
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

    public boolean isAnsweredOrNot() {
        return AnsweredOrNot;
    }

    public void setAnsweredOrNot(boolean answeredOrNot) {
        AnsweredOrNot = answeredOrNot;
    }

    public boolean isTheAnswerIsTrue() {
        return TheAnswerIsTrue;
    }

    public void setTheAnswerIsTrue(boolean theAnswerIsTrue) {
        TheAnswerIsTrue = theAnswerIsTrue;
    }
}
