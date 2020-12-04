package com.example.fire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spark.submitbutton.SubmitButton;

public class Register extends AppCompatActivity {
    boolean userIs = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final MediaPlayer lyd = MediaPlayer.create(this, R.raw.yes); //Create sound

        // final Button playsound = this.findViewById(R.id.play_sound); //Get the button

        final TextView showData = this.findViewById(R.id.show_data); //get the textview.

        final ImageView img = this.findViewById(R.id.imageView);

        final EditText username = this.findViewById(R.id.TextUserName);

        final EditText password = this.findViewById(R.id.TextPassword);

        final SubmitButton registerUserBtn = (SubmitButton) findViewById(R.id.registerBtn);

        final SubmitButton goToLoginBtn = (SubmitButton) findViewById(R.id.go2LoginBtn);
        //goToLoginBtn.setVisibility(View.INVISIBLE);


        goToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "onclick";

            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: Submit");

                String str1 = username.getText().toString();
                String str2 = password.getText().toString();

                if(TextUtils.isEmpty(str1)){
                    username.setError("You need to enter a Username");
                    username.requestFocus();
                    lyd.start(); //Play sound

                    return;
                }

                if(TextUtils.isEmpty(str2)){
                    password.setError("You need to enter a password");
                    password.requestFocus();
                    lyd.start(); //Play sound
                    return;

                }

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(5000);
                            }catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    thread.start();


                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }



        });

        img.setImageResource(R.drawable.profilevector);

        final FirebaseDatabase[] database = {FirebaseDatabase.getInstance()}; //Get instance of database
        final DatabaseReference myRef = database[0].getReference("User"); //Get reference to certain spot in database, tror det er til når jeg prøvede at hente data. Også når jeg indsætter data.

        //Skrive på harddisk, gemme hvem der er login. Kunne også gemme password i guess, for at tjekke hashcode og sådan.
        final SharedPreferences gemmeobjekt = PreferenceManager.getDefaultSharedPreferences(this);
        final String user = gemmeobjekt.getString("Username", "");

        if(user.isEmpty()){
            showData.setText("Choose Username and Password." /*+ user*/ );
            //gemmeobjekt.edit().remove("username").apply();
        } else {
            Intent loggedIn = new Intent(Register.this, HomeNavigation.class);
            startActivity(loggedIn);
        }

/*
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String a= dataSnapshot.getValue().toString();
                if(dataSnapshot.child("4").exists()) { //Kan se om en bestemt bruger eksisterer. Kan bruges når vi skal oprette ny bruger. Hvis den ikke eksisterer, tilføjer vi data, ved at sige setValue, ved et bestemt child.
                    showData.setText("has aids");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

 */



        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "onclick";

            @Override
            public void onClick(View view) { //When button playsound is clicked.
                Log.d(TAG, "onClick: Submit");
                final boolean[] userExists = {false};


               // lyd.start(); //Play sound

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //String a= dataSnapshot.getValue().toString();
                        if(dataSnapshot.child(username.getText().toString()).exists()) { //Kan se om en bestemt bruger eksisterer. Kan bruges når vi skal oprette ny bruger. Hvis den ikke eksisterer, tilføjer vi data, ved at sige setValue, ved et bestemt child.
                            showData.setText("Username Taken"); //Den kan nu se at brugernavnet er taget, skal så bare stoppe den fra at lave det nye.
                            //Tror dEN ENE AF nedenstående kan slettes.
                            userExists[0] = true;
                            userIs = true;


                        }

                        if(userIs == false) {
                            myRef.child(username.getText().toString()).child("Password").setValue(password.getText().toString()); //Send data to database
                            showData.setText("User Created");
                            //loginBtn.setVisibility(View.VISIBLE);
                            goToLoginBtn.setVisibility(View.VISIBLE);


                        }

                        userIs = false;


                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {

                    }
                });

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            //String greeting = dataSnapshot.child("message").child("childmessage").getValue().toString();
                            String greeting = dataSnapshot.getValue().toString();
                            /*
                            if(!dataSnapshot.getValue().toString().equals(null)){
                                System.out.println("wagwan blud, no user");
                                showData.setText("no User");
                            }
                             */
                            //showData.setText(greeting);
                          //    img.setImageResource(R.drawable.lol);
                            //img.setVisibility(View.INVISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {

                    }
                });




            }
        });



    }
}

