package com.example.android.histoquiz;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by Lorenzo on 22/03/2018.
 * <p>
 * A generic class for any question
 */

public abstract class HistoQuestion extends ConstraintLayout {

    // View parts
    Context context;
    TextView questionNumberText;
    TextView questionTitleText;
    TextView questionBodyText;

    // Store the ProgressHandler Instance
    ProgressHandler progress;

    // The question number to be displayed to the side
    protected int questionNumber;
    // The title describing the question type
    protected String questionTitle;
    // The question body with the actual question
    protected String questionBody;


    public void setQuestionNumber(int questionNumber) {
        /*
        Besides setting the questionNumber, updates the corresponding text field.
         */
        this.questionNumber = questionNumber;
        questionNumberText.setText(context.getResources().getString(R.string.QuestionNumber, this.questionNumber));
    }

    public void setQuestionTitle(String questionTitle) {
        /*
        Besides setting the questionTitle, updates the corresponding text field.
         */
        this.questionTitle = questionTitle;
        questionTitleText.setText(this.questionTitle);
    }

    public void setQuestionBody(String questionBody) {
        /*
        Besides setting the questionBody, updates the corresponding text field.
         */
        this.questionBody = questionBody;
        questionBodyText.setText(this.questionBody);
    }

    // TODO I should think to some way to automatically get the progress handler...

    public HistoQuestion(Context context) {
        /*
        Just setup an empty view.
         */
        super(context);
        initView(context);
    }

    public HistoQuestion(Context context, AttributeSet attrs) {
         /*
        Just setup an empty view.
         */
        super(context, attrs);
        initView(context);
    }

    public HistoQuestion(Context context, AttributeSet attrs, int defStyleAttr) {
         /*
        Just setup an empty view.
         */
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public HistoQuestion(Context context, ProgressHandler progress) {
        /*
        Just setup an empty view.
         */
        super(context);
        this.progress = progress;
        initView(context);
    }


    // Each type of question should initialize its own view here.
    protected void initView(Context context) {
        LayoutInflater.from(context).inflate(getLayout(), this, true);
        this.context = this.getContext();
        questionNumberText = findViewById(R.id.QuestionNumber);
        questionTitleText = findViewById(R.id.QuestionTitle);
        questionBodyText = findViewById(R.id.QuestionBody);
    }

    // Forces the subclass to specify a question layout.
    protected abstract int getLayout();

    // Forces the subclass to identify answered questions.
    public abstract boolean isAnswered();

    // Forces the subclass to implement a question reset.
    public abstract void reset();
}
