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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity11 extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button buttonPlayAudio;
    private Button buttonStopAudio;
    private Button buttonRewind;
    private Button buttonForward;
    private Button button13;
    private SeekBar seekBar;
    private Handler handler = new Handler();
    private Visualizer visualizer;
    private AudioVisualizerView visualizerView;


    private TextView[] textViewQuestions = new TextView[10]; // Массив для всех ответов
    private EditText[] editTextAnswers = new EditText[10];
    private String[] questions;
    private String[][] answers;
    private int currentQuestionIndex = 0; // Текущий индекс вопроса

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        questions = new String[]{
                "1) a square in Brancusi’s sculpture is made of oak.     ",
                "2) Brancusi likes to demonstrate contrasting objects.    ",
                "3) it’s difficult to guess the name of the sculpture.    ",
                "4) Brancusi’s bird is crying.     ",
                "5) the bird opens its mouth to sing.     ",
                "6) many Mondrian’s paintings are very confusing.    ",
                "7) Mondrian’s painting is like a closed window.     ",
                "8) there is a wide variety of bright colours in this painting.     ",
                "9) Mondrian signed the painting with his initials.     ",
                "10) Mondrian also wrote some music.     "
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
            int questionId = getResources().getIdentifier("textViewQuestion" + (i + 1), "id", getPackageName());
            textViewQuestions[i] = findViewById(questionId);

            int answerId = getResources().getIdentifier("editTextAnswer" + (i + 1), "id", getPackageName());
            editTextAnswers[i] = findViewById(answerId);

            // Устанавливаем текст вопросов
            textViewQuestions[i].setText(questions[i]);
        }

        buttonPlayAudio = findViewById(R.id.buttonPlayAudio);
        buttonStopAudio = findViewById(R.id.buttonStopAudio2);
        visualizerView = findViewById(R.id.visualizerView);
        buttonRewind = findViewById(R.id.buttonRewind);
        buttonForward = findViewById(R.id.buttonForward);
        seekBar = findViewById(R.id.seekBar);
        setupVisualizer();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Если изменение было инициировано пользователем, перематываем аудио к новому положению
                if (fromUser && mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Не используется, но должен быть реализован в соответствии с интерфейсом
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Не используется, но должен быть реализован в соответствии с интерфейсом
            }
        });
        buttonRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewindAudio(15000); // перемотка на 15 секунд назад
            }
        });

        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forwardAudio(15000); // перемотка на 15 секунд вперед
            }
        });

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
                checkAnswers();
            }
        });

        buttonStopAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();
            }
        });

        button13 = findViewById(R.id.button13);
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переходим на MainActivity6
                Intent intent = new Intent(MainActivity11.this, MainActivity6.class);
                startActivity(intent);
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

    private void checkAnswers() {
        int totalQuestions = questions.length;
        int correctAnswersCount = 0;

        // Переменная для отслеживания правильных и неправильных ответов
        boolean[] isCorrectAnswer = new boolean[totalQuestions];

        // Проверить ответы пользователя
        for (int i = 0; i < totalQuestions; i++) {
            String userAnswer = editTextAnswers[i].getText().toString().trim();
            String correctAnswer = answers[i][0].trim();

            // Сравнить ответ пользователя с правильным ответом, игнорируя регистр
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                correctAnswersCount++;
                isCorrectAnswer[i] = true;
            } else {
                isCorrectAnswer[i] = false;
            }
        }

        // Вывести результаты проверки
        String resultMessage = "Правильные ответы: " + correctAnswersCount + "/" + totalQuestions;
        Toast.makeText(MainActivity11.this, resultMessage, Toast.LENGTH_SHORT).show();

        // Обновить цвет текста вопросов в зависимости от правильности ответов
        for (int i = 0; i < totalQuestions; i++) {
            if (isCorrectAnswer[i]) {
                textViewQuestions[i].setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                textViewQuestions[i].setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        }

        // Блокировать ввод после отправки сообщения о результатах
        for (EditText editText : editTextAnswers) {
            editText.setEnabled(false);
        }
    }

    private void rewindAudio(int milliseconds) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int newPosition = currentPosition - milliseconds;
            if (newPosition < 0) {
                newPosition = 0;
            }
            mediaPlayer.seekTo(newPosition);
        }
    }

    private void forwardAudio(int milliseconds) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int newPosition = currentPosition + milliseconds;
            int duration = mediaPlayer.getDuration();
            if (newPosition > duration) {
                newPosition = duration;
            }
            mediaPlayer.seekTo(newPosition);
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