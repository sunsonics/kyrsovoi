package com.example.inglih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;


public class MainActivity5 extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        buttonPlayAudio = findViewById(R.id.buttonPlayAudio);
        buttonStopAudio = findViewById(R.id.buttonStopAudio);
        buttonRewind = findViewById(R.id.buttonRewind);
        buttonForward = findViewById(R.id.buttonForward);
        visualizerView = findViewById(R.id.visualizerView); // Получаем ссылку на VisualizerView
        seekBar = findViewById(R.id.seekBar);
        setupSeekBar();
        setupVisualizer(); // Инициализируем Visualizer

        buttonPlayAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
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
    }
    private void setupVisualizer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.possumsound);
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
            updateSeekBar(); // Обновляем позицию seekBar
        }
    }
    private void setupSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Не нужно делать ничего при начале перемотки
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Не нужно делать ничего при окончании перемотки
            }
        });
    }
    private void updateSeekBar() {
        if (mediaPlayer != null) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                }
            }, 1000); // Обновляем каждую секунду
        }
    }
    private void stopAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            visualizer.setEnabled(false); // Отключаем визуализацию при остановке воспроизведения
        }
    }

    private void rewindAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int newPosition = currentPosition - 5000; // Перемотка на 5 секунд назад
            mediaPlayer.seekTo(Math.max(newPosition, 0)); // Устанавливаем новую позицию воспроизведения
        }
    }

    private void forwardAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int newPosition = currentPosition + 5000; // Перемотка на 5 секунд вперёд
            mediaPlayer.seekTo(Math.min(newPosition, mediaPlayer.getDuration())); // Устанавливаем новую позицию воспроизведения
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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