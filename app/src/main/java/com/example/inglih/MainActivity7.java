package com.example.inglih;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity7 extends AppCompatActivity {
    private TextView textViewQuestion;
    private EditText editTextAnswer;
    private Button buttonSubmit;
    private TextView textViewQuestionNumber;
    private TextView textViewExplanation;
    private Button button5;

    private String[] questions = {
            "Choose the correct form of the verb: She ___ (go) to the store every day.",
            "Which sentence is grammatically correct?\n\nA) I have went to the store yesterday.\nB) I have gone to the store yesterday.",
            "Identify the part of speech for the word 'quickly' in the sentence: He ran quickly to catch the bus.",
            "Choose the correct form of the comparative adjective: This book is ___ (interesting) than that one.",
            "Which sentence is grammatically correct?\n\nA) She don't like coffee.\nB) She doesn't like coffee.",
            // Добавленные вопросы и объяснения
            "Choose the correct form of the verb: They ___ (eat) dinner when I arrived.",
            "Which sentence is grammatically correct?\n\nA) I have did my homework yesterday.\nB) I have done my homework yesterday.",
            "Identify the part of speech for the word 'always' in the sentence: She always arrives on time.",
            "Choose the correct form of the superlative adjective: This is ___ (big) building in the city.",
            "Which sentence is grammatically correct?\n\nA) He don't want to go to the party.\nB) He doesn't want to go to the party."
    };

    private String[] correctAnswers = {
            "goes",
            "B",
            "adverb",
            "more interesting",
            "B",

            "ate",
            "B",
            "adverb",
            "the biggest",
            "B"
    };

    private String[] explanations = {
            "Choose the correct form of the verb from the given options.",
            "Select the option corresponding to the grammatically correct sentence.",
            "Identify the part of speech for the word provided in the sentence.",
            "Choose the correct form of the comparative adjective.",
            "Select the grammatically correct sentence.",
            // Добавленные объяснения
            "Choose the correct form of the verb from the given options.",
            "Select the option corresponding to the grammatically correct sentence.",
            "Identify the part of speech for the word provided in the sentence.",
            "Choose the correct form of the superlative adjective.",
            "Select the grammatically correct sentence."
    };

    private int currentQuestionIndex = 0;
    private int totalQuestions = 0;
    private int incorrectAnswers = 0;
    private boolean isInputEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        textViewQuestion = findViewById(R.id.textViewQuestion2);
        editTextAnswer = findViewById(R.id.editTextAnswer2);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewQuestionNumber = findViewById(R.id.textViewQuestionNumber);
        textViewExplanation = findViewById(R.id.textViewExplanation);

        // Show the first question
        showQuestion();
        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переходим на MainActivity6
                Intent intent = new Intent(MainActivity7.this, MainActivity6.class);
                startActivity(intent);
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void showQuestion() {
        if (currentQuestionIndex < questions.length) {
            textViewQuestionNumber.setText("Вопрос " + (currentQuestionIndex + 1) + " из " + questions.length);

            String currentQuestion = questions[currentQuestionIndex];
            String explanation = explanations[currentQuestionIndex];

            textViewQuestion.setText(currentQuestion);
            textViewExplanation.setText(explanation);
        } else {
            Toast.makeText(this, "Тест завершен", Toast.LENGTH_SHORT).show();

            double errorPercentage = (double) incorrectAnswers / totalQuestions;
            if (errorPercentage > 0.5) {
                Toast.makeText(this, "Ваш уровень B2 Upper-Intermediate", Toast.LENGTH_LONG).show();
                isInputEnabled = false;
                Intent intent = new Intent(MainActivity7.this, MainActivity6.class);
                intent.putExtra("userLevel", "B2 Upper-Intermediate"); // Передача уровня
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity7.this, MainActivity10.class);
                startActivity(intent);
            }
        }
    }

    private void checkAnswer() {
        totalQuestions++;

        String userAnswer = editTextAnswer.getText().toString().trim();
        String correctAnswer = correctAnswers[currentQuestionIndex];

        if (!isInputEnabled) {
            Toast.makeText(this, "Ваш уровень английского B2 Upper-Intermediate. ", Toast.LENGTH_LONG).show();
        } else {
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                Toast.makeText(this, "Верно!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Неверно. Правильный ответ: " + correctAnswer, Toast.LENGTH_SHORT).show();
                incorrectAnswers++;
            }
        }

        currentQuestionIndex++;
        editTextAnswer.setText("");
        showQuestion();
    }
}