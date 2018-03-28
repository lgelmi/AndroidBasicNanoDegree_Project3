package com.example.android.histoquiz;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Lorenzo on 22/03/2018.
 * <p>
 * A ToggleableRadioButton that allow to uncheck a RadioButton with a further click.
 */

// Credits to https://stackoverflow.com/questions/15821334/unchecking-a-radio-button
public class ToggleableRadioButton extends android.support.v7.widget.AppCompatRadioButton {
    // Implement necessary constructors

    public ToggleableRadioButton(Context context) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context);
    }

    public ToggleableRadioButton(Context context, AttributeSet attrs) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, attrs);
    }

    public ToggleableRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void toggle() {
        if(isChecked()) {
            if(getParent() instanceof RadioGroup) {
                ((RadioGroup)getParent()).clearCheck();
            }
        } else {
            setChecked(true);
        }
    }
}
