package com.example.rush;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class StartActivity extends AppCompatActivity implements SensorEventListener {

    // センサー管理、カウントダウンタイマー、メディアプレイヤーなどのフィールドを定義
    private SensorManager sma;
    private boolean isRunning = false;
    public int score = 0;
    private long lastScoreTime = 0;

    private boolean backButtonEnabled = true;
    private MediaPlayer mediaPlayer;
    private SensorManager sensorManager;
    private Sensor gyroSensor;
    private TextView countdownText;
    private CountDownTimer countDownTimer;
    private boolean isFinished = false;
    private boolean isNormalmode = true;
    private long timeLeftInMillis = 10000;
    public int count = 0;
    private boolean flag2 = false;
    private boolean flag = true;
    Random random = new Random();

    private ImageView backArrow;

    private SoundPlayer soundPlayer;

    private static final String TAG = "SensorData";

    private Button startButton;

    // コンストラクタ
    public StartActivity() {
    }

    // アクティビティが作成された時に呼び出されるメソッド
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // UI要素を初期化
        countdownText = findViewById(R.id.countdownText);
        startButton = findViewById(R.id.button_start);
        Button resetButton = findViewById(R.id.button_reset);
        ImageView backArrow = findViewById(R.id.back_arrow);

        // メディアプレイヤーを初期化
        mediaPlayer = MediaPlayer.create(this, R.raw.sound1);

        // センサー管理を初期化
        sma = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }

        // インテントからモードを取得
        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        if ("super".equals(mode)) {
            isNormalmode = false;
        }

        // スタートボタンのクリックリスナーを設定
        startButton.setOnClickListener(v -> {
            if (!isRunning) { // カウントダウンが進行中でなければ
                startCountdown();
                startButton.setEnabled(false); // スタートボタンを無効化する
            }
        });

        // リセットボタンのクリックリスナーを設定
        resetButton.setOnClickListener(v -> {
            if (countDownTimer != null) {
                countDownTimer.cancel(); // カウントダウンタイマーをキャンセルする
            }
            resetCountdown();
            startButton.setEnabled(true);
            isRunning = false;

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
        });

        // 戻る矢印のクリックリスナーを設定
        backArrow.setOnClickListener(v -> {

            onBackPressed();
            finish();
        });


    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if (backButtonEnabled) {
                onBackPressed();
            }
        }
    };
}

    public void setBackButtonEnabled(boolean enabled){
        this.backButtonEnabled=enabled;
    }

    // センサーを登録するメソッド
    private void registerSensors() {
        sma.registerListener(this, sma.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_FASTEST);
        isRunning = true;
        score = 0;
        lastScoreTime = 0;
        count = 0;
    }

    // センサーの登録を解除するメソッド
    private void unregisterSensors() {
        sma.unregisterListener(this);
        sensorManager.unregisterListener(this);
        isRunning = false;
    }

    // 結果画面に遷移するメソッド
    private void gotoResult() {
        if (isFinished) {
            return;
        }

        // ファイル保存などの処理が終了した後に、次のアクティビティにデータを渡す
        Intent intent = new Intent(StartActivity.this, ResultActivity.class);
        intent.putExtra("score", score); // スコアを渡す
        intent.putExtra("count", count); // カウントを渡す
        startActivity(intent);
        finish();
    }

    private void startCountdown() {
        soundPlayer = new SoundPlayer(this);
        backArrow=findViewById(R.id.back_arrow);
        backArrow.setVisibility(View.GONE);
        setBackButtonEnabled(false);
        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntiFinshed) {
                long secondLeft = millisUntiFinshed / 1000 + 1;
                soundPlayer.countSound();
            }

            public void onFinish() {
                CountdownTimer();
                soundPlayer.countDownSound();
            }
        }.start();
    }


    // カウントダウンを開始するメソッド
    private void CountdownTimer() {
        mediaPlayer.start();
        registerSensors();
        setBackButtonEnabled(false);

        if (countDownTimer != null) {       //タイマーが作動しているかどうか
            countDownTimer.cancel();        //カウントダウンタイマーをキャンセル
        }
        if (isFinished) {
            score = 0;
            count = 0;
            isFinished = false;             //カウントダウンが終了したかどうかを追跡している
        }
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        //カウントダウンタイマーのインスタンスを作成し指定された時間でカウントダウンを開始する
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {

            //毎秒ごとに呼び出されるメソッド
            @Override
            public void onTick(long millisUntilFinished) {
                //残り時間を更新する
                timeLeftInMillis = millisUntilFinished;
                //カウントダウンテキストを更新する
                updateCountdownText();
            }

            //カウントダウンが終了したときに呼び出されるメソッド

            @Override
            public void onFinish() {
                //センサーの登録を解除
                unregisterSensors();

                //カウントダウンテキストを"終了に設定する"
                countdownText.setText("終了");

                //カウントダウンが終了したことを示すフラグを設定

                isFinished = true;

                /*ノーマルモードまたはスーパーモードの終了処理を呼び出す
                基本の設定で最初のモードはノーマルモードと設定されている*/
                if (isNormalmode) {
                    Normalmodefinish();
                } else {
                    Supermodefinish();
                }

                //メディアプレイヤーが再生中であれば一時停止する
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }

                //gotoResultメソッド呼び出し
                gotoResult();
            }
        }.start();
    }

    // ノーマルモードが終了した時の処理
    private void Normalmodefinish() {
        Intent intent = new Intent(StartActivity.this, ResultActivity.class);
        intent.putExtra("mode", "normal");//モードを"normal"としてインテントに追加
        intent.putExtra("score", score);//スコアをインテントに追加
        intent.putExtra("count", count);//カウントをインテントに追加
        startActivity(intent);//ResultActivityを開始
    }

    // スーパーモードが終了した時の処理
    private void Supermodefinish() {
        Intent intent = new Intent(StartActivity.this, ResultActivity.class);
        intent.putExtra("mode", "super");//モードを"super"としてインテントに追加
        intent.putExtra("score", score);//スコアをインテントに追加
        intent.putExtra("count", count);//カウントをインテントに追加
        startActivity(intent);//ResultActivityを開始
    }

    // カウントダウンをリセットするメソッド
    private void resetCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();//カウントダウンタイマーをキャンセル
        }

        score = 0;//スコアをリセット
        count = 0;//カウントをリセット
        timeLeftInMillis = 10000;//カウントダウンタイマーをキャンセル
        updateCountdownText();//カウントダウンテキストを更新
    }

    // カウントダウンテキストを更新するメソッド
    private void updateCountdownText() {
        int secondsLeft = (int) (timeLeftInMillis / 1000);//残りの秒数を計算
        countdownText.setText(String.valueOf(secondsLeft));//テキストビューに残り秒数を設定
    }

    // センサーの値が変更された時に呼び出されるメソッド
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            handleAccelerometerData(event);    //加速度センサーデータを処理
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            handleGyroscopeData(event);         //ジャイロスコープデータを処理
        }
    }

    // 加速度センサーのデータを処理するメソッド
    private void handleAccelerometerData(SensorEvent event) {
        double y = event.values[1];

        if (isNormalmode) {
            if (30 < y && flag2) {
                score++; //スコア増加
                flag2 = false;
            }

            if (y < -30 && !flag2) {
                score++;  //スコア増加
                flag2 = true;

            }
        } else {
            if (30 < y && flag2) {
                score = score + random.nextInt(4) + 1;  //スコアをランダムに増加
                flag2 = false;
            }

            if (y < -30 && !flag2) {
                score = score + random.nextInt(4) + 1;  //スコアをランダムに増加
                flag2 = true;


            }
        }
    }

    // ジャイロスコープのデータを処理するメソッド
    private void handleGyroscopeData(SensorEvent event) {
        float zg = event.values[2];

        if (isNormalmode) {
            if (3 < zg && flag) {
                count++;        //カウントを増加
                flag = false;
            }
            if (-3 > zg && !flag) {
                count++;            //カウントを増加
                flag = true;

            }
        } else {
            if (isNormalmode) {
                if (3 < zg && flag) {
                    count = count + random.nextInt(4) + 1;      //カウントをランダムに増加
                    flag = false;
                }
                if (-3 > zg && !flag) {
                    count = count + random.nextInt(4) + 1;      //カウントをランダムに増加
                    flag = true;

                }
            }
        }
    }

    // アクティビティが再開された時に呼び出されるメソッド
    @Override
    protected void onResume() {
        super.onResume();
        // アクティビティが再開されたら音楽を再生する
       /* if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true); // ループ再生
        }*/
        sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // アクティビティが再開されたら10秒にリセットする
        if (isFinished) {
            isFinished = false;
            timeLeftInMillis = 10000; // 10秒にリセット
            updateCountdownText();
            startButton.setEnabled(true);
        }
    }

    // アクティビティが一時停止された時に呼び出されるメソッド
    @Override
    protected void onPause() {
        super.onPause();
        // アクティビティが一時停止されたら音楽を停止する
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        sensorManager.unregisterListener(this);
    }

    // アクティビティが破棄される時に呼び出されるメソッド
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // アクティビティが破棄されるときに音楽をリリースする
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // センサーの精度が変更された時に呼び出されるメソッド
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void onBackPressed() {
        if (backButtonEnabled) {
            // 画面を閉じる
            super.onBackPressed();
        }
    }

}

