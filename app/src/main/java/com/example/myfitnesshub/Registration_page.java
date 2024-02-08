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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class Registration_page extends AppCompatActivity {

//    CustomerDatabaseClass databaseHelper;

    FirebaseDatabase db;

    DatabaseReference reference;

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
                EditText height_text = findViewById(R.id.height_editText);
                EditText weight_text = findViewById(R.id.weight_editText);
                EditText age_text = findViewById(R.id.age_editText);
                EditText address_text = findViewById(R.id.address_editText);

                String email = email_text.getText().toString();
                String password = password_text.getText().toString();
                String height = height_text.getText().toString();
                String weight = weight_text.getText().toString();
                String age = age_text.getText().toString();
                String address = address_text.getText().toString();

                if(email.equals("") || password.equals("") || age.equals("") || height.equals("") || weight.equals("") || address.equals("")){
                    Toast.makeText(Registration_page.this, "All fields are Mandatory", Toast.LENGTH_LONG).show();
                }
                else if(email.equals("")){
                    Toast.makeText(Registration_page.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals("")){
                    Toast.makeText(Registration_page.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                }
                else if(age.equals("")){
                    Toast.makeText(Registration_page.this, "Enter your Age", Toast.LENGTH_SHORT).show();
                }
                else if(height.equals("")){
                    Toast.makeText(Registration_page.this, "Enter your Height", Toast.LENGTH_SHORT).show();
                }
                else if(weight.equals("")){
                    Toast.makeText(Registration_page.this, "Enter your Weight", Toast.LENGTH_SHORT).show();
                }
                else if(address.equals("")){
                    Toast.makeText(Registration_page.this, "Enter your Address", Toast.LENGTH_SHORT).show();
                }
                else{
                    GlobalVariable.name = email;
                    String calories = String.valueOf((10 * Integer.parseInt(weight)) + (6.25 * Integer.parseInt(height)) - (5 * Integer.parseInt(age)) + 700);
                    user_data users_data = new user_data(email, password, age, height, weight, calories, address);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("user_data");
                    reference.child(email).setValue(users_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Registration_page.this, "Registration Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registration_page.this, Home_page.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }









//    public void add_new_user(){
//        Button register_btn = findViewById(R.id.submit_button);
//        databaseHelper = new CustomerDatabaseClass(this);
//
//        // when use click on submit pass and email checking if valid and if there Redirect to Main Page
//        register_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                EditText email_text = findViewById(R.id.name_editText);
//                EditText password_text = findViewById(R.id.password_editText);
//
//                String email = email_text.getText().toString();
//                String password = password_text.getText().toString();
//
//                if(email.equals("") || password.equals("")){
//                    Toast.makeText(Registration_page.this, "All fields are Mandatory", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Boolean checkUserEmail = databaseHelper.check_email(email);
//
//                    if(checkUserEmail){
//                        Toast.makeText(Registration_page.this, "User Already Exists, Please Login", Toast.LENGTH_SHORT).show();
//                    }else{
//                        databaseHelper.insertData(email, password);
//                        Toast.makeText(Registration_page.this, "Registration Success", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(Registration_page.this, client_body_info.class);
//                        startActivity(intent);
//                    }
//                }
//            }
//        });
//    }
    // Go back to login Page from Registration page   | Called when user click on "Already Registered? Login Page" |
    public void go_back_to_login(View v){
        Intent intent = new Intent(Registration_page.this, MainActivity.class);
        startActivity(intent);
    }
}