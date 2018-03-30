package com.example.android.histoquiz;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.AttributeSet;
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

    // Algorithm from https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
    public int levenshteinDistance(CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;
        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];
        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;
        // dynamically computing the array of distances
        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;
            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;
                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;
                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }
            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }
        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

    @Override
    public float correctness() {
        /*
        Levenshtein checks for string similarity in their characters, not their meaning.
        A proper app would implement also the second kind. But this is a job for someone else.
         */
        String answerText = answer.getText().toString();
        if (answerText.trim().isEmpty())
            return 0;
        else {
            int distance = levenshteinDistance(answerText, correctAnswer);
            // I want a result comprised between -1 and 1
            float normalizedDistance;
            if(answerText.length() > correctAnswer.length())
                normalizedDistance = (float) (answerText.length() - distance) / answerText.length();
            else
                normalizedDistance = (float) (correctAnswer.length() - distance) / correctAnswer.length();
            // Answer that differs for most than 0.5 are wrong to me and deserves a negative score.
            return 2 * normalizedDistance - 1;
        }
    }

    public void reset() {
        /*
        Reset the question to an initial state.
         */
        answer.getText().clear();
    }
}
