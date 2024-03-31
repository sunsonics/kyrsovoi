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
    private static final int REQUEST_CODE_MAIN_ACTIVITY_6 = 1;
    private String[] questions = {
            "Переведите предложение: 'The man in front of me is having a great day.'",
            "Переведите предложение: 'Go outside, the postman brought the letters.'",
            "Переведите предложение: 'The criminals escaped from prison yesterday.'",
            "Переведите предложение: 'Repetition is the mother of learning.'",
            "Переведите предложение: 'I can't see anything without glasses.'"
    };
    private String[][] correctAnswers = {
            {"У мужчины напротив меня отличный день", "У мужчины напротив меня хороший день", "У мужчины напротив меня чудесный день", "У мужчины передо мной хороший день", "У мужчины передо мной чудесный день", "У мужчины передо мной отличный день"},
            {"Выходи на улицу, почтальон принес письма", "Почтальон принес письма, выходи на улицу", "Почтальон принес почту, выходи на улицу","Выходи на улицу, почтальон принес почту"},
            {"Преступники вчера сбежали из тюрьмы", "Вчера преступники сбежали из тюрьмы", "Преступники сбежали из тюрьмы вчера"},
            {"Повторение мать учения", "Мать учения повторение"},
            {"Я ничего не вижу без очков", "Я без очков ничего не вижу", "Без очков я ничего не вижу"}
    };
    private int currentQuestionIndex = 0;
    private int totalQuestions = 0;
    private int incorrectAnswers = 0;
    private boolean isInputEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        textViewQuestion = findViewById(R.id.textViewQuestion2);
        editTextAnswer = findViewById(R.id.editTextAnswer2);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewQuestionNumber = findViewById(R.id.textViewQuestionNumber);

        showQuestion();

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
                Intent intent = new Intent(MainActivity4.this, MainActivity6.class);
                intent.putExtra("userLevel", "A2 Pre-Intermediate");
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
                intent.putExtra("userLevel", "Your level here"); // Здесь вы можете установить нужный уровень
                startActivity(intent);
                finish(); // Завершаем текущую активность
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
            Toast.makeText(this, "Ваш уровень английского: A2 Pre-Intermediate. Ввод заблокирован.", Toast.LENGTH_LONG).show();
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