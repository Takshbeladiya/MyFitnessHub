package com.example.myfitnesshub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class client_body_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_body_info);

        EditText weight_box = findViewById(R.id.editTextTextPersonName2);
        EditText height_box = findViewById(R.id.editTextTextPersonName3);
        EditText age_box = findViewById(R.id.editTextTextPersonName4);
        Button submit = findViewById(R.id.button2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    int weight = Integer.parseInt(weight_box.getText().toString());
                    int height = Integer.parseInt(height_box.getText().toString());
                    int age = Integer.parseInt(age_box.getText().toString());

                    double calculate_calories_algo = ((10 * weight) + (6.25 * height) - (5 * age) + 700);
                    Toast.makeText(getApplicationContext(),"Calories Require : " + String.valueOf(calculate_calories_algo), Toast.LENGTH_LONG).show();




                    // add calories in database
                }
                catch(NumberFormatException e) {
                    Toast.makeText(client_body_info.this, "Enter interger only no String ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}