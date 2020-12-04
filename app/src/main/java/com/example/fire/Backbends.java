package com.example.fire;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class Backbends extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backbends);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        final TextView textview=(TextView) findViewById(R.id.textView4);

        if(proximitySensor == null){
            Toast.makeText(this, "Proximity sensor not available !", Toast.LENGTH_LONG).show();
            finish();
        }

        proximitySensorListener = new SensorEventListener() {
            int reps = 0;
            boolean rep;

            //Fungerende med rygbøjninger.
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float currentValue = sensorEvent.values[0];
                System.out.println(currentValue); // Skal bare lige se hvad værdierne er


                if(currentValue == 0.0){
                    rep = false;
                }
                if (currentValue == 5.0 && rep == false){
                    reps++;
                    rep = true;
                }
                System.out.println(reps);
                //Bare fjerne sensorværdien, have et billede der ændrer sig, og et tal over. Timer under billedet, der måske kunne være rundt.
                textview.setText(String.valueOf(reps - 1)); //-1, fordi den starter på 1 af en eller anden grund.

                if ((reps - 1) == 2){
                    //onStop();
                    //Burde nok tilføje en slutskærm, hvor man viser reps og sådan
                    Intent workoutDone = new Intent(Backbends.this, HomeNavigation.class);
                    startActivity(workoutDone);
                    onStop();
                }
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