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

public class shopping_adapter extends FirebaseRecyclerAdapter<shopping_model, shopping_adapter.myViewHolder> {

    public shopping_adapter(@NonNull FirebaseRecyclerOptions<shopping_model> options) {
        super(options);
    }

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

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.shopping_wrapper, new shopping_product_info(model.getTitle(), model.getUrl(), model.getRating(), model.getColor(), model.getDescription(), model.getPrice()))
                        .addToBackStack(null)
                        .commit();
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
