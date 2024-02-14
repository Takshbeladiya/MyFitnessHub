package com.example.myfitnesshub;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class shopping_product_info extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public shopping_product_info() {
        // Required empty public constructor
    }

    String title, url, rating, color, description;
    int price;

    public shopping_product_info(String title, String url, String rating, String color, String description, int price) {
        this.title = title;
        this.url = url;
        this.color = color;
        this.description = description;
        this.price = price;
        this.rating = rating;
    }


    public static shopping_product_info newInstance(String param1, String param2) {
        shopping_product_info fragment = new shopping_product_info();
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

    TextView product_title, product_price, product_color, product_rating, product_description, quantity_txt;
    ImageView product_img, add_btn, remove_btn;

    Button add_to_card_btn, show_card_btn;

    int qty;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shopping_product_info, container,
                false);

        product_title = view.findViewById(R.id.product_title);
        product_price = view.findViewById(R.id.product_price);
        product_color = view.findViewById(R.id.product_color);
        product_rating = view.findViewById(R.id.product_rating);
        product_description = view.findViewById(R.id.product_description);
        product_img = view.findViewById(R.id.product_img);
        quantity_txt = view.findViewById(R.id.quantity_txt);
        add_btn = view.findViewById(R.id.add_btn);
        remove_btn = view.findViewById(R.id.remove_btn);
        add_to_card_btn = view.findViewById(R.id.add_to_card_btn);
        show_card_btn = view.findViewById(R.id.show_card_btn);

        product_title.setText(title);
        product_price.setText(String.valueOf(price));
        product_rating.setText(rating);
        product_color.setText(color);
        product_description.setText(description);

        Glide.with(getContext())
                .load(url)
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.error_image)
                .into(product_img);

        add_remove_btn();
        return view;
    }

    public void add_remove_btn(){
        qty = Integer.valueOf(quantity_txt.getText().toString());


//        FirebaseDatabase.getInstance().getReference().child("shopping_cart")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.hasChild(GlobalVariable.name+"_"+title)){
//                            qty = Integer.parseInt(snapshot.child(GlobalVariable.name+"_"+title).child("quantity").getValue().toString());
//                        }
//                        else {
//                            qty = Integer.valueOf(quantity_txt.getText().toString());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        // do not delete
//                    }
//                });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = qty + 1;
                quantity_txt.setText(String.valueOf(qty));
            }
        });

        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qty >= 1){
                    qty = 0;
                    quantity_txt.setText(String.valueOf(qty));
                }
                else{
                    qty = qty - 1;
                    quantity_txt.setText(String.valueOf(qty));
                }
            }
        });

        add_to_card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shopping_cart_data shopping_data = new shopping_cart_data(title, GlobalVariable.name, qty);

                FirebaseDatabase.getInstance().getReference().child("shopping_cart").child(GlobalVariable.name+"_"+title).setValue(shopping_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Product Added into Cart", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.shopping_product_page_wrapper, new shopping_cart())
                        .addToBackStack(null)
                        .commit();
            }
        });

        show_card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.shopping_product_page_wrapper, new shopping_cart())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}