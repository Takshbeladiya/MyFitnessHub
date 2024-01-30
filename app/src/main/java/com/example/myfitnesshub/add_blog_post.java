package com.example.myfitnesshub;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class add_blog_post extends AppCompatActivity {

    StorageReference storageReference;

    FirebaseStorage firebaseStorage;
    FirebaseFirestore firestore;
    LinearProgressIndicator progressIndicator;
    Uri image;
    Button select_image, upload_image;
    ImageView imageView;
    Toolbar toolbar;
    public String photoUrl;
    Bitmap bitmap;
    EditText blog_text;

    DocumentReference DocId;

    FirebaseDatabase db;
    Task<Void> reference;
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
                Toast.makeText(add_blog_post.this, "Please select and image", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog_post);

        FirebaseApp.initializeApp(add_blog_post.this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressIndicator = findViewById(R.id.progress);
        imageView = findViewById(R.id.image_view);
        select_image = findViewById(R.id.select_image);
        upload_image = findViewById(R.id.upload_image);


        // create firebase instances
        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        db = FirebaseDatabase.getInstance();


        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
//                upload_blog_data();
            }
        });
    }

    public void uploadImage(){
        StorageReference myRef = storageReference.child("photos/" + image.getLastPathSegment());
        myRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // uploading image to firebase database
                myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if(uri != null){
                            photoUrl = uri.toString();
//                            Toast.makeText(add_blog_post.this, photoUrl.toString(), Toast.LENGTH_SHORT).show();
                            upload_blog_data();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_blog_post.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(add_blog_post.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


//    user_data users_data = new user_data(email, password, age, height, weight, calories);
//    db = FirebaseDatabase.getInstance();
//    reference = db.getReference("user_data");
//                    reference.child(email).setValue(users_data).addOnCompleteListener(new OnCompleteListener<Void>() {
//        @Override
//        public void onComplete(@NonNull Task<Void> task) {
//            Toast.makeText(Registration_page.this, "Registration Success", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(Registration_page.this, Home_page.class);
//            startActivity(intent);
//        }
//    });

    public void upload_blog_data(){
        blog_text = findViewById(R.id.blog_title);
        String current_user_name = GlobalVariable.name;
        String blog_string = blog_text.getText().toString();

        add_post_model blog_model = new add_post_model(blog_string, photoUrl, current_user_name);
        reference = db.getReference("blog").child(blog_string).setValue(blog_model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(task.isSuccessful()){
                        Toast.makeText(add_blog_post.this, "sucecs", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(add_blog_post.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
//    public void upload_blog_data(){
//        blog_text = findViewById(R.id.blog_title);
//        String current_user_name = GlobalVariable.name;
//        String blog_string = blog_text.getText().toString();
//
//        DocumentReference documentReference = firestore.collection("blog").document();
//        add_post_model blog_model = new add_post_model(blog_string, photoUrl, current_user_name);
//        documentReference.set(blog_model, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    if(task.isSuccessful()){
//                        Toast.makeText(add_blog_post.this, "sucecs", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(add_blog_post.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}