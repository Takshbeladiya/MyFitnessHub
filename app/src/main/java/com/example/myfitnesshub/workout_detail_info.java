package com.example.myfitnesshub;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


public class workout_detail_info extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String name, image_url, exercise_type, duration, description;

    private String mParam1;
    private String mParam2;

    public workout_detail_info() {
        // Required empty public constructor
    }
    public workout_detail_info(String name, String image_url, String exercise_type, String duration, String description) {
        this.name = name;
        this.image_url = image_url;
        this.exercise_type = exercise_type;
        this.duration = duration;
        this.description = description;
    }

    public static workout_detail_info newInstance(String param1, String param2) {
        workout_detail_info fragment = new workout_detail_info();
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

    View view;

    TextView workout_title, exercise_duration, exercise_description;

    VideoView exercise_video;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_workout_detail_info, container, false);

        workout_title = view.findViewById(R.id.workout_title);
        exercise_duration = view.findViewById(R.id.exercise_duration);
        exercise_description = view.findViewById(R.id.exercise_description);
        exercise_video = view.findViewById(R.id.exercise_video);


        // setting video
        exercise_video.setVideoURI(Uri.parse(image_url));
        exercise_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                exercise_video.start();
            }
        });

        workout_title.setText(name);
        exercise_duration.setText(duration);
        exercise_description.setText(description);

        return view;
    }
}