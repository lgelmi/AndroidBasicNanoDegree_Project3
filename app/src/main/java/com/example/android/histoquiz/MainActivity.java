package com.example.android.histoquiz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    // View Elements
    TextView issueText;
    LinearLayout questionList;
    LinearLayout.LayoutParams questionParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0);
    int questionPadding;
    ProgressHandler progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        issueText = findViewById(R.id.IssueNumber);
        questionList = findViewById(R.id.QuestionList);
        questionPadding = (int) (this.getResources().getDimension(R.dimen.KiloPadding) + 0.5f);
        questionParams.setMargins(questionPadding, questionPadding, questionPadding, questionPadding);
        questionParams.weight = 1;
        progress = new ProgressHandler(findViewById(R.id.MainLayout));
        try {
            loadJSON();
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void loadJSON() throws IOException {
        /*
        Reads my JSON file.

        This should be heavily improved for robustness, but I don't think it is worth it in this
        case.
         */
        try (JsonReader reader = new JsonReader(new InputStreamReader(getAssets().open("46-February2018-Italy.json"), "UTF-8"))) {
            int issue = 0;
            int year = 0;
            String month = "N/A";
            reader.beginObject();
            questionList.removeAllViews();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "Issue":
                        issue = reader.nextInt();
                        break;
                    case "Year":
                        year = reader.nextInt();
                        break;
                    case "Month":
                        month = reader.nextString();
                        break;
                    case "Questions":
                        loadJSONQuestions(reader);
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            issueText.setText(this.getResources().getString(R.string.Issue, issue, month, year));
        }
        progress.update();
    }

    private void loadJSONQuestions(JsonReader reader) throws IOException {
        /*
        Specialized method to parse the question array.
         */
        int questionNumber = 1;
        String questionType;
        String questionTitle;
        String questionBody;
        boolean questionBoolAnswer;
        String questionStringAnswer;
        int questionIntAnswer;
        reader.beginArray();
        while (reader.hasNext()) {
            questionType = "";
            questionTitle = "";
            questionBody = "";
            questionStringAnswer = "";
            questionBoolAnswer = true;
            questionIntAnswer = 0;
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "Type":
                        questionType = reader.nextString();
                        break;
                    case "Title":
                        questionTitle = reader.nextString();
                        break;
                    case "Body":
                        questionBody = reader.nextString();
                        break;
                    case "Answer":
                        JsonToken token = reader.peek();
                        switch (token) {
                            case BOOLEAN:
                                questionBoolAnswer = reader.nextBoolean();
                                break;
                            case STRING:
                                questionStringAnswer = reader.nextString();
                                break;
                            case NUMBER:
                                questionIntAnswer = reader.nextInt();
                                break;
                            default:
                                reader.skipValue();
                        }
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            switch (questionType) {
                case "True/False": {
                    TrueFalseQuestion question = new TrueFalseQuestion(this, progress);
                    question.setQuestionNumber(questionNumber);
                    question.setQuestionTitle(questionTitle);
                    question.setQuestionBody(questionBody);
                    question.correctAnswer = questionBoolAnswer;
                    questionList.addView(question, questionParams);
                    questionNumber++;
                }
            }
        }
        reader.endArray();
    }

    public void reset(View view){
        /*
        Disable all current answer and update the progresses.
         */
        ScrollView scroll = findViewById(R.id.QuestionScroll);
        scroll.fullScroll(ScrollView.FOCUS_UP);
        for (int i = 0; i < questionList.getChildCount(); i++) {
            View question = questionList.getChildAt(i);
            if (question instanceof HistoQuestion)
                ((HistoQuestion) question).reset();
        }
        progress.update();
    }
}

class ProgressHandler {
    /*
    A utility class that takes care of handling the progress bar.
     */

    //View elements
    private Context context;
    private ProgressBar progress;
    private TextView progressText;
    private LinearLayout questionList;

    ProgressHandler(View view) {
        context = view.getContext();
        progress = view.findViewById(R.id.QuizProgressBar);
        progressText = view.findViewById(R.id.CompletionText);
        questionList = view.findViewById(R.id.QuestionList);
        update();

    }

    public int getAnswered() {
        /*
        Counts the number of completed question.
         */
        int answered = 0;
        for (int i = 0; i < questionList.getChildCount(); i++) {
            View question = questionList.getChildAt(i);
            if (question instanceof HistoQuestion)
                if (((HistoQuestion) question).isAnswered()) answered++;
        }
        return answered;
    }

    public int getQuestionNumber() {
         /*
        Counts the number of question.
         */
        int questionNumber = 0;
        for (int i = 0; i < questionList.getChildCount(); i++) {
            View question = questionList.getChildAt(i);
            if (question instanceof HistoQuestion) questionNumber++;
        }
        return questionNumber;
    }

    public void update() {
        /*
        Sets the progress based on the amount of completed answer, also sets the completion textview.
         */
        int questionNumber = getQuestionNumber();
        int answered = getAnswered();
        progress.setMax(questionNumber);
        progress.setProgress(answered);
        progressText.setText(context.getResources().getString(R.string.CompletedNumber, answered, questionNumber));
    }


}