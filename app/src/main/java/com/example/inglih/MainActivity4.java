package com.example.inglih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity4 extends AppCompatActivity {
    private TextView textViewQuestion;
    private EditText editTextAnswer;
    private Button buttonSubmit;
    private TextView textViewQuestionNumber;

    private String[] questions = {
            "Переведите предложение: 'this is a book.'",
            "Переведите предложение: 'i want to go home.'",
            "Переведите предложение: 'she is a student.'",
            "Переведите предложение: 'they are playing football.'",
            "Переведите предложение: 'he has a cat.'"
    };
    private String[][] correctAnswers = {
            {"это книга", "книга", "книгу", "книге"},
            {"я хочу пойти домой", "хочу пойти домой", "хочу домой", "домой хочу"},
            {"она студентка", "студентка", "студент", "она студент"},
            {"они играют в футбол", "играют в футбол", "футбол играют", "в футбол играют"},
            {"у него есть кот", "есть кот", "кот у него", "у него кот"}
    };
    private int currentQuestionIndex = 0;
    private int totalQuestions = 0;
    private int incorrectAnswers = 0;
    private boolean isInputEnabled = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textViewQuestion = findViewById(R.id.textViewQuestion2);
        editTextAnswer = findViewById(R.id.editTextAnswer2);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewQuestionNumber = findViewById(R.id.textViewQuestionNumber);

        showQuestion();

        // Находим кнопку button5
        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переходим на MainActivity6
                Intent intent = new Intent(MainActivity4.this, MainActivity6.class);
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
            textViewQuestion.setText(currentQuestion);
        } else {
            Toast.makeText(this, "Тест завершен", Toast.LENGTH_SHORT).show();
            double errorPercentage = (double) incorrectAnswers / totalQuestions;
            if (errorPercentage > 0.5) {
                Toast.makeText(this, "Ваш уровень английского: A2 Pre-Intermediate", Toast.LENGTH_LONG).show();
                isInputEnabled = false;
            } else {
                Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
                startActivity(intent);
            }
        }
    }

    private void checkAnswer() {
        totalQuestions++;

        String userAnswer = editTextAnswer.getText().toString().trim().toLowerCase();
        String[] possibleAnswers = correctAnswers[currentQuestionIndex];

        boolean isCorrect = false;
        for (String possibleAnswer : possibleAnswers) {
            if (userAnswer.equals(possibleAnswer.toLowerCase())) {
                isCorrect = true;
                break;
            }
        }

        if (!isInputEnabled) {
            Toast.makeText(this, "Ваш уровень английского: A1 Beginner. Ввод заблокирован.", Toast.LENGTH_LONG).show();
        } else {
            if (isCorrect) {
                Toast.makeText(this, "Верно!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Неверно. Правильный ответ: " + Arrays.toString(possibleAnswers), Toast.LENGTH_SHORT).show();
                incorrectAnswers++;
            }
        }

        currentQuestionIndex++;
        editTextAnswer.setText("");
        showQuestion();
    }
}