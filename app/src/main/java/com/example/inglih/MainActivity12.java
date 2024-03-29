package com.example.inglih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity12 extends AppCompatActivity {

    private Button submitButton;
    private EditText[] answerEditTexts = new EditText[7]; // Массив для всех ответов
    private TextView[] questionTextViews = new TextView[7]; // Массив для всех вопросов
    private String[] questions;
    private String[] correctAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);

        // Вопросы и ответы
        questions = new String[]{
                "What did the shopkeeper sell? food / things",
                "What were the things made of? glass / metal",
                "Where did the shopkeeper go one day? market / palace",
                "What did the shopkeeper tell the boy to do? not to leave the door / not to leave the shop",
                "What did the shopkeeper see when he came back? the shop was closed / the shop was open",
                "Where was the boy standing? in the shop / in the square",
                "Did the boy understand the shopkeeper’s words exactly? yes / no"
        };

        correctAnswers = new String[]{
                "things",
                "metal",
                "palace",
                "not to leave the door",
                "the shop was open",
                "in the square",
                "yes"
        };

        // Найти все представления для EditText ответов и TextView вопросов
        for (int i = 0; i < 7; i++) {
            int answerId = getResources().getIdentifier("editTextAnswer" + (i + 1), "id", getPackageName());
            answerEditTexts[i] = findViewById(answerId);

            int questionId = getResources().getIdentifier("textViewQuestion" + (i + 1), "id", getPackageName());
            questionTextViews[i] = findViewById(questionId);

            // Установить текст вопроса в соответствующий TextView
            questionTextViews[i].setText(questions[i]);
        }

        // Найти кнопку отправки
        submitButton = findViewById(R.id.buttonSubmit2);

        // Добавить слушателя нажатия кнопки отправки
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получить ответы из EditText и выполнить проверку
                checkAnswers();
                // Блокировать ввод после отправки результатов
                blockInput();
            }
        });

        // Найти кнопку перехода на активность 6
        Button button13 = findViewById(R.id.button13);
        // Добавить слушателя нажатия на кнопку
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Перейти на активность 6
                Intent intent = new Intent(MainActivity12.this, MainActivity6.class);
                startActivity(intent);
                // Блокировать ввод после отправки результатов
                blockInput();
            }
        });
    }

    private void checkAnswers() {
        int totalQuestions = questions.length;
        int correctAnswersCount = 0;

        // Проверить ответы пользователя
        for (int i = 0; i < totalQuestions; i++) {
            String userAnswer = answerEditTexts[i].getText().toString().trim();
            String correctAnswer = correctAnswers[i];

            // Сравнить ответ пользователя с правильным ответом
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                correctAnswersCount++;
                // Установить зеленый цвет для правильного ответа
                questionTextViews[i].setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                // Установить красный цвет для неправильного ответа
                questionTextViews[i].setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        }

        // Вывести результаты проверки
        String resultMessage = "Правильные ответы: " + correctAnswersCount + "/" + totalQuestions;
        Toast.makeText(MainActivity12.this, resultMessage, Toast.LENGTH_SHORT).show();

        // Блокировать ввод после отправки результатов
        blockInput();
    }

    private void blockInput() {
        // Блокировать ввод после отправки результатов
        for (EditText editText : answerEditTexts) {
            editText.setEnabled(false);
        }
        submitButton.setEnabled(false);
    }
}