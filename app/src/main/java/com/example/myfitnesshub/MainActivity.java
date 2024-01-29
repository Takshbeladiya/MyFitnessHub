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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CustomerDatabaseClass databaseHelper;

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

        databaseHelper = new CustomerDatabaseClass(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = email_box.getText().toString();
                String password = password_box.getText().toString();

                FirebaseDatabase.getInstance().getReference().child("user_data")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            int user_search_switch = 0;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot data:snapshot.getChildren()){
                                    String email_temp = data.child("email").getValue().toString();
                                    String password_temp = data.child("password").getValue().toString();

                                    if(email.equals(email_temp) && password.equals(password_temp)){
                                        Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, Home_page.class);
                                        startActivity(intent);
                                        user_search_switch = 1;
                                    }
                                }
                                if(user_search_switch == 0){
                                    Toast.makeText(MainActivity.this, "Credential is wrong", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        });
    }

    // On clicking on New User ? User Redirected to Registration Page
    public void redirect_register_page(View v){
//        Intent intent = new Intent(MainActivity.this, Registration_page.class);
        Intent intent = new Intent(MainActivity.this, Home_page.class);
        startActivity(intent);
    }


}