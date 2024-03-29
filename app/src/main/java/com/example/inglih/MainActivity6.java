package com.example.inglih;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity6 extends AppCompatActivity {

    private TextView textViewUserAnswer;
    private Button buttonSubmit4;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String USER_LEVEL_KEY = "userLevel";
    private static final int REQUEST_CODE_MAIN_ACTIVITY_3 = 1;
    private static final int REQUEST_CODE_MAIN_ACTIVITY_4 = 2;
    private static final int REQUEST_CODE_MAIN_ACTIVITY_5 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        textViewUserAnswer = findViewById(R.id.textViewUserAnswer);

        Button button7 = findViewById(R.id.button7);
        Button button6 = findViewById(R.id.button6);
        Button buttonSubmit3 = findViewById(R.id.buttonSubmit3);

        String lastUserLevel = loadUserLevel();
        if (lastUserLevel != null) {
            textViewUserAnswer.setText("Ваш уровень: " + lastUserLevel);
        }

        Intent intent = getIntent();
        if (intent != null) {
            String userLevel = intent.getStringExtra("userLevel");
            if (userLevel != null) {
                textViewUserAnswer.setText("Ваш уровень: " + userLevel);
                // Сохраняем полученный уровень в SharedPreferences
                saveUserLevel(userLevel);
            }
        }
        if (button7 != null) {
            button7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity6.this, MainActivity3.class);
                    startActivityForResult(intent, REQUEST_CODE_MAIN_ACTIVITY_3);
                }
            });
        }
        Button buttonSubmit4 = findViewById(R.id.buttonSubmit4);
        buttonSubmit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем намерение для запуска MainActivity8
                Intent intent = new Intent(MainActivity6.this, MainActivity8.class);
                startActivity(intent); // Запускаем MainActivity8
            }
        });
        if (button6 != null) {
            button6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity6.this, MainActivity11.class);
                    startActivity(intent);
                }
            });
        }


        if (buttonSubmit3 != null) {
            buttonSubmit3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity6.this, MainActivity3.class);
                    startActivityForResult(intent, REQUEST_CODE_MAIN_ACTIVITY_4);
                }
            });
        }
    }
    private void saveUserLevel(String userLevel) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(USER_LEVEL_KEY, userLevel);
        editor.apply();
    }

    private String loadUserLevel() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(USER_LEVEL_KEY, null);
    }





}