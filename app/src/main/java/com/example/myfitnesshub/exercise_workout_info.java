package com.example.myfitnesshub;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class exercise_workout_info extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    String title, description, video_url;
    public exercise_workout_info() {
        // Required empty public constructor
    }

    public exercise_workout_info(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static exercise_workout_info newInstance(String param1, String param2) {
        exercise_workout_info fragment = new exercise_workout_info();
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

    String video_format, workout_info;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_exercise_workout_info, container, false);

        TextView title_holder = view.findViewById(R.id.title_box);
        TextView description_holder = view.findViewById(R.id.description_box);

        WebView webView = view.findViewById(R.id.web_view);


        FirebaseDatabase.getInstance().getReference().child("exercise")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String check_title = data.child("title").getValue().toString();
                            if(title.equals(check_title)){
                                video_url = data.child("video_url").getValue().toString();
                                workout_info = data.child("workout_info").getValue().toString();
                                video_format = video_url.substring(0, 14) + "\"100%\"" + video_url.substring(19, 27) + "\"100%\"" + video_url.substring(32, video_url.length());
                                webView.loadData(video_format, "text/html", "utf-8");
                                webView.getSettings().setJavaScriptEnabled(true);
                                webView.setWebChromeClient(new WebChromeClient());
                                description_holder.setText(workout_info);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do not delete
                    }
                });

        title_holder.setText(title);


        return view;
    }

    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.exercise_wrapper, new ExericseFragment())
                .addToBackStack(null)
                .commit();
    }
}