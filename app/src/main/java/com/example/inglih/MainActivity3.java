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

public class MainActivity3 extends AppCompatActivity {
    private TextView textViewQuestion;
    private EditText editTextAnswer;
    private Button buttonSubmit;
    private TextView textViewQuestionNumber;

    private String[] questions = {
            "Как переводится слово 'bucket'?",
            "Как переводится слово 'plane'?",
            "Как переводится слово 'network'?",
            "Как переводится слово 'extinguisher'?",
            "Как переводится слово 'balloon'?",
            "Как переводится слово 'manufacturer'?",
            "Как переводится слово 'fork'?",
            "Как переводится слово 'fishing rod'?",
            "Как переводится слово 'government'?",
            "Как переводится слово 'butterfly'?"
    };
    private String[][] correctAnswers = {
            {"ведро", "вёдра", "ведёрко", "корзина", "корзинка"},
            {"самолёт", "самолет"},
            {"сеть"},
            {"огнетушитель"},
            {"воздушный шар", "шарик","воздушный шарик","шар"},
            {"производитель", "изготовитель"},
            {"вилка", "вилочка"},
            {"удочка", "спиннинг"},
            {"правительство", "власть"},
            {"бабочка"}
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
                Intent intent = new Intent(MainActivity3.this, MainActivity6.class);
                intent.putExtra("userLevel", "A1 Beginner"); // Замените "A1 Beginner" на фактический уровень
                startActivity(intent);
                finish();
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
        String[] correctAnswersForCurrentQuestion = correctAnswers[currentQuestionIndex];

        if (!isInputEnabled) {
            // Если ввод текста заблокирован, не проверяем ответ
            Toast.makeText(this, "Ваш уровень английского: A1 Beginner. Ввод заблокирован.", Toast.LENGTH_LONG).show();
            // Проверяем, что MainActivity6 существует, прежде чем запускать его
              { // Порог ошибок

            }
        } else {
            boolean isCorrect = false;
            for (String correctAnswer : correctAnswersForCurrentQuestion) {
                if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                    isCorrect = true;
                    break;
                }
            }

            if (isCorrect) {
                Toast.makeText(this, "Верно!", Toast.LENGTH_SHORT).show();
            } else {
                String correctAnswersString = Arrays.toString(correctAnswersForCurrentQuestion);
                Toast.makeText(this, "Неверно. Правильный ответ(ы): " + correctAnswersString, Toast.LENGTH_SHORT).show();
                incorrectAnswers++;
            }
        }

        // Переходим к следующему вопросу
        currentQuestionIndex++;
        editTextAnswer.setText(""); // Очищаем поле ввода
        showQuestion();
    }
}