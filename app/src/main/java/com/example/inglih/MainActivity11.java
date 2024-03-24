package com.example.inglih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity11 extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button buttonPlayAudio;
    private Button buttonStopAudio;
    private SeekBar seekBar;
    private Handler handler = new Handler();
    private Visualizer visualizer;
    private AudioVisualizerView visualizerView;

    private EditText[] editTextAnswers = new EditText[10]; // Массив для всех ответов

    private String[] questions;
    private String[][] answers;
    private int currentQuestionIndex = 0; // Текущий индекс вопроса

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        questions = new String[]{
                "1) a square in Brancusi’s sculpture is made of oak.  ",
                "2) Brancusi likes to demonstrate contrasting objects.  ",
                "3) it’s difficult to guess the name of the sculpture.  ",
                "4) Brancusi’s bird is crying.  ",
                "5) the bird opens its mouth to sing.  ",
                "6) many Mondrian’s paintings are very confusing. ",
                "7) Mondrian’s painting is like a closed window.  ",
                "8) there is a wide variety of bright colours in this painting.  ",
                "9) Mondrian signed the painting with his initials.  ",
                "10) Mondrian also wrote some music.  "
        };

        answers = new String[][]{
                {"B"},
                {"A"},
                {"A"},
                {"B"},
                {"B"},
                {"A"},
                {"A"},
                {"B"},
                {"A"},
                {"B"}
        };

        // Инициализация всех EditText для ответов
        for (int i = 0; i < 10; i++) {
            int editTextId = getResources().getIdentifier("editTextAnswer" + (i + 1), "id", getPackageName());
            editTextAnswers[i] = findViewById(editTextId);
        }

        // Установка текста вопросов для соответствующих EditText
        for (int i = 0; i < questions.length; i++) {
            editTextAnswers[i].setText(questions[i]);
        }

        buttonPlayAudio = findViewById(R.id.buttonPlayAudio);
        buttonStopAudio = findViewById(R.id.buttonStopAudio);
        visualizerView = findViewById(R.id.visualizerView);
        seekBar = findViewById(R.id.seekBar);
        setupVisualizer();
        //updateQuestionAndAnswers(); // Убран вызов метода

        buttonPlayAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });

        Button buttonSubmit = findViewById(R.id.buttonSubmit2);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkAnswers(); // Убран вызов метода
            }
        });

        buttonStopAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();
            }
        });



    }

    private void setupVisualizer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.scup);
        if (mediaPlayer != null) {
            visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
            visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
            visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
                @Override
                public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                    visualizerView.updateVisualizer(waveform);
                }

                @Override
                public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                    // Не используется в этом примере
                }
            }, Visualizer.getMaxCaptureRate() / 2, true, false);
        }
    }

    private void playAudio() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    visualizer.setEnabled(false);
                }
            });
            mediaPlayer.start();
            visualizer.setEnabled(true);
            seekBar.setMax(mediaPlayer.getDuration());

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        handler.postDelayed(this, 20);
                    }
                }
            }, 20);
        }
    }

    private void stopAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            visualizer.setEnabled(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (visualizer != null) {
            visualizer.release();
            visualizer = null;
        }
    }
}