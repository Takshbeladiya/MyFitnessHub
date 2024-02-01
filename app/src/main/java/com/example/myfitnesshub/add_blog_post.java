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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    Button select_image, upload_image, history_button;
    ImageView imageView;
    Toolbar toolbar;
    public String photoUrl;
    Bitmap bitmap;
    EditText blog_text, blog_description;

    DocumentReference DocId;

    String description;
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

        blog_text = findViewById(R.id.blog_title);
        blog_description = findViewById(R.id.blog_description);

        Intent intent = getIntent();
        if(intent.getStringExtra("intent_from").toString().equals("blog_update")){
            String title = intent.getStringExtra("title");
            // setting blog title if user come from update section
            blog_text.setText(title);

            // getting description of title
            FirebaseDatabase.getInstance().getReference("blog").child(title).child("blog_description").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String description = snapshot.getValue().toString();

                    // setting username in Edit Text of user name
                    blog_description.setText(description);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // do not delete
                }
            });

            // setting blog image if user come from update section
            Glide.with(getApplicationContext()).load(intent.getStringExtra("image_url")).into(imageView);

            // photo is already taken so button is enabled
            upload_image.setEnabled(true);
            Toast.makeText(add_blog_post.this, "update kar ", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(add_blog_post.this, "Add kar", Toast.LENGTH_SHORT).show();
        }

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
            }
        });

        history_button = findViewById(R.id.history_button);

        history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(add_blog_post.this, blog_edit.class);
                startActivity(intent);
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


    public void upload_blog_data(){
        String current_user_name = GlobalVariable.name;
        String blog_string = blog_text.getText().toString();
        String description = blog_description.getText().toString();

        add_post_model blog_model = new add_post_model(blog_string, photoUrl, current_user_name, description);
        reference = db.getReference("blog").child(blog_string).setValue(blog_model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(task.isSuccessful()){
                        Toast.makeText(add_blog_post.this, "blog Added", Toast.LENGTH_LONG).show();
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
}