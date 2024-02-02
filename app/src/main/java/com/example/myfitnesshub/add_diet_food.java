package com.example.myfitnesshub;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class add_diet_food extends AppCompatActivity {

    StorageReference storageReference;

    FirebaseStorage firebaseStorage;
    FirebaseFirestore firestore;

    FirebaseDatabase db;

    Task<Void> reference;
    String photoUrl;

    Uri image;
    ImageView imageView;
    EditText diet_title, diet_description, diet_calories, diet_cook_time;

    Button upload_image, select_image;

    FloatingActionButton history_button;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                if(result.getData() != null){
                    image = result.getData().getData();
                    upload_image.setEnabled(true);
                    Glide.with(getApplicationContext()).load(image).into(imageView);
                }
            } else {
                Toast.makeText(add_diet_food.this, "Please select and image", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet_food);

        diet_title = findViewById(R.id.diet_title);
        diet_description = findViewById(R.id.diet_description);
        diet_calories = findViewById(R.id.diet_calories);
        diet_cook_time = findViewById(R.id.diet_cook_time);
        imageView = findViewById(R.id.image_view);

        upload_image = findViewById(R.id.upload_image);
        select_image = findViewById(R.id.select_image);
        history_button = findViewById(R.id.history_button);


        // create firebase instances
        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        db = FirebaseDatabase.getInstance();


        history_btn();
        select_btn();
        upload_btn();
    }

    public void select_btn() {

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });

    }

    public void upload_btn() {
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

    }

    public void history_btn(){
        history_button = findViewById(R.id.history_button);
        history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(add_blog_post.this, blog_edit.class);
//                startActivity(intent);
            }
        });
    }



    public void uploadImage(){
        StorageReference myRef = storageReference.child("diet_photos/" + image.getLastPathSegment());
        myRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // uploading image to firebase database
                myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if(uri != null){
                            photoUrl = uri.toString();
                            upload_blog_data();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_diet_food.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(add_diet_food.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void upload_blog_data(){

        if(diet_title.getText().toString().equals("") || diet_description.getText().toString().equals("") || diet_calories.getText().toString().equals("") || diet_cook_time.getText().toString().equals("")){
            Toast.makeText(add_diet_food.this, "Fill all details", Toast.LENGTH_SHORT).show();
        } else {
            String current_user_name = GlobalVariable.name;
            String diet_title_txt = diet_title.getText().toString();
            String diet_description_txt = diet_description.getText().toString();
            int  diet_calories_txt = Integer.parseInt(diet_calories.getText().toString());
            String  diet_cook_time_txt = diet_cook_time.getText().toString();

            add_diet_model diet_model = new add_diet_model(diet_title_txt, current_user_name, diet_cook_time_txt, diet_description_txt, photoUrl, diet_calories_txt);
            reference = db.getReference("diet").child(diet_title_txt).setValue(diet_model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        if(task.isSuccessful()){
                            Toast.makeText(add_diet_food.this, "Diet Added", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(add_diet_food.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}