package com.example.android.histoquiz;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Lorenzo on 22/03/2018.
 * <p>
 * A Multiple Choice question View.
 */

public class MultipleChoiceQuestion extends HistoQuestion {

    // View parts
    RadioGroup optionList;


    // The question correctAnswer
    public int correctAnswer;

    public MultipleChoiceQuestion(Context context) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context);
    }

    public MultipleChoiceQuestion(Context context, AttributeSet attrs) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, attrs);
    }

    public MultipleChoiceQuestion(Context context, AttributeSet attrs, int defStyleAttr) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, attrs, defStyleAttr);
    }

    public MultipleChoiceQuestion(Context context, ProgressHandler progress) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, progress);
    }

    // Initialize the view instances
    protected void initView(Context context) {
        super.initView(context);
        optionList = findViewById(R.id.questionGroup);
        optionList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                progress.update();
            }
        });
    }

    protected int getLayout() {
        return R.layout.multiple_choice_view;
    }

    public boolean isAnswered() {
        /*
        Just recognizes whether there is a currently selected answer.
         */
        System.out.println(optionList.indexOfChild(findViewById(optionList.getCheckedRadioButtonId())));
        return optionList.indexOfChild(findViewById(optionList.getCheckedRadioButtonId())) + 1 != 0;
    }

    public void reset() {
        /*
        Reset the question to an initial state.
         */
        optionList.clearCheck();
    }

    public void addOption(RadioButton option, int index) {
        /*
        Interface for adding options to the question.
         */
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        optionList.addView(option, index, params);
    }
}
