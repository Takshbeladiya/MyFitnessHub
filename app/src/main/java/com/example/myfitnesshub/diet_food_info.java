package com.example.myfitnesshub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class diet_food_info extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public diet_food_info() {
        // Required empty public constructor
    }

    String  title, image_url, cook_time;
    int calories;
    public diet_food_info(String title, String image_url, int calories, String cook_time) {
        this.title = title;
        this.image_url = image_url;
        this.calories = calories;
        this.cook_time = cook_time;
    }

    public static diet_food_info newInstance(String param1, String param2) {
        diet_food_info fragment = new diet_food_info();
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

    TextView diet_title, diet_description, diet_cook_time, diet_calories;
    ImageView diet_image;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_diet_food_info, container,false);

        diet_title = view.findViewById(R.id.diet_title);
        diet_description = view.findViewById(R.id.diet_description);
        diet_cook_time = view.findViewById(R.id.diet_cook_time);
        diet_calories = view.findViewById(R.id.diet_calories);
        diet_image = view.findViewById(R.id.diet_image);

        diet_title.setText(title);
        diet_calories.setText(String.valueOf(calories));
        diet_cook_time.setText(cook_time);
        Glide.with(view.getContext()).load(image_url).into(diet_image);

        FirebaseDatabase.getInstance().getReference().child("diet")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String title_data = data.child("title").getValue().toString();
                            if(title_data.equals(title)){
                                String description_data = data.child("description").getValue().toString();
                                diet_description.setText(description_data);
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
}