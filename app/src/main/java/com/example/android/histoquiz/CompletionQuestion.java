package com.example.android.histoquiz;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Lorenzo on 29/03/2018.
 * <p>
 * A Completion question View.
 */

public class CompletionQuestion extends HistoQuestion {

    // View parts
    EditText answer;
    String questionText;

    // The question correctAnswer
    public String correctAnswer;

    public CompletionQuestion(Context context) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context);
    }

    public CompletionQuestion(Context context, AttributeSet attrs) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, attrs);
    }

    public CompletionQuestion(Context context, AttributeSet attrs, int defStyleAttr) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, attrs, defStyleAttr);
    }

    public CompletionQuestion(Context context, ProgressHandler progress) {
        /*
        Just call to its parent. Java is special like that.
         */
        super(context, progress);
    }

    // Initialize the view instances
    protected void initView(final Context context) {
        super.initView(context);
        answer = findViewById(R.id.AnswerText);
        answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateBody();
                progress.update();
            }
        });
    }

    public String getValidEditText() {
        String text = answer.getText().toString();
        if (text.trim().isEmpty())
            return "________";
        else
            return text;
    }

    public void setQuestionBody(String questionBody) {
        /*
        Besides setting the questionBody, updates the corresponding text field.
         */
        questionText = questionBody;
        updateBody();
    }

    protected void updateBody() {
        /*
        Update the questionBodyText depending on the answer editText content.
        */
        questionBodyText.setText(Html.fromHtml(String.format(questionText, "<b>" + getValidEditText() + "</b>")));
    }

    protected int getLayout() {
        return R.layout.completion_view;
    }

    public boolean isAnswered() {
        /*
        Just recognizes whether there is a currently selected answer.
         */
        String text = answer.getText().toString();
        return !text.trim().isEmpty();
    }

    public void reset() {
        /*
        Reset the question to an initial state.
         */
        answer.getText().clear();
    }
}
