package com.example.rush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button startButton = findViewById(R.id.start);//スタートボタンの取得
        Button recordButton = findViewById(R.id.record);//レコードボタンの取得
        ImageView helpButton = findViewById(R.id.help);//ヘルプボタンの取得

        //スタートのクリックリスナ―の設定
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showButtonsDialog(); //ダイアログのメソッド呼び出し
            }
        });
        //レコードのクリックリスナーの設定
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        //ヘルプのクリックリスナーの設定
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    //ダイアログの表示をするメソッド
    private void showButtonsDialog() {
        DialogFragment dialog = new ButtonsDialogFragment();
        dialog.show(getSupportFragmentManager(), "ButtonsDialogFragment");
    }
    @Override
    public void onBackPressed() {
        // ここで戻るボタンを無効化するか、カスタムの動作を定義する
        // 例えば、何もしない場合:
        // super.onBackPressed() を呼ばないことで、戻るボタンを無効化
    }
}
