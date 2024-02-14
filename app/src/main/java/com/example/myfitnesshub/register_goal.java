package com.example.myfitnesshub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class register_goal extends AppCompatActivity {

    Button button;
    RadioButton goal_btn;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_goal);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                goal_btn = (RadioButton) findViewById(selectedId);
                if(selectedId==-1){
                    Toast.makeText(register_goal.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    String goal = goal_btn.getText().toString();
                    Intent intent = new Intent(register_goal.this, register_calories.class);
                    intent.putExtra("goal", goal);
                    startActivity(intent);
                }
        }
        });
    }
}