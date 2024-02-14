package com.example.myfitnesshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class register_calories extends AppCompatActivity {

    android.widget.Button mcalculator;

    TextView mcurrentheight;
    TextView mcurrentage, mcurrentweight;
    ImageView mincrementtage, mincrementweight, mdecrementweight, mdecrementage;
    SeekBar mseekbarforheight;
    RelativeLayout mmale, mfemale;

    int intweight = 55;
    int intage = 24;
    int currentprogress;
    String mintprogress = "170";
    String typeofuser = "0";
    String weight2 = "55";
    String age2 = "24", goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_calories);

        Intent intent = getIntent();
        goal = intent.getStringExtra("goal");

        // initiliaztion of variable
        mcalculator = findViewById(R.id.calculatebmi);
        mcurrentage = findViewById(R.id.currentage);
        mcurrentweight = findViewById(R.id.currentweight);
        mcurrentheight = findViewById(R.id.currentheight);
        mincrementtage = findViewById(R.id.incrementage);
        mdecrementage = findViewById(R.id.decrementage);
        mincrementweight = findViewById(R.id.incrementweight);
        mdecrementweight = findViewById(R.id.decrementweight);
        mseekbarforheight = findViewById(R.id.seekbarforheight);
        mmale = findViewById(R.id.male);
        mfemale = findViewById(R.id.female);

        main_logic();
    }

    public void main_logic() {
        mmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mmale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.malefemalefocues));
                mfemale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.malefemalenotfocues));
                typeofuser = "Male";
            }
        });

        mfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mfemale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.malefemalefocues));
                mmale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.malefemalenotfocues));
                typeofuser = "Female";
            }
        });

        mseekbarforheight.setMax(300);
        mseekbarforheight.setProgress(170);
        mseekbarforheight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentprogress = progress;
                mintprogress = String.valueOf(currentprogress);
                mcurrentheight.setText(mintprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mincrementtage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intage = intage + 1;
                age2 = String.valueOf(intage);
                mcurrentage.setText(age2);
            }
        });

        mdecrementage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intage = intage - 1;
                age2 = String.valueOf(intage);
                mcurrentage.setText(age2);
            }
        });

        mincrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intweight = intweight + 1;
                weight2 = String.valueOf(intweight);
                mcurrentweight.setText(weight2);
            }
        });

        mdecrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intweight = intweight - 1;
                weight2 = String.valueOf(intweight);
                mcurrentweight.setText(weight2);
            }
        });

        mcalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (typeofuser.equals("0")) {
                    Toast.makeText(getApplicationContext(), "Select your gender first", Toast.LENGTH_SHORT).show();
                } else if (mintprogress.equals("0")) {
                    Toast.makeText(getApplicationContext(), "Select your height first", Toast.LENGTH_SHORT).show();
                } else if (intage == 0 || intage < 0) {
                    Toast.makeText(getApplicationContext(), "Age is incorrect", Toast.LENGTH_SHORT).show();
                } else if (intweight == 0 || intweight < 0) {
                    Toast.makeText(getApplicationContext(), "Weight is Incorrect", Toast.LENGTH_SHORT).show();
                } else {
                    float intheight = Float.parseFloat(mintprogress);
                    float intweight = Float.parseFloat((weight2));

                    intheight = intheight / 100;
                    float intbmai = intweight / (intheight * intheight);

                    String bmi = Float.toString(intbmai);
                    String height = String.valueOf(intheight);
                    String weight = String.valueOf(intweight);

                    Intent intent = new Intent(register_calories.this, Registration_page.class);
                    intent.putExtra("bmi", bmi);
                    intent.putExtra("height", height);
                    intent.putExtra("weight", weight);
                    intent.putExtra("goal", goal);
                    intent.putExtra("age", age2);
                    startActivity(intent);
                }
            }
        });
    }
}