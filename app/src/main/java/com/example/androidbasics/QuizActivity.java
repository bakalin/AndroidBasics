package com.example.androidbasics;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {



    //
    //private int mColor;

    //记录已答问题数
    private int numOfQuestionAnswered = 0;
    private int numOfCorrectAnswer = 0;

    //app暂停时暂存数据
    private static final String KEY_INDEX = "index";

    //用于日志打印的TAG
    private static final String TAG = "QuizActivity";

    //获取试图中的按钮和文本
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mBackButton;
    private TextView mQuestionTextView;

    //问题数组
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;


    //以下5个位重载的回调函数，仅在activity状态改变时调用，打印日志信息
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    //重载的暂存数据用的函数
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");//oncreate 调用时打印日志
        setContentView(R.layout.activity_quiz);


        //若有暂存数据，导入
        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);



        //updateQuestion();


        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

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


        //下一个问题按钮
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });


        //前一个问题按钮，加数组长度再取余，保证了数组不越界
        mBackButton=(Button)findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;//加上问题数组长度取余后及为后退
                updateQuestion();
            }
        });

        updateQuestion();

    }

    //改变按钮状态，同一道题，选过之后按钮不可继续点击，且被选过的按钮会高亮出来（蓝底）
    private void setButtons() {
        if (mQuestionBank[mCurrentIndex].isAnsweredOrNot() == true) {
            disableButtons();
            if (mQuestionBank[mCurrentIndex].isTheAnswerIsTrue()) {
                mTrueButton.setBackgroundColor(Color.BLUE);
                mFalseButton.setBackgroundColor(Color.GRAY);
            } else if (mQuestionBank[mCurrentIndex].isTheAnswerIsFalse()){
                mFalseButton.setBackgroundColor(Color.BLUE);
                mTrueButton.setBackgroundColor(Color.GRAY);
            }
        }
        else {
            enableButtons();
            mTrueButton.setBackgroundColor(Color.GRAY);
            mFalseButton.setBackgroundColor(Color.GRAY);
        }
    }


    //更新问题
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        setButtons();
        if (numOfQuestionAnswered == mQuestionBank.length){
            Toast.makeText(this,"all done,correct "+numOfCorrectAnswer+"/"+mQuestionBank.length,Toast.LENGTH_LONG).show();
        }

        }


    //检查用户点击是否正确
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        mQuestionBank[mCurrentIndex].setAnsweredOrNot(true);
        mQuestionBank[mCurrentIndex].setTheAnswerIsTrue(userPressedTrue);
        mQuestionBank[mCurrentIndex].setTheAnswerIsFalse(!userPressedTrue);
        Log.d(TAG, "checkAnswer: ["+mCurrentIndex+"]"+mQuestionBank[mCurrentIndex].isTheAnswerIsTrue()+"/"+mQuestionBank[mCurrentIndex].isTheAnswerIsFalse());
        int messageResId = 0;
        numOfQuestionAnswered++;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            numOfCorrectAnswer++;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();

        //点击后立即更新按钮颜色
        updateQuestion();

    }

    private void disableButtons(){
        mTrueButton.setClickable(false);
        mFalseButton.setClickable(false);
    }
    private void enableButtons(){
        mTrueButton.setClickable(true);
        mFalseButton.setClickable(true);
    }
}
