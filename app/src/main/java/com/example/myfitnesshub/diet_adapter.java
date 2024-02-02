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

public class diet_adapter extends FirebaseRecyclerAdapter<diet_model, diet_adapter.myViewHolder> {

    public diet_adapter(@NonNull FirebaseRecyclerOptions<diet_model> options) {
        super(options);
    }

    int item_tot_count = 0;
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull diet_model model) {
        holder.diet_title.setText(model.getTitle());
        holder.diet_calories.setText(String.valueOf(model.getCalories()));
        holder.diet_timer.setText(model.getCook_time());
        Glide.with(holder.diet_image.getContext())
                .load(model.getImage_url())
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.error_image)
                .into(holder.diet_image);

        holder.base_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.diet_wrapper, new diet_food_info(model.getTitle(), model.getImage_url(), model.getCalories(), model.getCook_time()))
                        .addToBackStack(null)
                        .commit();
            }
        });

        holder.add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_tot_count += 1;
            }
        });

        holder.remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_tot_count -= 1;
            }
        });

        holder.item_count.setText(String.valueOf(item_tot_count));
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_viewpager_item, parent, false);
        return new diet_adapter.myViewHolder(view);
    }


    public class myViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout base_card;
        ImageView diet_image, add_btn, remove_btn;
        TextView diet_title, diet_timer, diet_calories, item_count;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            diet_image = (ImageView)itemView.findViewById(R.id.diet_image);
            base_card = itemView.findViewById(R.id.base_card);
            diet_title = itemView.findViewById(R.id.diet_title);
            diet_timer = itemView.findViewById(R.id.diet_timer);
            diet_calories = itemView.findViewById(R.id.diet_calories);
            add_btn = itemView.findViewById(R.id.add_btn);
            remove_btn = itemView.findViewById(R.id.remove_btn);
            item_count = itemView.findViewById(R.id.item_count);
        }
    }
}
