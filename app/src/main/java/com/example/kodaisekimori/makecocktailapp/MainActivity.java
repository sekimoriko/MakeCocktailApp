package com.example.kodaisekimori.makecocktailapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //スタートボタン
        Button startButton = (Button)findViewById(R.id.start_button);
        ImageView bartenderView = (ImageView)findViewById(R.id.bartenderView);

        //材料選択モードに遷移
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SelectActivity.class);
                startActivity(intent);
            }
        });
    }
}
