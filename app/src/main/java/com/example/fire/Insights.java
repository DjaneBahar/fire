package com.example.fire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Insights extends AppCompatActivity {

    EditText Height, Weight;
    TextView answer;
    Button calculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insights);

        //implementation of BMI calculator

        Height=(EditText) findViewById(R.id.Height);
        Weight=(EditText) findViewById(R.id.Weight);
        answer=(TextView) findViewById(R.id.answer);
        calculate=(Button) findViewById(R.id.calculate);
        final FirebaseDatabase[] database = {FirebaseDatabase.getInstance()}; //Get instance of database
        final DatabaseReference myRef = database[0].getReference("User"); //Get reference to certain spot in database, tror det er til når jeg prøvede at hente data. Også når jeg indsætter data.

        final SharedPreferences gemmeobjekt = PreferenceManager.getDefaultSharedPreferences(this);
        final String user = gemmeobjekt.getString("username", "");
        //Burde være gemt i bruger objekt, så vi kunne hente det derfra. Kunne lave brugerobjekt der kun indeholder det faktisk.


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateBMI();
                myRef.child(user).child("BMI").setValue(calculateBMI());
                //Error message if nothing typed
                String str1 = Weight.getText().toString();
                String str2 = Height.getText().toString();

                if(TextUtils.isEmpty(str1)){
                    Weight.setError("You need to enter your weight in order to calculate your BMI");
                    Weight.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(str2)){
                    Height.setError("You need to enter your height in order to calculate your BMI");
                    Height.requestFocus();
                    return;
                }

            }
        });


    }

    //Nedenstående kan nemt komme i egen klasse. Lave de andre ud fra det.
    private float calculateBMI(){
        String heightStr=Height.getText().toString();
        String weightStr=Weight.getText().toString();

        if(heightStr!=null && !"".equals(heightStr) && weightStr!=null &&!"".equals(weightStr)){
            float heightvalue=Float.parseFloat(heightStr)/100;
            float weightValue=Float.parseFloat(weightStr);

            float bmi =weightValue/(heightvalue * heightvalue);

            displayBMI(bmi);
            return bmi;
        }
        return 0;
    }
    private void displayBMI(float bmi){
        String bmiLabel="";

        if (Float.compare(bmi, 15f) <= 0) {
            bmiLabel = "Very severely underweight";
        } else if (Float.compare(bmi, 15f) > 0  &&  Float.compare(bmi, 16f) <= 0) {
            bmiLabel = "very underweight";
        } else if (Float.compare(bmi, 16f) > 0  &&  Float.compare(bmi, 18.5f) <= 0) {
            bmiLabel = "Underweight";
        } else if (Float.compare(bmi, 18.5f) > 0  &&  Float.compare(bmi, 25f) <= 0) {
            bmiLabel = "Normal";
        } else if (Float.compare(bmi, 25f) > 0  &&  Float.compare(bmi, 30f) <= 0) {
            bmiLabel = "Overweight";
        } else if (Float.compare(bmi, 30f) > 0  &&  Float.compare(bmi, 35f) <= 0) {
            bmiLabel = "Very Overweight";
        } else if (Float.compare(bmi, 35f) > 0  &&  Float.compare(bmi, 40f) <= 0) {
            bmiLabel = "Obese";
        } else {
            bmiLabel = "Fat fuck";
        }

        bmiLabel = bmi + "\n\n" + bmiLabel;
        answer.setText(bmiLabel);
    }
}