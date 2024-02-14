package com.example.myfitnesshub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check if user is Valid if True Redirect To main page else Error
        check_user_Login_validity();
    }

    public void check_user_Login_validity(){
        EditText email_box = findViewById(R.id.editTextTextPersonName);
        EditText password_box = findViewById(R.id.editTextTextPassword);
        Button btn_login = findViewById(R.id.button);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = email_box.getText().toString();
                String password = password_box.getText().toString();

                FirebaseDatabase.getInstance().getReference().child("user_data")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            int user_search_switch = 0;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot data:snapshot.getChildren()){
                                    String email_temp = data.child("name").getValue().toString();
                                    String password_temp = data.child("password").getValue().toString();


                                    if(name.equals("") && password.equals("")){
                                        Toast.makeText(MainActivity.this, "All details are mandatory", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(name.equals("")){
                                        Toast.makeText(MainActivity.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(password.equals("")){
                                        Toast.makeText(MainActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(name.equals(email_temp) && password.equals(password_temp)){
                                        GlobalVariable.name = name;

                                        // finding user goal

                                        FirebaseDatabase.getInstance().getReference().child("user_data")
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for(DataSnapshot data:snapshot.getChildren()){
                                                            String user_name = data.child("name").getValue().toString();
                                                            if(name.equals(user_name)){
                                                                String goal = data.child("goal").getValue().toString();
                                                                GlobalVariable.goal = goal;
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        // do not delete
                                                    }
                                                });

                                        Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, Home_page.class);
                                        startActivity(intent);
                                        user_search_switch = 1;
                                    }
                                }
                                if(user_search_switch == 0 && !name.equals("") && !password.equals("")){
                                    Toast.makeText(MainActivity.this, "Credential is wrong", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // do not delete
                            }
                        });
            }
        });
    }

    // On clicking on New User ? User Redirected to Registration Page
    public void redirect_register_page(View v){
        Intent intent = new Intent(MainActivity.this, register_goal.class);
//        Intent intent = new Intent(MainActivity.this, Registration_page.class);
//        Intent intent = new Intent(MainActivity.this, Home_page.class);
        startActivity(intent);
    }


}