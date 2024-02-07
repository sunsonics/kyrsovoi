package com.example.inglih;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity2 extends AppCompatActivity {

    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button2 = findViewById(R.id.button);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода на другую активность
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                startActivity(intent); // Запускаем другую активность
            }
        });
    }
}