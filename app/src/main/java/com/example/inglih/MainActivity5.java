package com.example.inglih;

import androidx.appcompat.app.AppCompatActivity;

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


public class MainActivity5 extends AppCompatActivity {
    private int incorrectAnswersCount = 0;
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
    private int currentQuestionNumber = 1;
    private TextView textViewQuestionNumber;
    private static final int REQUEST_CODE_MAIN_ACTIVITY_5 = 5;

    private boolean isSeekBarTracking = false;
    private int[] questionAudios = {R.raw.bank, R.raw.noga, R.raw.pevez, R.raw.nob, R.raw.math};

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
        setContentView(R.layout.activity_main5);

        questions = getResources().getStringArray(R.array.questions);
        answers = new String[5][];
        answers[0] = getResources().getStringArray(R.array.answers1);
        answers[1] = getResources().getStringArray(R.array.answers2);
        answers[2] = getResources().getStringArray(R.array.answers3);
        answers[3] = getResources().getStringArray(R.array.answers4);
        answers[4] = getResources().getStringArray(R.array.answers5);

        textViewQuestion = findViewById(R.id.textView3);
        textViewQuestionNumber = findViewById(R.id.textViewQuestionNumber);
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

                switch (currentQuestionIndex) {
                    case 1:
                        playSecondQuestionAudio();
                        break;
                    case 2:
                        playThirdQuestionAudio();
                        break;
                    case 3:
                        playFourthQuestionAudio();
                        break;
                    case 4:
                        playFifthQuestionAudio();
                        break;
                    default:
                        playAudio();
                        break;
                }
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
                Intent intent = new Intent(MainActivity5.this, MainActivity6.class);
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
        mediaPlayer = MediaPlayer.create(this, R.raw.bank);
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

    private void playSecondQuestionAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        seekBar.setProgress(0);

        mediaPlayer = MediaPlayer.create(this, R.raw.noga);

        if (visualizer != null) {
            visualizer.setEnabled(false);
            visualizer.release();
        }

        visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                visualizerView.updateVisualizer(waveform);
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                // Do nothing for this example
            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                visualizer.setEnabled(false);
            }
        });

        mediaPlayer.start();
        visualizer.setEnabled(true);
        seekBar.setMax(mediaPlayer.getDuration()); // Установка максимального значения для нового аудиофайла

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

    private void playThirdQuestionAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        seekBar.setProgress(0);

        mediaPlayer = MediaPlayer.create(this, R.raw.pevez);

        if (visualizer != null) {
            visualizer.setEnabled(false);
            visualizer.release();
        }

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

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                visualizer.setEnabled(false);
            }
        });
        updateQuestionAndAnswers();
        mediaPlayer.start();
        visualizer.setEnabled(true);
        seekBar.setMax(mediaPlayer.getDuration());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying() && !isSeekBarTracking) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 20);
                }
            }
        }, 20);
    }

    private void playFourthQuestionAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        seekBar.setProgress(0);

        mediaPlayer = MediaPlayer.create(this, R.raw.nob);

        if (visualizer != null) {
            visualizer.setEnabled(false);
            visualizer.release();
        }
        visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                visualizerView.updateVisualizer(waveform);
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {

            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                visualizer.setEnabled(false);
            }
        });
        updateQuestionAndAnswers();
        mediaPlayer.start();
        visualizer.setEnabled(true);
        seekBar.setMax(mediaPlayer.getDuration());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying() && !isSeekBarTracking) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 20);
                }
            }
        }, 20);
    }


    private void playFifthQuestionAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        seekBar.setProgress(0);

        mediaPlayer = MediaPlayer.create(this, R.raw.math);

        if (visualizer != null) {
            visualizer.setEnabled(false);
            visualizer.release();
        }
        visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                visualizerView.updateVisualizer(waveform);
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {

            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                visualizer.setEnabled(false);
            }
        });
        updateQuestionAndAnswers();
        mediaPlayer.start();
        visualizer.setEnabled(true);
        seekBar.setMax(mediaPlayer.getDuration());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying() && !isSeekBarTracking) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 20);
                }
            }
        }, 20);
    }

    private void stopAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            visualizer.setEnabled(false);
        }


        if (currentQuestionIndex == 1) {
            stopSecondQuestionAudio();
        }
    }

    private void stopSecondQuestionAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
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

            // Проверяем, что индекс вопроса не выходит за пределы массива ответов
            if (currentQuestionIndex < answers.length) {
                checkboxOption1.setText(answers[currentQuestionIndex][0]);
                checkboxOption2.setText(answers[currentQuestionIndex][1]);
                checkboxOption3.setText(answers[currentQuestionIndex][2]);
                checkboxOption4.setText(answers[currentQuestionIndex][3]);
            }

            String questionNumberText = "Вопрос " + (currentQuestionIndex + 1) + " из " + questions.length;
            textViewQuestionNumber.setText(questionNumberText);
        } else {
            Toast.makeText(this, "No more questions available", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAnswers() {
        int correctAnswersCount = 0; // Count of correct answers

        // Get the selected options by the user
        boolean option1Checked = checkboxOption1.isChecked();
        boolean option2Checked = checkboxOption2.isChecked();
        boolean option3Checked = checkboxOption3.isChecked();
        boolean option4Checked = checkboxOption4.isChecked();

        // Check the correctness of the answer for the current question
        switch (currentQuestionIndex) {
            case 0: // First question
                if (option1Checked) // If the answer is correct
                    correctAnswersCount++;
                else // If the answer is incorrect
                    incorrectAnswersCount++;
                break;
            case 1: // Second question
                if (option3Checked) // If the answer is correct
                    correctAnswersCount++;
                else // If the answer is incorrect
                    incorrectAnswersCount++;
                break;
            case 2: // Third question
                if (option3Checked) // If the answer is correct
                    correctAnswersCount++;
                else // If the answer is incorrect
                    incorrectAnswersCount++;
                break;
            case 3: // Fourth question
                if (option1Checked) // If the answer is correct
                    correctAnswersCount++;
                else // If the answer is incorrect
                    incorrectAnswersCount++;
                break;
            case 4: // Fifth question
                if (option4Checked) // If the answer is correct
                    correctAnswersCount++;
                else // If the answer is incorrect
                    incorrectAnswersCount++;
                break;
            default:
                break;
        }

        // If the user has answered incorrectly three or more times, redirect to MainActivity6
        if (incorrectAnswersCount >= 3) {
            Intent intent = new Intent(MainActivity5.this, MainActivity6.class);
            intent.putExtra("userLevel", "B1 Intermediate"); // Передача уровня
            startActivity(intent);
            finish();
            Toast.makeText(this, "Ваш уровень B1 Intermediate.", Toast.LENGTH_SHORT).show();
        } else {
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++; // Move to the next question
                updateQuestionAndAnswers(); // Update the question and answer options
                seekBar.setProgress(0); // Reset the audio progress
            } else {
                // All questions answered correctly, move to MainActivity7
                Intent intent = new Intent(MainActivity5.this, MainActivity7.class);
                startActivity(intent);
                finish(); // Close the current activity to prevent the user from returning to the questions
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