package com.example.fire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class Squat extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor acceleroMeter;
    private SensorEventListener acceleroSensorListener;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squat);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acceleroMeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(Squat.this, acceleroMeter,  sensorManager.SENSOR_DELAY_NORMAL);
        textview = (TextView) findViewById(R.id.textView69);


    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(acceleroSensorListener);
        super.onDestroy();
    }

    int reps = 0;
    boolean situp;
    double currentValue;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currentValue = sensorEvent.values[1];

        //i++;
        //System.out.println(i);

        //for(int i = 0; reps < 10; i++){
        if(/*sensorEvent.values[1] > 0 lastValue < 0.0 && */currentValue < 1.0) {
            situp = true;
            //int a = 1;
        }
        if(situp==true && currentValue > 8.0){
            //reps++;
            situp=false;
            reps++;
        }
//Bare fjerne sensorværdien, have et billede der ændrer sig, og et tal over. Timer under billedet, der måske kunne være rundt.
        textview.setText(reps);
        double lastValue = currentValue;

        if(reps == 2){
            //onStop();
            //onDestroy();
            Intent exercise3 = new Intent(Squat.this, Backbends.class);
            startActivity(exercise3);
            onStop();
        }
    }

    //System.out.println(sensorEvent.values[1]);

    //}

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onStop(){
        super.onStop();
        sensorManager.unregisterListener(Squat.this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

}