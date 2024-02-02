package com.example.myfitnesshub;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExericseFragment extends Fragment {

    public ExericseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    ImageSlider main_slider;
    FragmentActivity referenceActivity;
    View view;
    RecyclerView recyclerView;
    exercise_adapter mainAdapter;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Without this Search will not work | Allow to Acquire OptionMenu
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        referenceActivity = getActivity();
        view = inflater.inflate(R.layout.fragment_exericse, container,
                false);

        // code of fetching and adapting data to slider
        slider_activity_data();

        // All exercise Recycle view
        recycle_view_data();

        return view;
    }

    public void recycle_view_data(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false));


        FirebaseRecyclerOptions<exercise_model> options =
                new FirebaseRecyclerOptions.Builder<exercise_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("exercise"), exercise_model.class)
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

        mainAdapter = new exercise_adapter(options);
        recyclerView.setAdapter(mainAdapter);
    }


    private void txtSearch(String Str){
        FirebaseRecyclerOptions<exercise_model> options =
                new FirebaseRecyclerOptions.Builder<exercise_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("exercise").orderByChild("title").startAt(Str).endAt(Str+"~"), exercise_model.class)
                        .build();

        mainAdapter = new exercise_adapter(options);
        mainAdapter.startListening();
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

    public void slider_activity_data(){
        main_slider = (ImageSlider) view.findViewById(R.id.image_slider);
        final List<SlideModel> remote_image = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Slider")
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
}