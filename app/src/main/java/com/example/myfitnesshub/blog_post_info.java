package com.example.myfitnesshub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class blog_post_info extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String title, image_url, user_name;

    private String mParam1;
    private String mParam2;

    public blog_post_info() {
        // Required empty public constructor
    }

    public blog_post_info(String title, String image_url, String user_name) {
        this.title = title;
        this.image_url = image_url;
        this.user_name = user_name;
    }

    public static blog_post_info newInstance(String param1, String param2) {
        blog_post_info fragment = new blog_post_info();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    TextView blog_title, blog_description;
    ImageView blog_image;

    String title_data, description_data, blog_image_data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_blog_post_info, container, false);

        blog_title = view.findViewById(R.id.blog_title);
        blog_description = view.findViewById(R.id.blog_description);
        blog_image = view.findViewById(R.id.blog_image);


        FirebaseDatabase.getInstance().getReference().child("blog")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            title_data = data.child("title").getValue().toString();
                            blog_image_data = data.child("image_url").getValue().toString();
                            if(title_data.equals(title)){
                                description_data = data.child("blog_description").getValue().toString();
                                Glide.with(view.getContext()).load(blog_image_data).into(blog_image);
                                blog_description.setText(description_data);
                                blog_title.setText(title_data);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do not delete
                    }
                });


        return view;
    }



    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.blog_wrapper, new BlogFragment())
                .addToBackStack(null)
                .commit();
    }
}