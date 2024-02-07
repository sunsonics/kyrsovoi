package com.example.inglih;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity3 extends AppCompatActivity {
    private TextView textViewQuestion;
    private EditText editTextAnswer;
    private Button buttonSubmit;

    private String[] questions = {"Как переводится слово 'apple'?", "Как переводится слово 'cat'?", "Как переводится слово 'book'?"};
    private String[] correctAnswers = {"яблоко", "кот", "книга"};
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Показываем первый вопрос
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
            String currentQuestion = questions[currentQuestionIndex];
            textViewQuestion.setText(currentQuestion);
        } else {
            // Все вопросы пройдены
            Toast.makeText(this, "Тест завершен", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAnswer() {
        String userAnswer = editTextAnswer.getText().toString().trim();
        String correctAnswer = correctAnswers[currentQuestionIndex];

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            Toast.makeText(this, "Верно!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Неверно. Правильный ответ: " + correctAnswer, Toast.LENGTH_SHORT).show();
        }

        // Переходим к следующему вопросу
        currentQuestionIndex++;
        editTextAnswer.setText(""); // Очищаем поле ввода
        showQuestion();
    }
}