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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DietFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DietFragment() {
        // Required empty public constructor
    }

    public static DietFragment newInstance(String param1, String param2) {
        DietFragment fragment = new DietFragment();
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

    RecyclerView recyclerView;

    diet_adapter mainAdapter;

    TextView calorie_counter;

    FloatingActionButton floating_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_diet, container,false);

        recycle_view_data();
        calories_math();
        floating_btn_data();
        return view;
    }

    public void floating_btn_data(){
        floating_btn = view.findViewById(R.id.floatingActionButton);
        floating_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), add_diet_food.class);
                intent.putExtra("intent_from", "diet_fragment");
                startActivity(intent);
            }
        });
    }

    public void calories_math(){
        calorie_counter = view.findViewById(R.id.calorie_counter);

//        String curr_user = GlobalVariable.name;
        String curr_user = "taksh";
        FirebaseDatabase.getInstance().getReference().child("user_data").child(curr_user).child("calories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user_calories = snapshot.getValue().toString();
                calorie_counter.setText(user_calories + "+");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void recycle_view_data(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false));

        FirebaseRecyclerOptions<diet_model> options =
                new FirebaseRecyclerOptions.Builder<diet_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("diet"), diet_model.class)
                        .build();

        mainAdapter = new diet_adapter(options);
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