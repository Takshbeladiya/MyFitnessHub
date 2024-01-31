package com.example.myfitnesshub;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlogFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BlogFragment() {
        // Required empty public constructor
    }
    public static BlogFragment newInstance(String param1, String param2) {
        BlogFragment fragment = new BlogFragment();
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

    FragmentActivity referenceActivity;
    View view;
    RecyclerView recyclerView;
    blog_adapter mainAdapter;
    SearchView searchView;

//    SearchView searchView;
    FloatingActionButton button;

    SearchView search_view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Without this Search will not work | Allow to Acquire OptionMenu
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        referenceActivity = getActivity();
        view = inflater.inflate(R.layout.fragment_blog, container,
                false);

        recycle_view_data();

        add_blog_event();
        return view;
    }

    public void add_blog_event(){
        button = view.findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), add_blog_post.class);
                startActivity(intent);
            }
        });
    }
    public void recycle_view_data(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        FirebaseRecyclerOptions<blog_model> options =
                new FirebaseRecyclerOptions.Builder<blog_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("blog"), blog_model.class)
                        .build();

        search_view = (SearchView) view.findViewById(R.id.search_view);
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

        mainAdapter = new blog_adapter(options);
        recyclerView.setAdapter(mainAdapter);
    }

    private void txtSearch(String Str){
        FirebaseRecyclerOptions<blog_model> options =
                new FirebaseRecyclerOptions.Builder<blog_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("blog").orderByChild("title").startAt(Str).endAt(Str+"~"), blog_model.class)
                        .build();

        mainAdapter = new blog_adapter(options);
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
}