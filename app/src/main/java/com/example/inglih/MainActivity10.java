package com.example.inglih;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity10 extends AppCompatActivity {
    private int incorrectAnswerCount = 0;
    private static final int MAX_INCORRECT_ANSWERS = 3;
    private MediaPlayer mediaPlayer;
    private Button buttonPlayAudio;
    private Button buttonStopAudio;
    private SeekBar seekBar;
    private Button buttonRewind;
    private Button buttonForward;
    private int pausedPosition = 0;
    private Handler handler = new Handler();
    private Visualizer visualizer;
    private AudioVisualizerView visualizerView;



    private boolean isSeekBarTracking = false;

    private String[] questions;
    private String[][] answers;
    private int currentQuestionIndex = 0; // Текущий индекс вопроса

    private TextView textViewQuestion;
    private CheckBox checkboxOption1;
    private CheckBox checkboxOption2;
    private CheckBox checkboxOption3;
    private CheckBox checkboxOption4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

        questions = new String[]{
                "According to the legend of Atlantis, why did the gods decide to destroy the island?",
                "What is the prevailing belief regarding the existence of Atlantis?"
        };

        answers = new String[][]{
                {"The people of Atlantis became too greedy and power-hungry.",
                        "The island was plagued by natural disasters.",
                        "Atlantis posed a threat to neighboring civilizations.",
                        "The gods were displeased with the inhabitants' lack of gratitude."},
                {"Atlantis is widely accepted as a historical fact.",
                        "Atlantis is considered a fictional tale created by Plato.",
                        "Atlantis remains a mystery, with inconclusive evidence for or against its existence.",
                        "Atlantis is believed to be a lost civilization waiting to be discovered by modern archaeology."}
        };

        textViewQuestion = findViewById(R.id.textView3);

        checkboxOption1 = findViewById(R.id.checkbox_option1);
        checkboxOption2 = findViewById(R.id.checkbox_option2);
        checkboxOption3 = findViewById(R.id.checkbox_option3);
        checkboxOption4 = findViewById(R.id.checkbox_option4);

        buttonPlayAudio = findViewById(R.id.buttonPlayAudio);
        buttonStopAudio = findViewById(R.id.buttonStopAudio);
        buttonRewind = findViewById(R.id.buttonRewind);
        buttonForward = findViewById(R.id.buttonForward);
        visualizerView = findViewById(R.id.visualizerView);
        seekBar = findViewById(R.id.seekBar);
        setupSeekBar();
        setupVisualizer(); // Инициализируем Visualizer
        updateQuestionAndAnswers();

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
                checkAnswers(); // Вызываем метод проверки ответов
            }
        });

        buttonStopAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();
            }
        });

        buttonRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewindAudio();
            }
        });

        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forwardAudio();
            }
        });

        // Находим кнопку button4
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переходим на MainActivity6
                Intent intent = new Intent(MainActivity10.this, MainActivity6.class);
                startActivity(intent);
            }
        });

        checkboxOption1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkboxOption2.setChecked(false);
                    checkboxOption3.setChecked(false);
                    checkboxOption4.setChecked(false);
                }
            }
        });

        checkboxOption2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkboxOption1.setChecked(false);
                    checkboxOption3.setChecked(false);
                    checkboxOption4.setChecked(false);
                }
            }
        });

        checkboxOption3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkboxOption1.setChecked(false);
                    checkboxOption2.setChecked(false);
                    checkboxOption4.setChecked(false);
                }
            }
        });

        checkboxOption4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkboxOption1.setChecked(false);
                    checkboxOption2.setChecked(false);
                    checkboxOption3.setChecked(false);
                }
            }
        });
    }

    private void setupVisualizer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.atlan);
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
        } else {
            Log.e("Visualizer", "MediaPlayer is null");
        }
    }

    private void playAudio() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    visualizer.setEnabled(false); // Отключаем визуализацию при завершении воспроизведения
                }
            });
            mediaPlayer.start();
            visualizer.setEnabled(true); // Включаем визуализацию при воспроизведении
            seekBar.setMax(mediaPlayer.getDuration());

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null && mediaPlayer.isPlaying() && !isSeekBarTracking) {
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        handler.postDelayed(this, 20); // Обновляем каждые 20 миллисекунд
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

    private void rewindAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int newPosition = currentPosition - 5000;
            mediaPlayer.seekTo(Math.max(newPosition, 0));
        }
    }

    private void forwardAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying() && !isSeekBarTracking) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int newPosition = currentPosition + 5000;

            if (newPosition <= mediaPlayer.getDuration()) {
                mediaPlayer.seekTo(newPosition);
                seekBar.setProgress(newPosition);
            }
        }
    }

    private boolean isTracking = false;

    private void setupSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (!isSeekBarTracking && mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                isTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
                isSeekBarTracking = false;
            }
        });
    }

    private void smoothSeekBarAnimation(int progress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(seekBar, "progress", progress);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    private void updateQuestionAndAnswers() {
        if (currentQuestionIndex < questions.length) {
            textViewQuestion.setText(questions[currentQuestionIndex]);

            checkboxOption1.setChecked(false);
            checkboxOption2.setChecked(false);
            checkboxOption3.setChecked(false);
            checkboxOption4.setChecked(false);

            checkboxOption1.setText(answers[currentQuestionIndex][0]);
            checkboxOption2.setText(answers[currentQuestionIndex][1]);
            checkboxOption3.setText(answers[currentQuestionIndex][2]);
            checkboxOption4.setText(answers[currentQuestionIndex][3]);

        } else {
            Toast.makeText(this, "No more questions available", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAnswers() {
        boolean isAnswerCorrect = false;

        // Check which options are selected
        boolean option1Selected = checkboxOption1.isChecked();
        boolean option2Selected = checkboxOption2.isChecked();
        boolean option3Selected = checkboxOption3.isChecked();
        boolean option4Selected = checkboxOption4.isChecked();

        // Check the correctness of the answer for the current question
        switch (currentQuestionIndex) {
            case 0:
                // Check the first question
                isAnswerCorrect = option1Selected && !option2Selected && !option3Selected && !option4Selected;
                break;
            case 1:
                // Check the second question
                isAnswerCorrect = !option1Selected && option2Selected && !option3Selected && !option4Selected;
                break;
            // Add additional cases for other questions similarly
        }

        if (isAnswerCorrect) {
            // If the answer is correct, move to the next question or activity
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.length) {
                updateQuestionAndAnswers(); // Update question and answers
            } else {
                Intent intent = new Intent(MainActivity10.this, MainActivity6.class);
                startActivity(intent);
                Toast.makeText(this, "Ваш уровень английского C1 Advanced. ", Toast.LENGTH_LONG).show();
            }
        } else {
            // If the answer is incorrect, display a message
            Toast.makeText(this, "Неправильный ответ", Toast.LENGTH_SHORT).show();
            incorrectAnswerCount++;

            if (incorrectAnswerCount >= MAX_INCORRECT_ANSWERS) {
                Toast.makeText(this, "Ваш уровень английского B2 Upper-Intermediate. ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity10.this, MainActivity6.class);
                startActivity(intent);
            }
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