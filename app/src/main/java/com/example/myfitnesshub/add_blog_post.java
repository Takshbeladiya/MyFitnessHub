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
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class add_blog_post extends AppCompatActivity {

    StorageReference storageReference;
    LinearProgressIndicator progressIndicator;
    Uri image;
    MaterialButton select_image, upload_image;
    ImageView imageView;
    private Toolbar toolbar;
//    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//        @Override
//        public void onActivityResult(ActivityResult result) {
//            if(result.getResultCode() == RESULT_OK){
//                if(result.getData() != null){
//                    image = result.getData().getData();
//                    upload_image.setEnabled(true);
//                    Glide.with(getApplicationContext()).load(image).into(imageView);
//                }
//            } else {
//                Toast.makeText(add_blog_post.this, "Please select and image", Toast.LENGTH_SHORT).show();
//            }
//        }
//    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog_post);

//        FirebaseApp.initializeApp(add_blog_post.this);
//        storageReference = FirebaseStorage.getInstance().getReference();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressIndicator = findViewById(R.id.progress);

        imageView = findViewById(R.id.image_view);
        select_image = findViewById(R.id.select_image);
        upload_image = findViewById(R.id.upload_image);

//        select_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                activityResultLauncher.launch(intent);
//            }
//        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                uploadImage(image);
            }
        });
    }

    public void uploadImage(Uri file) {
//        StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
//        ref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(add_blog_post.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(add_blog_post.this, "Failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                progressIndicator.setMax(Math.toIntExact(taskSnapshot.getTotalByteCount()));
//                progressIndicator.setProgress(Math.toIntExact(taskSnapshot.getBytesTransferred()));
//            }
//        });
    }
}