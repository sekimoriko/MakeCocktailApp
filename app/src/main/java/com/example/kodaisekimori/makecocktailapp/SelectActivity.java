package com.example.kodaisekimori.makecocktailapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SelectActivity extends AppCompatActivity {
    private TextView textView;
    private final int NUM = 6;  // 材料数
    int[] factor = {0,0,0,0,0,0};     // 材料ごとの要素

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Button shakeButton = (Button)findViewById(R.id.go_shake);

        CheckBox c1 = (CheckBox)findViewById(R.id.one);
        CheckBox c2 = (CheckBox)findViewById(R.id.two);
        CheckBox c3 = (CheckBox)findViewById(R.id.three);
        CheckBox c4 = (CheckBox)findViewById(R.id.four);
        CheckBox c5 = (CheckBox)findViewById(R.id.five);
        CheckBox c6 = (CheckBox)findViewById(R.id.six);

        textView = (TextView)findViewById(R.id.testview);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                boolean checked = checkBox.isChecked();

                if(checked) {
                    factor[0] = 1;
                }else {
                    factor[0] = 0;
                }
            }
        });

        shakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(SelectActivity.this, ShakeActivity.class);
                // 値渡し書く
                // startActivity(intent);
                textView.setText(String.valueOf(factor[0]));
            }
        });
    }
}
