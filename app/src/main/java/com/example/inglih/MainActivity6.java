package com.example.inglih;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity6 extends AppCompatActivity {

    private Button button7;
    private Button button6;
    private Button buttonSubmit3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        button7 = findViewById(R.id.button7);
        button6 = findViewById(R.id.button6);
        buttonSubmit3 = findViewById(R.id.buttonSubmit3);

        // Проверяем, что объекты кнопок были успешно найдены
        if (button7 != null) {
            button7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Переход на MainActivity3
                    Intent intent = new Intent(MainActivity6.this, MainActivity3.class);
                    startActivity(intent);
                }
            });
        }

        if (button6 != null) {
            button6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Переход на MainActivity5
                    Intent intent = new Intent(MainActivity6.this, MainActivity5.class);
                    startActivity(intent);
                }
            });
        }

        if (buttonSubmit3 != null) {
            buttonSubmit3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Переход на MainActivity3
                    Intent intent = new Intent(MainActivity6.this, MainActivity3.class);
                    startActivity(intent);
                }
            });
        }
    }
}