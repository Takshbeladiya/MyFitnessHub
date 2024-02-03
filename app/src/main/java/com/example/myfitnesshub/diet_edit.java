package com.example.myfitnesshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class diet_edit extends AppCompatActivity {

    diet_menu_adapter mainAdapter;
    RecyclerView recyclerView;

    TextView user_name;

    String curr_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_edit);

        user_name = findViewById(R.id.user_name);

        curr_user = GlobalVariable.name;

        // setting user_name in title
        user_name.setText(curr_user);

        recycle_view_data();

    }

    public void recycle_view_data(){
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        FirebaseRecyclerOptions<diet_model> options =
                new FirebaseRecyclerOptions.Builder<diet_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("diet").orderByChild("user_name").startAt(curr_user).endAt(curr_user+"~"),diet_model.class)
                        .build();

        mainAdapter = new diet_menu_adapter(options);
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