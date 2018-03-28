package com.example.android.histoquiz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

/**
 * Created by Lorenzo on 28/03/2018.
 * <p>
 * A Multiple Answer question View.
 */

public class MultipleAnswerQuestion extends HistoQuestion {

    // View parts
    LinearLayout optionList;

    // The question correctAnswer
    public List<Integer> correctAnswer;

    public MultipleAnswerQuestion(Context context) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context);
    }

    public MultipleAnswerQuestion(Context context, AttributeSet attrs) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, attrs);
    }

    public MultipleAnswerQuestion(Context context, AttributeSet attrs, int defStyleAttr) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, attrs, defStyleAttr);
    }

    public MultipleAnswerQuestion(Context context, ProgressHandler progress) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, progress);
    }

    protected int getLayout() {
        return R.layout.multiple_answer_view;
    }

    public boolean isAnswered() {
        /*
        Just recognizes whether there is a currently selected answer.
         */
        for (int i = 0; i < optionList.getChildCount(); i++) {
            View option = optionList.getChildAt(i);
            if (option instanceof CheckBox)
                if(((CheckBox) option).isChecked())
                    return true;
        }
        return false;
    }

    public void reset() {
        /*
        Reset the question to an initial state.
         */
        for (int i = 0; i < optionList.getChildCount(); i++) {
            View option = optionList.getChildAt(i);
            if (option instanceof CheckBox)
                ((CheckBox) option).setChecked(false);
        }
    }

    public void addOption(RadioButton option, int index) {
        /*
        Interface for adding options to the question.
         */
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        optionList.addView(option, index, params);
    }
}
