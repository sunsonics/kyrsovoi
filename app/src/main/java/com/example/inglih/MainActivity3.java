package com.example.inglih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
    private TextView textViewQuestion;
    private EditText editTextAnswer;
    private Button buttonSubmit;
    private TextView textViewQuestionNumber;

    private String[] questions = {
            "Как переводится слово 'apple'?",
            "Как переводится слово 'cat'?",
            "Как переводится слово 'book'?",
            "Как переводится слово 'dog'?",
            "Как переводится слово 'house'?",
            "Как переводится слово 'pen'?",
            "Как переводится слово 'table'?",
            "Как переводится слово 'computer'?",
            "Как переводится слово 'student'?"
    };
    private String[] correctAnswers = {
            "яблоко",
            "кот",
            "книга",
            "собака",
            "дом",
            "ручка",
            "стол",
            "компьютер",
            "ученик"
    };
    private int currentQuestionIndex = 0;
    private int totalQuestions = 0;
    private int incorrectAnswers = 0;
    private boolean isInputEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textViewQuestion = findViewById(R.id.textViewQuestion2);
        editTextAnswer = findViewById(R.id.editTextAnswer2);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewQuestionNumber = findViewById(R.id.textViewQuestionNumber); // Находим TextView для номера вопроса

        // Показываем первый вопрос
        showQuestion();

        // Находим кнопку button5
        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переходим на MainActivity6
                Intent intent = new Intent(MainActivity3.this, MainActivity6.class);
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
            // Отображаем номер текущего вопроса
            textViewQuestionNumber.setText("Вопрос " + (currentQuestionIndex + 1) + " из " + questions.length);

            String currentQuestion = questions[currentQuestionIndex];
            textViewQuestion.setText(currentQuestion);
        } else {
            // Все вопросы пройдены
            Toast.makeText(this, "Тест завершен", Toast.LENGTH_SHORT).show();
            // Проверяем процент ошибок
            double errorPercentage = (double) incorrectAnswers / totalQuestions;
            if (errorPercentage > 0.5) {
                // Если процент ошибок более 50%, выводим заметное сообщение
                Toast.makeText(this, "Ваш уровень английского: A1 Beginner", Toast.LENGTH_LONG).show();
                // Блокируем ввод текста
                isInputEnabled = false;
            } else {
                // Иначе переходим на новую активность
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                startActivity(intent);
            }
        }
    }

    private void checkAnswer() {
        totalQuestions++;

        String userAnswer = editTextAnswer.getText().toString().trim();
        String correctAnswer = correctAnswers[currentQuestionIndex];

        if (!isInputEnabled) {
            // Если ввод текста заблокирован, не проверяем ответ
            Toast.makeText(this, "Ваш уровень английского: A1 Beginner. Ввод заблокирован.", Toast.LENGTH_LONG).show();
        } else {
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                Toast.makeText(this, "Верно!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Неверно. Правильный ответ: " + correctAnswer, Toast.LENGTH_SHORT).show();
                incorrectAnswers++;
            }
        }

        // Переходим к следующему вопросу
        currentQuestionIndex++;
        editTextAnswer.setText(""); // Очищаем поле ввода
        showQuestion();
    }
}