package com.example.rush;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rush.R;

public class HelpActivity extends AppCompatActivity {

    private LinearLayout bottomSheet;
    private LinearLayout bottomSheet1;

    private LinearLayout bottomSheet2;
    private TextView sheet1;
    private TextView sheet2;

    private TextView sheet3;
    private boolean isSheetExpanded = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        //レイアウトの初期化
        bottomSheet = findViewById(R.id.bottom_sheet);
        sheet1 = findViewById(R.id.sheet1);
        bottomSheet1 = findViewById(R.id.bottom_sheet2);
        sheet2 = findViewById(R.id.sheet2);
        bottomSheet2 = findViewById(R.id.bottom_sheet3);
        sheet3 = findViewById(R.id.sheet3);

        //ボタンの取得
        Button button1 = findViewById(R.id.title);
        Button button2 = findViewById(R.id.title2);
        Button button3 = findViewById(R.id.title3);
        ImageView backarrow = findViewById(R.id.back_arrow);


        //タイトル１のクリックリスナー
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //シートの展開と収納を切り替えるメソッド
                if (!isSheetExpanded) {
                    expandSheet();//展開メソッド
                } else {
                    collapseSheet();//収納メソッド
                }
            }
        });


        //タイトル２のクリックリスナー
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            //シートの展開と収納を切り替えるメソッド
            public void onClick(View v) {
                if (!isSheetExpanded) {
                    expandSheet1();//展開メソッド
                } else {
                    collapseSheet1();//収納メソッド
                }
            }
        });


        //タイトル３のクリックリスナー
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            //シートの展開と収納を切り替えるメソッド
            public void onClick(View v) {
                if (!isSheetExpanded) {
                    expandSheet2();//展開メソッド
                } else {
                    collapseSheet2();//収納メソッド
                }
            }
        });

        //戻るボタンのクリックリスナーの設定

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//アクティビティを終了して前の画面に戻る
            }
        });

        //文字列の取得とspannableStringの設定
        String text = getString(R.string.sheet_text);
        SpannableString spannableString = new SpannableString(text);

        // ノーマルモードを大きく、色を赤にする
        int normalModeStart = text.indexOf("ノーマルモード");
        int normalModeEnd = normalModeStart + "ノーマルモード".length();
        spannableString.setSpan(new RelativeSizeSpan(1.5f), normalModeStart, normalModeEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), normalModeStart, normalModeEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 超人モードを大きく、色を青にする
        int superModeStart = text.indexOf("スーパーモード");
        int superModeEnd = superModeStart + "スーパーモード".length();
        spannableString.setSpan(new RelativeSizeSpan(1.5f), superModeStart, superModeEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), superModeStart, superModeEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // TextViewに設定
        sheet2.setText(spannableString);
    }


    //シートの展開メソッド
    private void expandSheet() {
        bottomSheet.setVisibility(View.VISIBLE);
        sheet1.setVisibility(View.VISIBLE);
        isSheetExpanded = true;
    }

    //シートの収納メソッド
    private void collapseSheet() {
        bottomSheet.setVisibility(View.GONE);
        sheet1.setVisibility(View.GONE);
        isSheetExpanded = false;
    }
    /*ボタンの数とシートの数は同じだからシートを一つずつ変える必要がある
    タイトル２のシート展開と収納*/
    private void expandSheet1() {
        bottomSheet1.setVisibility(View.VISIBLE);
        sheet2.setVisibility(View.VISIBLE);
        isSheetExpanded = true;
    }

    private void collapseSheet1() {
        bottomSheet1.setVisibility(View.GONE);
        sheet2.setVisibility(View.GONE);
        isSheetExpanded = false;
    }

    //タイトル３のシートの展開と収納メソッド

    private void expandSheet2() {
        bottomSheet2.setVisibility(View.VISIBLE);
        sheet3.setVisibility(View.VISIBLE);
        isSheetExpanded = true;
    }

    private void collapseSheet2() {
        bottomSheet2.setVisibility(View.GONE);
        sheet3.setVisibility(View.GONE);
        isSheetExpanded = false;
    }
    @Override
    public void onBackPressed() {
        // ここで戻るボタンを無効化するか、カスタムの動作を定義する
        // 例えば、何もしない場合:
        // super.onBackPressed() を呼ばないことで、戻るボタンを無効化
    }
}
