package com.example.kodaisekimori.makecocktailapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SelectActivity extends AppCompatActivity {
    private final int NUM = 6;  // 材料数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Button shakeButton = (Button)findViewById(R.id.go_shake);

        CheckBox checkBox = (CheckBox)findViewById(R.id.one);
        checkBox.setChecked(true);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                boolean checked = checkBox.isChecked();
                Toast.makeText(SelectActivity.this, "onClick():"+String.valueOf(checked), Toast.LENGTH_SHORT).show();
            }
        });

        shakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(SelectActivity.this, ShakeActivity.class);
                // 値渡し書く
                // startActivity(intent);
            }
        });
    }
}
