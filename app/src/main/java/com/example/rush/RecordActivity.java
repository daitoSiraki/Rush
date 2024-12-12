package com.example.rush;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    private TextView[] scoreTextViews = new TextView[3]; // 配列のサイズを3に変更
    private TextView modeTextView; // 追加: モード表示用のTextView
    private ImageView rankImageView; // 追加: ランク画像表示用のImageView
    private boolean isNormal = true; // デフォルトはnormalディレクトリ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        ImageView backArrow = findViewById(R.id.back_arrow);
        ImageView buttonR = findViewById(R.id.buttonR);
        ImageView buttonL = findViewById(R.id.buttonL);

        // スコア表示用のTextViewを取得
        scoreTextViews[0] = findViewById(R.id.score1);
        scoreTextViews[1] = findViewById(R.id.score2);
        scoreTextViews[2] = findViewById(R.id.score3);
        modeTextView = findViewById(R.id.text2); // 追加: モード表示用のTextViewを取得
        rankImageView = findViewById(R.id.image); // 追加: ランク画像表示用のImageViewを取得

        // 初回読み込み
        loadAndDisplayData();

        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNormal = !isNormal;
                loadAndDisplayData();
            }
        });

        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNormal = !isNormal;
                loadAndDisplayData();
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // データを読み込んで表示するメソッド
    private void loadAndDisplayData() {
        List<String> dataList = loadAllData(isNormal ? "normal" : "super");

        // モード表示用のTextViewを更新
        modeTextView.setText(isNormal ? "ノーマルモード" : "スーパーモード");

        // スコアをTextViewにセット
        int highestScore = 0;
        for (int i = 0; i < scoreTextViews.length; i++) {
            if (i < dataList.size()) {
                scoreTextViews[i].setText(dataList.get(i));
                highestScore = Math.max(highestScore, extractScore(dataList.get(i)));
            } else {
                scoreTextViews[i].setText("");
            }
        }

        // スコアに応じてランク画像を変更
        if (highestScore >= 179) {
            rankImageView.setImageResource(R.drawable.platinumtrophy);
        } else if (highestScore >= 120) {
            rankImageView.setImageResource(R.drawable.goldtrophy);
        } else if (highestScore >= 72) {
            rankImageView.setImageResource(R.drawable.silvertrophy);
        } else {
            rankImageView.setImageResource(R.drawable.bronzetrophy);
        }
    }

    // 内部ストレージからデータをすべて読み込む
    private List<String> loadAllData(String mode) {
        List<String> dataList = new ArrayList<>();

        // 保存先ディレクトリを取得
        File dir = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), mode);

        if (dir.exists() && dir.isDirectory()) {
            // ファイル名リストを作成
            List<File> fileList = new ArrayList<>();
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    fileList.add(file);
                }
            }

            // ファイルからスコアを抽出し、スコアを基準にソート
            List<ScoreData> scoreDataList = new ArrayList<>();
            for (File file : fileList) {
                if (file.isFile()) {
                    try {
                        // ファイルを読み込む
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            int score = extractScore(line);
                            scoreDataList.add(new ScoreData(file.getName(), score, line));
                        }
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // スコアを基準にソート
            scoreDataList.sort(Comparator.comparingInt(ScoreData::getScore).reversed());

            // 上位3件のみをリストに追加
            for (int i = 0; i < Math.min(3, scoreDataList.size()); i++) {
                ScoreData scoreData = scoreDataList.get(i);
                dataList.add(scoreData.getLine());
            }
        }

        return dataList;
    }

    // スコアを抽出するヘルパーメソッド
    private int extractScore(String line) {
        try {
            // スコアが数字のみの場合の仮定
            return Integer.parseInt(line.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // スコアデータの内部クラス
    private static class ScoreData {
        private final String fileName;
        private final int score;
        private final String line;

        public ScoreData(String fileName, int score, String line) {
            this.fileName = fileName;
            this.score = score;
            this.line = line;
        }

        public String getFileName() {
            return fileName;
        }

        public int getScore() {
            return score;
        }

        public String getLine() {
            return line;
        }
    }

    @Override
    public void onBackPressed() {
        // ここで戻るボタンを無効化するか、カスタムの動作を定義する
        // 例えば、何もしない場合:
        // super.onBackPressed() を呼ばないことで、戻るボタンを無効化
    }
}
