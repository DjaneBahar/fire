package com.example.fire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;


public class Pushups extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;
    CountDownTimer countDownTimer;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushups);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        final TextView textview=(TextView) findViewById(R.id.textView);
        final TextView pushupTimer = findViewById(R.id.pushupTimer);

        if(proximitySensor == null){
            Toast.makeText(this, "Proximity sensor not available !", Toast.LENGTH_LONG).show();
            finish();
        }

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                pushupTimer.setText(millisUntilFinished/1000 + " Seconds left");
            }

            @Override
            public void onFinish() {
                Toast.makeText(Pushups.this,"finish",Toast.LENGTH_SHORT).show();
                Intent exercise2 = new Intent(Pushups.this, Situp.class);
                startActivity(exercise2);
                onStop();
                finish();
            }
        };

        Toast.makeText(Pushups.this,"time start", Toast.LENGTH_SHORT).show();
        countDownTimer.start(); //Skal måske rykkes ned til metoden?

        proximitySensorListener = new SensorEventListener() {
            int reps = 0;
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if (sensorEvent.values[0] < proximitySensor.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    reps++;
                } else {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
                System.out.println(reps);
                //Bare fjerne sensorværdien, have et billede der ændrer sig, og et tal over. Timer under billedet, der måske kunne være rundt.
                textview.setText(String.valueOf(reps));
                /*
                if(reps == 2){
                    //onStop();
                    //onDestroy();

                    Intent exercise2 = new Intent(Pushups.this, Situp.class);
                    startActivity(exercise2);
                    onStop();
                }
                 */
            }



            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(proximitySensorListener, proximitySensor, 2*1000*1000, 1000);
    }
    @Override
    protected void onStop(){
        super.onStop();
        sensorManager.unregisterListener(proximitySensorListener);
    }
    }