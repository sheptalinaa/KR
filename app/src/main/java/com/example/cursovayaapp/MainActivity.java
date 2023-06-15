package com.example.cursovayaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Question> questions;
    private int currentQuestionIndex;

    int countOfCorrectAnswers;

    private ArrayList<Question> loadQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.file_question);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String question;
            while ((question = reader.readLine()) != null) {
                String[] answers = new String[4];
                for (int i = 0; i < 4; i++) {
                    answers[i] = reader.readLine();
                }
                int correctAnswerIndex = Integer.parseInt(reader.readLine()) - 1;
                Question newQuestion = new Question(question, answers, correctAnswerIndex);
                questions.add(newQuestion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        Intent intent = new Intent(this, ResultActivity.class);
        ProgressBar progress = findViewById(R.id.progress);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
        });

        questions = loadQuestions();
        currentQuestionIndex = 0;
        countOfCorrectAnswers = 0;

        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(questions.get(currentQuestionIndex).getQuestion());

        RadioButton answer1RadioButton = findViewById(R.id.radio_button_1);
        answer1RadioButton.setText(questions.get(currentQuestionIndex).getAnswers()[0]);

        RadioButton answer2RadioButton = findViewById(R.id.radio_button_2);
        answer2RadioButton.setText(questions.get(currentQuestionIndex).getAnswers()[1]);

        RadioButton answer3RadioButton = findViewById(R.id.radio_button_3);
        answer3RadioButton.setText(questions.get(currentQuestionIndex).getAnswers()[2]);

        RadioButton answer4RadioButton = findViewById(R.id.radio_button_4);
        answer4RadioButton.setText(questions.get(currentQuestionIndex).getAnswers()[3]);


        Button nextButton = findViewById(R.id.button_next);
        nextButton.setOnClickListener(view -> {
            RadioButton selectedAnswerRadioButton = findViewById(
                    ((RadioGroup) findViewById(R.id.radio_group)).getCheckedRadioButtonId());
            if (selectedAnswerRadioButton == null) {
                Toast.makeText(MainActivity.this, "Выберите ответ", Toast.LENGTH_SHORT).show();
            } else {
                int selectedAnswerIndex = 0;

                int id = selectedAnswerRadioButton.getId();

                if (id == R.id.radio_button_1) {
                    selectedAnswerIndex = 0;
                } else if (id == R.id.radio_button_2) {
                    selectedAnswerIndex = 1;
                } else if (id == R.id.radio_button_3) {
                    selectedAnswerIndex = 2;
                } else if (id == R.id.radio_button_4) {
                    selectedAnswerIndex = 3;
                }


                if (selectedAnswerIndex == questions.get(currentQuestionIndex).getCorrectAnswerIndex()) {
                    Toast.makeText(MainActivity.this, "Верно", Toast.LENGTH_SHORT).show();
                    countOfCorrectAnswers++;
                } else {
                    Toast.makeText(MainActivity.this, "Неверно", Toast.LENGTH_SHORT).show();
                }
                progress.incrementProgressBy(5);
                currentQuestionIndex++;

                if (currentQuestionIndex < questions.size()) {
                    TextView questionTextView1 = findViewById(R.id.questionTextView);
                    questionTextView1.setText(questions.get(currentQuestionIndex).getQuestion());

                    RadioButton answer1RadioButton1 = findViewById(R.id.radio_button_1);
                    answer1RadioButton1.setText(questions.get(currentQuestionIndex).getAnswers()[0]);

                    RadioButton answer2RadioButton1 = findViewById(R.id.radio_button_2);
                    answer2RadioButton1.setText(questions.get(currentQuestionIndex).getAnswers()[1]);

                    RadioButton answer3RadioButton1 = findViewById(R.id.radio_button_3);
                    answer3RadioButton1.setText(questions.get(currentQuestionIndex).getAnswers()[2]);

                    RadioButton answer4RadioButton1 = findViewById(R.id.radio_button_4);
                    answer4RadioButton1.setText(questions.get(currentQuestionIndex).getAnswers()[3]);

                    selectedAnswerRadioButton.setChecked(false);

                } else {
                    intent.putExtra("result", countOfCorrectAnswers);
                    setResult(RESULT_OK, intent);
                    startActivity(intent);
                }
            }
            radioGroup.clearCheck();

        });

    }
}
