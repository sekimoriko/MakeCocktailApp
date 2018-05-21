package com.example.kodaisekimori.makecocktailapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Button restartButton = (Button)findViewById(R.id.restart_button);

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //int[] getColor = getIntent().getIntArrayExtra("_color");

        //background = (TextView)findViewById(R.id.background);
        //background.setBackgroundColor(Color.rgb(getColor[0], getColor[1], getColor[2]));
        //setContentView(background);
    }
}