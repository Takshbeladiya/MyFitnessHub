package com.example.myfitnesshub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class shopping_adapter extends FirebaseRecyclerAdapter<shopping_model, shopping_adapter.myViewHolder> {

    public shopping_adapter(@NonNull FirebaseRecyclerOptions<shopping_model> options) {
        super(options);
    }

    int qty;
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull shopping_model model) {
        holder.shopping_title.setText(model.getTitle());
        holder.shopping_rating.setText(model.getRating());
        holder.shopping_price.setText("Rs"+model.getPrice());
        Glide.with(holder.shopping_image.getContext())
                .load(model.getUrl())
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.error_image)
                .into(holder.shopping_image);

        holder.base_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qty = 0;

                FirebaseDatabase.getInstance().getReference().child("shopping_cart")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(GlobalVariable.name+"_"+model.getTitle())){
                            qty = Integer.parseInt(snapshot.child(GlobalVariable.name+"_"+model.getTitle()).child("quantity").getValue().toString());
                            AppCompatActivity activity = (AppCompatActivity) view.getContext();
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.shopping_wrapper, new shopping_product_info(model.getTitle(), model.getUrl(), model.getRating(), model.getColor(), model.getDescription(), model.getPrice(), qty))
                                    .addToBackStack(null)
                                    .commit();
                        }
                        else {
                            qty = 0;
                            AppCompatActivity activity = (AppCompatActivity) view.getContext();
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.shopping_wrapper, new shopping_product_info(model.getTitle(), model.getUrl(), model.getRating(), model.getColor(), model.getDescription(), model.getPrice(), qty))
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do not delete
                    }
                });



            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_slider_item, parent, false);
        return new shopping_adapter.myViewHolder(view);
    }


    public class myViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout base_card;
        ImageView shopping_image;
        TextView shopping_title, shopping_price, shopping_rating;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            shopping_image = (ImageView)itemView.findViewById(R.id.shopping_image);
            base_card = itemView.findViewById(R.id.base_card);
            shopping_title = itemView.findViewById(R.id.shopping_title);
            shopping_price = itemView.findViewById(R.id.shopping_price);
            shopping_rating = itemView.findViewById(R.id.shopping_rating);
        }
    }
}
