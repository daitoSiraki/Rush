package com.example.rush;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultTextView = findViewById(R.id.resultTextView);

        ImageView home = findViewById(R.id.home_icon);

        //インテントからスコア、カウント、モードを取得
        int score = getIntent().getIntExtra("score", 0);
        int count = getIntent().getIntExtra("count", 0);
        String mode = getIntent().getStringExtra("mode");

        // スコアを保存する
        saveScore(score, count);

        // 速度を計算
        double speed = calculateSpeed(score);

        // モードに応じて画像とテキストを設定
        if ("normal".equals(mode)) {
            setNormalResultText(score, speed);
        } else if ("super".equals(mode)) {
            setSuperResultText(score, speed);
        } else {
            // デフォルトの処理（今回の例ではあり得ないが、念のため）
            setResultText(score, speed);
        }


        // ホームボタンのクリックリスナーを設定
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void saveScore(int score, int count) {
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".txt";
        //File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        String mode = getIntent().getStringExtra("mode"); // モードを取得
        File dir;

        // モードに応じてディレクトリを設定
        if ("super".equals(mode)) {
            dir = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "super");
        } else {
            dir = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "normal");
        }

        // ディレクトリが存在しない場合は作成
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(/*"合計スコア: " + */score + "\n");
            //writer.write("合計カウント: " + count + "\n");
            writer.flush();
        } catch (IOException e) {
            Log.e("ResultActivity", "スコア保存失敗", e);
        }
    }


    // スコアに応じて速度を計算するメソッド
    public double calculateSpeed(int score) {
        return (score * 2.32) * 0.18;
    }

    // スコアに応じて画像とテキストを設定するメソッド
    public void setNormalResultText(int score, double speed) {
        ImageView imageView = findViewById(R.id.imageview);
        imageView.setVisibility(View.VISIBLE);

        if (score==0){
            resultTextView.setText("合計スコア: " + score + "\n" + "  速度:" + String.format("%.1f", speed) + "m/hです");
        }else if (score < 38) {
            imageView.setImageResource(R.drawable.namakemono);
            resultTextView.setText("合計スコア: " + score + "\n" + "  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 72) {
            imageView.setImageResource(R.drawable.rakuda);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 90) {
            imageView.setImageResource(R.drawable.kaba);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 120) {
            imageView.setImageResource(R.drawable.kapibara);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 140) {
            imageView.setImageResource(R.drawable.hyo);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 179) {
            imageView.setImageResource(R.drawable.rion);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 191) {
            imageView.setImageResource(R.drawable.cheater);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else {
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        }
    }

    public void setResultText(int score, double speed) {
        ImageView imageView = findViewById(R.id.imageview);
        imageView.setVisibility(View.VISIBLE);

        if (score < 38) {
            imageView.setImageResource(R.drawable.namakemono);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 72) {
            imageView.setImageResource(R.drawable.rakuda);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 90) {
            imageView.setImageResource(R.drawable.kaba);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 120) {
            imageView.setImageResource(R.drawable.kapibara);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 140) {
            imageView.setImageResource(R.drawable.hyo);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 179) {
            imageView.setImageResource(R.drawable.rion);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 191) {
            imageView.setImageResource(R.drawable.cheater);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else {
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        }
    }


    public void setSuperResultText(int score, double speed) {
        ImageView imageView = findViewById(R.id.imageview);
        imageView.setVisibility(View.VISIBLE);

        if (score < 38) {
            imageView.setImageResource(R.drawable.namakemono);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 72) {
            imageView.setImageResource(R.drawable.rakuda);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 90) {
            imageView.setImageResource(R.drawable.kaba);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 120) {
            imageView.setImageResource(R.drawable.kapibara);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 140) {
            imageView.setImageResource(R.drawable.hyo);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 179) {
            imageView.setImageResource(R.drawable.rion);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else if (score < 191) {
            imageView.setImageResource(R.drawable.cheater);
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        } else {
            resultTextView.setText("合計スコア: " + score + "\n" +"  速度:" + String.format("%.1f", speed) + "m/hです");
        }
    }
    @Override
    public void onBackPressed() {
        // ここで戻るボタンを無効化するか、カスタムの動作を定義する
        // 例えば、何もしない場合:
        // super.onBackPressed() を呼ばないことで、戻るボタンを無効化
    }


}
