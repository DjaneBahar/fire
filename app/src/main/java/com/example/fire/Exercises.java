package com.example.fire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Exercises extends AppCompatActivity {
    TextView t1;
    Button b1;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        //implementing bottom navigationBar

        //init and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


       // final MediaPlayer lyd = MediaPlayer.create(this, R.raw.ready_2); //Create sound
       // lyd.start(); //Play sound


//Skal lige kigge på det her hjemme.

        RelativeLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();






        //perform itemselectedlistener

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext()
                                , Settings.class));
                        overridePendingTransition(0,0);
                        return true;



                    case R.id.notifications:
                        startActivity(new Intent(getApplicationContext()
                                ,Notifications.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });


        //start timer
        t1 = findViewById(R.id.textView);
        b1 = findViewById(R.id.button);

        countDownTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                t1.setText(millisUntilFinished/1000 + "sec left");
            }



            @Override
            public void onFinish() {

                t1.setText("time finish");
                Toast.makeText(Exercises.this,"finish",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Exercises.this, Pushups.class);
                startActivity(intent);
                finish();

            }


        };

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Exercises.this,"time start", Toast.LENGTH_SHORT).show();
                countDownTimer.start();
            }


        });
    }
}