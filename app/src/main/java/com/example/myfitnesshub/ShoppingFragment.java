package com.example.myfitnesshub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ShoppingFragment() {
        // Required empty public constructor
    }

    public static ShoppingFragment newInstance(String param1, String param2) {
        ShoppingFragment fragment = new ShoppingFragment();
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

    SearchView searchView;
    shopping_adapter mainAdapter;

    RecyclerView recyclerView;
    ImageSlider main_slider;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shopping, container,
                false);

        // code of fetching and adapting data to slider
        slider_activity_data();

        recycle_view_data();

        TextView user_title = view.findViewById(R.id.user_title);
        user_title.setText("Hello "+GlobalVariable.name);
        return view;
    }

    public void recycle_view_data(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
//        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false));

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
//        gridLayoutManager.setSpanCount(2);
//        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        FirebaseRecyclerOptions<shopping_model> options =
                new FirebaseRecyclerOptions.Builder<shopping_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("shopping"), shopping_model.class)
                        .build();

        searchView = (SearchView) view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });

        mainAdapter = new shopping_adapter(options);
        recyclerView.setAdapter(mainAdapter);
    }

    private void txtSearch(String Str){
        FirebaseRecyclerOptions<shopping_model> options =
                new FirebaseRecyclerOptions.Builder<shopping_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("shopping").orderByChild("title").startAt(Str).endAt(Str+"~"), shopping_model.class)
                        .build();

        mainAdapter = new shopping_adapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }

    public void slider_activity_data(){
        main_slider = (ImageSlider) view.findViewById(R.id.image_slider);
        final List<SlideModel> remote_image = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("shopping_offer")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // this loop will take all children from database itinerary
                        for(DataSnapshot data:snapshot.getChildren()){

                            remote_image.add(new SlideModel(data.child("url").getValue().toString(),
                                    data.child("title").getValue().toString(),
                                    ScaleTypes.FIT
                            ));
                        }
                        main_slider.setImageList(remote_image, ScaleTypes.FIT);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Leave empty
                    }
                });
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