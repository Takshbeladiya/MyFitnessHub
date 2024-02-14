package com.example.myfitnesshub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfitnesshub.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;

public class Registration_page extends AppCompatActivity {

//    CustomerDatabaseClass databaseHelper;

    FirebaseDatabase db;

    DatabaseReference reference;

    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

//        add_new_user();

        public_new_user_firebase();

    }

    public void public_new_user_firebase(){
        Button register_btn = findViewById(R.id.submit_button);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText email_text = findViewById(R.id.email_editText);
                EditText password_text = findViewById(R.id.password_editText);
                EditText address_text = findViewById(R.id.address_editText);

                String name = email_text.getText().toString();
                String password = password_text.getText().toString();
                String address = address_text.getText().toString();

                if(name.equals("") || password.equals("") || address.equals("")){
                    Toast.makeText(Registration_page.this, "All fields are Mandatory", Toast.LENGTH_LONG).show();
                }
                else if(name.equals("")){
                    Toast.makeText(Registration_page.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals("")){
                    Toast.makeText(Registration_page.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                }
                else if(address.equals("")){
                    Toast.makeText(Registration_page.this, "Enter your Address", Toast.LENGTH_SHORT).show();
                }
                else{
                    GlobalVariable.name = name;
                    Intent intent = getIntent();
                    String bmi = intent.getStringExtra("bmi");
                    float height = Float.parseFloat(intent.getStringExtra("height").toString()); // cm
                    float weight = Float.parseFloat(intent.getStringExtra("weight").toString());
                    int age = Integer.parseInt(intent.getStringExtra("age").toString());
                    String goal = intent.getStringExtra("goal");
                    float calories = Float.parseFloat(String.valueOf((10 * weight) + (6.25 * height) - (5 * age) + 700));

                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    String curr_date = String.valueOf(dateFormat.format(date));

                    user_data users_data = new user_data(name, password, address, goal, curr_date, age, calories, weight, height);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("user_data");
                    count = 1;
                    FirebaseDatabase.getInstance().getReference().child("user_data")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot data:snapshot.getChildren()){
                                        String user_name = data.child("name").getValue().toString();
                                        if(name.equals(user_name)){
                                            Toast.makeText(Registration_page.this, "user name already exist", Toast.LENGTH_SHORT).show();
                                            count = 0;
                                        }
                                    }

                                    if(count == 1){
                                        reference.child(name).setValue(users_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                GlobalVariable.goal = goal;
                                                Toast.makeText(Registration_page.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Registration_page.this, register_splash_screen.class);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // do not delete
                                }
                    });
                }
            }
        });

    }

    // Go back to login Page from Registration page   | Called when user click on "Already Registered? Login Page" |
    public void go_back_to_login(View v){
        Intent intent = new Intent(Registration_page.this, MainActivity.class);
        startActivity(intent);
    }
}