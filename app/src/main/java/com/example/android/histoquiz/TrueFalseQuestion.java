package com.example.android.histoquiz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Lorenzo on 22/03/2018.
 * <p>
 * A True/False question View.
 */

public class TrueFalseQuestion extends HistoQuestion {

    // View parts
    ImageButton trueButton;
    ImageButton falseButton;

    // The question correctAnswer
    public boolean correctAnswer;
    // The question currentAnswer
    private Boolean currentAnswer = null;

    private void setCurrentAnswer(Boolean currentAnswer) {
        /*
        Beside settings the new currentAnswer, changes the view state accordingly.
         */
        this.currentAnswer = currentAnswer;
        if (this.currentAnswer == null) {
            trueButton.setImageResource(R.drawable.greencheck);
            falseButton.setImageResource(R.drawable.bigredx);
        } else if (this.currentAnswer) {
            trueButton.setImageResource(R.drawable.greencheckselected);
            falseButton.setImageResource(R.drawable.bigredx);
        } else {
            trueButton.setImageResource(R.drawable.greencheck);
            falseButton.setImageResource(R.drawable.bigredxselected);
        }
        progress.update();
    }

    public TrueFalseQuestion(Context context, ProgressHandler progress) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, progress);
    }

    public TrueFalseQuestion(Context context, AttributeSet attrs, ProgressHandler progress) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, attrs, progress);
    }

    public TrueFalseQuestion(Context context, AttributeSet attrs, int defStyleAttr, ProgressHandler progress) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, attrs, defStyleAttr, progress);
    }

    // Initialize the view instances
    protected void initView(Context context) {
        super.initView(context);
        trueButton = findViewById(R.id.TrueButton);
        trueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                truePressed(v);

            }
        });
        falseButton = findViewById(R.id.FalseButton);
        falseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                falsePressed(v);

            }
        });
    }

    protected int getLayout() {
        return R.layout.true_false_question_view;
    }

    public void truePressed(View view) {
        /*
        Change the current selected answer.
         */
        if (currentAnswer == Boolean.TRUE) setCurrentAnswer(null);
        else setCurrentAnswer(true);
    }

    public void falsePressed(View view) {
        /*
        Change the current selected answer.
         */
        if (currentAnswer == Boolean.FALSE) setCurrentAnswer(null);
        else setCurrentAnswer(false);
    }

    public boolean isAnswered() {
        /*
        Just recognizes whether there is a currently selected answer.
         */
        return currentAnswer != null;
    }

    public void reset(){
        /*
        Reset the question to an initial state.
         */
        setCurrentAnswer(null);
    }


}
