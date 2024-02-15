package com.example.myfitnesshub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class today_workout_page extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public today_workout_page() {
        // Required empty public constructor
    }

    public static today_workout_page newInstance(String param1, String param2) {
        today_workout_page fragment = new today_workout_page();
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

    ImageView imageView;

    RecyclerView recyclerView;

    today_workout_adapter mainAdapter;

    TextView goal_txt;
    View view;

    Button goal_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_today_workout_page, container, false);

        imageView = (ImageView) view.findViewById(R.id.exercise_img);
        goal_txt = view.findViewById(R.id.goal_txt);
        goal_btn = view.findViewById(R.id.goal_btn);

        goal_txt.setText("Exercise for " + GlobalVariable.goal);
        Glide.with(getContext())
                .load("https://media.post.rvohealth.io/wp-content/uploads/2020/02/man-exercising-plank-push-up-732x549-thumbnail.jpg")
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.error_image)
                .into(imageView);


        goal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        recycle_view_data();

        return view;
    }

    public void recycle_view_data(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false));


        FirebaseRecyclerOptions<today_workout_model> options =
                new FirebaseRecyclerOptions.Builder<today_workout_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("exercise_plan").child("monday").child(GlobalVariable.goal), today_workout_model.class)
                        .build();


        mainAdapter = new today_workout_adapter(options);
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
}