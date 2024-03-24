package com.example.inglih;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity9 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);

        // Получаем переданный элемент из интента
        String selectedItem = getIntent().getStringExtra("selectedItem");
        String details = getIntent().getStringExtra("details");

        // Находим TextView в макете активности
        TextView textViewDetail = findViewById(R.id.textViewDetail);

        // Устанавливаем текст для TextView
        textViewDetail.setText(selectedItem + ": " + details);

        // Находим кнопку "Назад"
        Button buttonBack = findViewById(R.id.button8);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрываем текущую активность при нажатии на кнопку "Назад"
            }
        });
    }
}