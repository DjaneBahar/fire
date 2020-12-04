package com.example.fire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Bundle;

public class HomeNavigation extends AppCompatActivity {
    private ImageView profileImage;
    private ImageView exercisesImage;
    private ImageView insightsImage;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_navigation);

        //init and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected

        bottomNavigationView.setSelectedItemId(R.id.home);

        //perform itemselectedlistener

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext()
                                , Settings.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
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


        profileImage = findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfile();

            }
        });

        exercisesImage = findViewById(R.id.exersies_image);
        exercisesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExercises();

            }
        });

        insightsImage = findViewById(R.id.insight_image);
        insightsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInsight();

            }
        });

    }


    private void openProfile() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);

    }

    private void openExercises() {

        Intent intent = new Intent(this, Exercises.class);
        startActivity(intent);

    }
    private void openInsight() {

        Intent intent = new Intent(this, Insights.class);
        startActivity(intent);
    }
    }