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

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                boolean checked = checkBox.isChecked();

                if(checked) {
                    factor[1] = 1;
                }else {
                    factor[1] = 0;
                }
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                boolean checked = checkBox.isChecked();

                if(checked) {
                    factor[2] = 1;
                }else {
                    factor[2] = 0;
                }
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                boolean checked = checkBox.isChecked();

                if(checked) {
                    factor[3] = 1;
                }else {
                    factor[3] = 0;
                }
            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                boolean checked = checkBox.isChecked();

                if(checked) {
                    factor[4] = 1;
                }else {
                    factor[4] = 0;
                }
            }
        });

        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                boolean checked = checkBox.isChecked();

                if(checked) {
                    factor[5] = 1;
                }else {
                    factor[5] = 0;
                }
            }
        });

        shakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ShakeActivity.class);
                // 値渡し書く
                intent.putExtra("_factor", factor);
                startActivity(intent);
                //textView.setText(String.valueOf(factor[0]));
            }
        });
    }
}
