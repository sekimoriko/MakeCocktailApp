package com.example.kodaisekimori.makecocktailapp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;

public class ResultActivity extends AppCompatActivity implements SensorEventListener, TextToSpeech.OnInitListener {
    private TextView background;
    private SensorManager sensorManager;
    private float sensorX;
    private float sensorY;
    private float sensorZ;
    private int[] materials;
    private String cocktailName;
    private TextView textAccel_2;
    private TextView textMaterials;
    private TextView textCocktail;
    private ImageView imageCocktail;
    private TextToSpeech tts;
    private static final String TAG = "TestTTS";
    private boolean slide_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tts = new TextToSpeech(this, this);

        Button restartButton = (Button)findViewById(R.id.restart_button);
        textAccel_2 = (TextView)findViewById(R.id.text_accel_2);
        textMaterials = (TextView)findViewById(R.id.materialsView);
        textCocktail = (TextView)findViewById(R.id.cocktailNameView);
        imageCocktail = (ImageView)findViewById(R.id.cocktailImageView);

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        materials = getIntent().getIntArrayExtra("_materials");
        textMaterials.setText(Arrays.toString(materials));

        int materials_num = 0;
        for(int i=0; i < materials.length; i++) {
            materials_num += materials[i];
        }

        if(materials_num > 2) {
            cocktailName = "謎";
            imageCocktail.setImageResource(R.drawable.drink8_purple);
        } else if(materials[0] == 1 && materials[3] == 1) {
            cocktailName = "オレンジブロッサム";
            imageCocktail.setImageResource(R.drawable.drink5_orange);
        } else if(materials[0] == 1 && materials[4] == 1) {
            cocktailName = "ジントニック";
            imageCocktail.setImageResource(R.drawable.drink6_skyblue);
        } else if(materials[0] == 1 && materials[5] == 1) {
            cocktailName = "ジンバック";
            imageCocktail.setImageResource(R.drawable.drink2_yellow);
        } else if(materials[1] == 1 && materials[3] == 1) {
            cocktailName = "スクリュードライバー";
            imageCocktail.setImageResource(R.drawable.drink5_orange);
        } else if(materials[1] == 1 && materials[4] == 1) {
            cocktailName = "ウォッカトニック";
            imageCocktail.setImageResource(R.drawable.drink6_skyblue);
        } else if(materials[1] == 1 && materials[5] == 1) {
            cocktailName = "モスコミュール";
            imageCocktail.setImageResource(R.drawable.drink2_yellow);
        } else if(materials[2] == 1 && materials[3] == 1) {
            cocktailName = "テキーラサンライズ";
            imageCocktail.setImageResource(R.drawable.drink5_orange);
        } else if(materials[2] == 1 && materials[4] == 1) {
            cocktailName = "テキーラトニック";
            imageCocktail.setImageResource(R.drawable.drink6_skyblue);
        } else if(materials[2] == 1 && materials[5] == 1) {
            cocktailName = "テキーラバック";
            imageCocktail.setImageResource(R.drawable.drink2_yellow);
        } else {
            cocktailName = "謎";
            imageCocktail.setImageResource(R.drawable.drink8_purple);
        }
        textCocktail.setText(cocktailName);

        //int[] getColor = getIntent().getIntArrayExtra("_color");

        //background = (TextView)findViewById(R.id.background);
        //background.setBackgroundColor(Color.rgb(getColor[0], getColor[1], getColor[2]));
        //setContentView(background);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Listenerの登録
        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Listenerを解除
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if ( sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER ) {
            sensorX = sensorEvent.values[0];
            sensorY = sensorEvent.values[1];
            sensorZ = sensorEvent.values[2];

//            String strTmp = "加速度センサー\n"
//                    + " X: " + sensorX + "\n"
//                    + " Y: " + sensorY + "\n"
//                    + " Z: " + sensorZ;
//            textAccel_2.setText(strTmp);

            if(2.0 < sensorX && -2.0 < sensorY && sensorY < 2.0 && 9.5 < sensorZ && sensorZ < 10.0) {
                slide_flag = true;
            } else if(slide_flag && sensorX < 0.4 && -2.0 < sensorY && sensorY < 2.0 && 9.5 < sensorZ && sensorZ < 10.0) {
                speachText();
                slide_flag = false;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onInit(int i) {
        // TTS初期化
        if(TextToSpeech.SUCCESS == i) {
            Log.d(TAG, "initialized");
        } else {
            Log.e(TAG, "failed to initialize");
        }
    }

    private void shutDown() {
        if(null != tts) {
            tts.shutdown();
        }
    }

    private void speachText() {
        String string = "あちらのお客様からです";
        if (0 < string.length()) {
            if (tts.isSpeaking()) {
                tts.stop();
                return;
            }
            setSpeechRate(1.0f);
            setSpeechPitch(1.0f);

            if (Build.VERSION.SDK_INT >= 21) {
                tts.speak(string, TextToSpeech.QUEUE_FLUSH, null, "messageID");
            } else {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "messageID");
                tts.speak(string, TextToSpeech.QUEUE_FLUSH, map);
            }

        }
    }

    // 読み上げのスピード
    private void setSpeechRate(float rate){
        if (null != tts) {
            tts.setSpeechRate(rate);
        }
    }

    // 読み上げのピッチ
    private void setSpeechPitch(float pitch){
        if (null != tts) {
            tts.setPitch(pitch);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        shutDown();
    }
}