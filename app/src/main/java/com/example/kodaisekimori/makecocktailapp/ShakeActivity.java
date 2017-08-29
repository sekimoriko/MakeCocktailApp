package com.example.kodaisekimori.makecocktailapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ShakeActivity extends AppCompatActivity implements SensorEventListener {

    private static final int FORCE_THRESHOLD = 350;
    private static final int TIME_THRESHOLD = 100;
    private static final int SHAKE_TIMEOUT = 500;
    private static final int SHAKE_DURATION = 100;
    private static final int SHAKE_COUNT = 3;

    private SensorManager sensorManager;
    private TextView textView, textInfo;
    private float sensorX;
    private float sensorY;
    private float sensorZ;
    private boolean flg = true;

    private float mLastX = -1.0f, mLastY = -1.0f, mLastZ = -1.0f;
    private long mLastTime;
    private Context mContext;
    private int mShakeCount = 0;
    private long mLastShake;
    private long mLastForce;

    private AudioAttributes audioAttributes;
    private SoundPool soundPool;

    private int shakeSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        textInfo = (TextView) findViewById(R.id.text_info);

        // Get an instance of the TextView
        textView = (TextView) findViewById(R.id.text_view);

        audioAttributes = new AudioAttributes.Builder()
                // USAGE_MEDIA
                // USAGE_GAME
                .setUsage(AudioAttributes.USAGE_GAME)
                // CONTENT_TYPE_MUSIC
                // CONTENT_TYPE_SPEECH, etc.
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                // ストリーム数に応じて
                .setMaxStreams(1)
                .build();

        //効果音をダウンロードしておく
        shakeSound = soundPool.load(this, R.raw.shake_01, 1);

        // load が終わったか確認する場合
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.d("debug","sampleId="+sampleId);
                Log.d("debug","status="+status);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Listenerの登録
        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
//        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
//        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);
//        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
    }

    //解除する
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

            String strTmp = "加速度センサー\n"
                    + " X: " + sensorX + "\n"
                    + " Y: " + sensorY + "\n"
                    + " Z: " + sensorZ;
            textView.setText(strTmp);

            if(flg){
                //showInfo(sensorEvent);
                //System.currentTimeMillisを使い現在時刻をミリ秒で取得。
                long now = System.currentTimeMillis();
//最後に動かしてから500ミリ秒経過していたら、連続していないのでカウントを0に戻す。
                if ( ( now - mLastForce ) > SHAKE_TIMEOUT ) {
                    mShakeCount = 0;
                }
//最後に振ってから100ミリ秒経っていたら以下の処理。
                if ( ( now - mLastTime ) > TIME_THRESHOLD ) {
                    long diff = now - mLastTime;
//端末の加速度から前回の加速度＝if ( speed > 350 )を引いたものをMath.absで絶対値にする。それを経過時間で割ったものに10000を掛けて10秒間でどれだけ加速したかを求めているよう。
/*
XYZ軸の概念は
http://seesaawiki.jp/w/moonlight_aska/d/%B2%C3%C2%AE%C5%D9%A5%BB%A5%F3%A5%B5%A1%BC%A4%CE%C3%CD%A4%F2%BC%E8%C6%C0%A4%B9%A4%EB
が参考になりました。
*/
                    float speed = Math.abs(sensorX + sensorY + sensorZ -
                                    mLastX - mLastY - mLastZ ) / diff * 10000;
/*350より大きい速度で、振られたのが3回目（以上）でかつ、最後にシェイクを検知してから100ミリ秒以上経っていたら
今の時間を残して、シェイク回数を0に戻す。onShakeメソッドを呼ぶ。
*/
                    if ( speed > FORCE_THRESHOLD ) {
                        if ( ( ++mShakeCount >= SHAKE_COUNT ) && now - mLastShake > SHAKE_DURATION ) {
                            mLastShake = now;
                            mShakeCount = 0;
                            onShake();
                        }
                        mLastForce = now;
                    }
                    mLastTime = now;
                    mLastX = sensorX;
                    mLastY = sensorY;
                    mLastZ = sensorZ;
                }
            }
        }
    }

    //振られたときの処理
    private void onShake() {
        soundPool.play(shakeSound, 1.0f, 1.0f, 0, 0, 1);
    }

    //加速度表示(デバッグ用)
    private void showInfo(SensorEvent event){
        String info = "Name: " + event.sensor.getName() + "\n";
        info += "Vendor: " + event.sensor.getVendor() + "\n";
        info += "Type: " + event.sensor.getType() + "\n";
        info += "StringType: " + event.sensor.getStringType()+ "\n";

        int data = event.sensor.getMinDelay();
        info += "Mindelay: "+String.valueOf(data) +" usec\n";

        data = event.sensor.getMaxDelay();
        info += "Maxdelay: "+String.valueOf(data) +" usec\n";

        data = event.sensor.getReportingMode();
        String stinfo = "unknown";
        if(data == 0){
            stinfo = "REPORTING_MODE_CONTINUOUS";
        }else if(data == 1){
            stinfo = "REPORTING_MODE_ON_CHANGE";
        }else if(data == 2){
            stinfo = "REPORTING_MODE_ONE_SHOT";
        }
        info += "ReportingMode: "+stinfo +" \n";

        float fData = event.sensor.getMaximumRange();
        info += "MaxRange: "+String.valueOf(fData) +" \n";

        fData = event.sensor.getResolution();
        info += "Resolution: "+String.valueOf(fData) +" m/s^2 \n";

        fData = event.sensor.getPower();
        info += "Power: "+String.valueOf(fData) +" mA\n";

        textInfo.setText(info);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
