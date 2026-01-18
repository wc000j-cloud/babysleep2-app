package com.babysleep.app;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private StarView starView;
    private Button playButton;
    private Button stopButton;
    private SeekBar volumeSeekBar;
    private SeekBar timerSeekBar;
    private TextView timerText;
    private CountDownTimer countDownTimer;
    private boolean isPlaying = false;
    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        starView = findViewById(R.id.starView);
        playButton = findViewById(R.id.playButton);
        stopButton = findViewById(R.id.stopButton);
        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerText = findViewById(R.id.timerText);

        setupMediaPlayer();
        setupVolumeControl();
        setupTimerControl();
        setupButtons();
        startStarAnimation();
    }

    private void setupMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.lullaby);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.5f, 0.5f);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "BabySleep:WakeLock");
    }

    private void setupVolumeControl() {
        volumeSeekBar.setMax(100);
        volumeSeekBar.setProgress(50);
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = progress / 100f;
                mediaPlayer.setVolume(volume, volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void setupTimerControl() {
        timerSeekBar.setMax(120);
        timerSeekBar.setProgress(30);
        updateTimerText(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimerText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void updateTimerText(int minutes) {
        timerText.setText(minutes + " 分钟");
    }

    private void setupButtons() {
        playButton.setOnClickListener(v -> {
            if (!isPlaying) {
                startPlaying();
            }
        });

        stopButton.setOnClickListener(v -> {
            if (isPlaying) {
                stopPlaying();
            }
        });
    }

    private void startPlaying() {
        mediaPlayer.start();
        isPlaying = true;
        playButton.setEnabled(false);
        stopButton.setEnabled(true);
        wakeLock.acquire();

        int minutes = timerSeekBar.getProgress();
        long millisInFuture = minutes * 60 * 1000;

        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int mins = seconds / 60;
                int secs = seconds % 60;
                timerText.setText(String.format("%02d:%02d", mins, secs));
            }

            @Override
            public void onFinish() {
                stopPlaying();
                timerText.setText(timerSeekBar.getProgress() + " 分钟");
            }
        }.start();
    }

    private void stopPlaying() {
        mediaPlayer.pause();
        mediaPlayer.seekTo(0);
        isPlaying = false;
        playButton.setEnabled(true);
        stopButton.setEnabled(false);

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

    private void startStarAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(starView, "rotation", 0f, 360f);
        animator.setDuration(60000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

    public static class StarView extends View {
        private Paint paint;
        private Random random;
        private float[] starX;
        private float[] starY;
        private float[] starSize;
        private float[] starAlpha;

        public StarView(Context context) {
            super(context);
            init();
        }

        private void init() {
            paint = new Paint();
            random = new Random();
            starX = new float[50];
            starY = new float[50];
            starSize = new float[50];
            starAlpha = new float[50];

            for (int i = 0; i < 50; i++) {
                starX[i] = random.nextFloat();
                starY[i] = random.nextFloat();
                starSize[i] = 2 + random.nextFloat() * 4;
                starAlpha[i] = 0.5f + random.nextFloat() * 0.5f;
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.parseColor("#0a0a2e"));

            paint.setColor(Color.WHITE);

            for (int i = 0; i < 50; i++) {
                paint.setAlpha((int) (starAlpha[i] * 255));
                float x = starX[i] * getWidth();
                float y = starY[i] * getHeight();
                canvas.drawCircle(x, y, starSize[i], paint);

                starAlpha[i] += (random.nextFloat() - 0.5f) * 0.1f;
                if (starAlpha[i] < 0.2f) starAlpha[i] = 0.2f;
                if (starAlpha[i] > 1.0f) starAlpha[i] = 1.0f;
            }

            postInvalidateDelayed(100);
        }
    }
}
