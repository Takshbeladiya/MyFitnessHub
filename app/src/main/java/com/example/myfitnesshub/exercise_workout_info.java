package com.example.myfitnesshub;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class exercise_workout_info extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    String name, image_url, exercise_type, type;
    public exercise_workout_info() {
        // Required empty public constructor
    }


    public exercise_workout_info(String name, String image_url, String exercise_type, String type) {
        this.name = name;
        this.image_url = image_url;
        this.exercise_type = exercise_type;
        this.type = type;
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

    RecyclerView recyclerView;
    ImageView exercise_img;
    TextView name_box;

    workout_adapter mainAdapter;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_exercise_workout_info, container, false);

        exercise_img = view.findViewById(R.id.exercise_img);
        name_box = view.findViewById(R.id.name_box);

        name_box.setText(name);

        Glide.with(getContext())
                .load(image_url)
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.error_image)
                .into(exercise_img);

//      All exercise Recycle view
        recycle_view_data();

        return view;
    }

    public void recycle_view_data(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false));


        FirebaseRecyclerOptions<workout_model> options =
                new FirebaseRecyclerOptions.Builder<workout_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("exercise").orderByChild("exercise_type").equalTo(exercise_type), workout_model.class)
                        .build();


        mainAdapter = new workout_adapter(options);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

//    public void onBackPressed(){
//        AppCompatActivity activity = (AppCompatActivity)getContext();
//        activity.getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.info_wrapper, new ExericseFragment())
//                .addToBackStack(null)
//                .commit();
//    }
}