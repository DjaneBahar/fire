package com.example.fire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spark.submitbutton.SubmitButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText userName = findViewById(R.id.EditTextuserName);

        final EditText password = findViewById(R.id.EditTextPassword);

        SubmitButton login = (SubmitButton) findViewById(R.id.login2Home_btn);


        final SubmitButton HomeButton = (SubmitButton) findViewById(R.id.HomeButton);
        //HomeButton.setVisibility(View.INVISIBLE);

        final TextView display = findViewById(R.id.textViewShowData);

        final TextView register = findViewById(R.id.textViewRegister);
        final FirebaseDatabase[] database = {FirebaseDatabase.getInstance()}; //Get instance of database
        final DatabaseReference myRef = database[0].getReference("User"); //Get reference to certain spot in database, tror det er til når jeg prøvede at hente data. Også når jeg indsætter data.

        final SharedPreferences gemmeobjekt = PreferenceManager.getDefaultSharedPreferences(this);
        final String user = gemmeobjekt.getString("username", "");

        if(!user.isEmpty()){ //Hvis der er en bruger logget ind. Burde nok gøre det på den første side. Så kan man enten lave en bruger eller logge ind, hvis man ikke er det.
            Intent homeIntent = new Intent(MainActivity.this, Home.class);
            startActivity(homeIntent);
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "onclick";
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Submit");

                String str1 = userName.getText().toString();
                String str2 = password.getText().toString();


                if(TextUtils.isEmpty(str1)){
                    userName.setError("You need to enter a Username");
                    userName.requestFocus();


                    return;
                }

                if(TextUtils.isEmpty(str2)){
                    password.setError("You need to enter a password");
                    password.requestFocus();

                    return;

                }

                    //Tjek om brugernavn og kode stemmer overens.
                    //Bare se om den med child der er brugernavn har child med det password. Så bare sige username eller password forkert og ellers sig, velkommen + username

                    //database[0].


                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //display.setText(dataSnapshot.child(userName.getText().toString()).child("password").getValue().toString());
                            if(dataSnapshot.child(userName.getText().toString()).exists()) {
                                if (dataSnapshot.child(userName.getText().toString()).child("password").getValue().toString().equals(password.getText().toString())) {
                                    gemmeobjekt.edit().putString("username", userName.getText().toString()).commit();
                                    User thisUser = new User(gemmeobjekt.getString("username", "")); //Prøver at lave et brugerobjekt med brugernavnet, men tror singleton giver mening her, da vi skal bruge den samme bruger, men hellere vil have den fra rammen end harddisk.
                                    display.setText("You have inputtet a matching pair of username and password! :) welcome " + gemmeobjekt.getString("username", "User"));
                                  //  HomeButton.setVisibility(View.VISIBLE);

                                } else {
                                    display.setText("Wrong password. :(");
                                }
                            } else {
                                display.setText("Wrong Username and/or Password");
                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                //display.setText("you have attempted a login.");

            }
        });

        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(MainActivity.this, HomeNavigation.class);
                startActivity(homeIntent);
            }



        });

}
}