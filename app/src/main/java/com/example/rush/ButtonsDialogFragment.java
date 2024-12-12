package com.example.rush;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

// DialogFragmentを継承したクラスを作成
public class ButtonsDialogFragment extends DialogFragment {

    // ダイアログが作成された時に呼び出されるメソッド
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // ダイアログを作成するためのAlertDialog.Builderを初期化
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.TransparentDialog);

        // レイアウトインフレーターを使って、カスタムレイアウトを読み込む
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_buttons_dialog_fragment, null);

        // カスタムレイアウト内のボタンとImageViewを取得
        Button button1 = view.findViewById(R.id.normal);
        Button button2 = view.findViewById(R.id.Super);
        ImageView backArrow = view.findViewById(R.id.back_arrow);

        // button1がクリックされた時の処理を定義
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startクラスに遷移するIntentを作成し、モードを"normal"に設定
                Intent intent = new Intent(requireActivity(), StartActivity.class);
                intent.putExtra("mode", "normal");
                // Intentを使ってActivityを開始
                startActivity(intent);
            }
        });

        // button2がクリックされた時の処理
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startクラスに遷移するIntentを作成し、モードを"super"に設定
                Intent intent = new Intent(requireActivity(), StartActivity.class);
                intent.putExtra("mode", "super");
                // Intentを使ってActivityを開始
                startActivity(intent);
            }
        });

        // backArrowがクリックされた時の処理
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ダイアログを閉じる
                dismiss();
            }
        });

        // ビューをダイアログに設定
        builder.setView(view);
        // ダイアログを作成して返す
        return builder.create();
    }



}

