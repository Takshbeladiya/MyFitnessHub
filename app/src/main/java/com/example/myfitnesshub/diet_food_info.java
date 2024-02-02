package com.example.myfitnesshub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_diet_food_info, container,false);

        return view;
    }
}