package com.example.android.histoquiz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    protected void initView(Context context) {
        super.initView(context);
        optionList = findViewById(R.id.questionGroup);
    }

    public boolean isAnswered() {
        /*
        Just recognizes whether there is a currently selected answer.
         */
        for (int i = 0; i < optionList.getChildCount(); i++) {
            View option = optionList.getChildAt(i);
            if (option instanceof CheckBox)
                if (((CheckBox) option).isChecked()) {
                    return true;
                }

        }
        return false;
    }

    @Override
    public float correctness() {
        int correctChecks = 0;
        int totalChecks = 0;
        for (int e = 0; e < correctAnswer.size(); e++)
            System.out.println("Correct Anwers " + correctAnswer.get(e));
        for (int i = 0; i < optionList.getChildCount(); i++) {
            View option = optionList.getChildAt(i);
            if (option instanceof CheckBox) {
                totalChecks++;
                if (((CheckBox) option).isChecked()) {
                    System.out.println("Checked " + (i + 1));
                    if (correctAnswer.contains(i + 1))
                        correctChecks++;
                    else
                        correctChecks--;
                }
            }
        }
        if (correctAnswer.size() == 0)
            if (correctChecks == 0)
                return 1;
            else if (totalChecks != 0)
                return correctChecks / totalChecks;
            else
                // I don't see how this could happen...
                return -1;
        else
            return (float) correctChecks / correctAnswer.size();
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

    public void addOption(CheckBox option, int index) {
        /*
        Interface for adding options to the question.
         */
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        option.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton option, boolean isChecked) {
                progress.update();
            }
        });
        optionList.addView(option, params);
    }
}
