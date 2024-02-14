package com.example.myfitnesshub;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class exercise_adapter extends FirebaseRecyclerAdapter<exercise_model, exercise_adapter.myViewHolder> {

    public exercise_adapter(@NonNull FirebaseRecyclerOptions<exercise_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull exercise_model model) {
        holder.name_txt.setText(model.getName());
        holder.time_txt.setText(model.getTime());
        Glide.with(holder.exercise_img.getContext())
                .load(model.getImage_url())
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.error_image)
                .into(holder.exercise_img);


        holder.base_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.exercise_wrapper, new exercise_workout_info(model.getName(), model.getImage_url(), model.getExercise_type(), model.getType()))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_slider_item, parent, false);
        return new myViewHolder(view);
    }



    public class myViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout base_card;
        ImageView exercise_img;
        TextView name_txt, time_txt;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name_txt = itemView.findViewById(R.id.name_txt);
            base_card = itemView.findViewById(R.id.base_card);
            time_txt = itemView.findViewById(R.id.time_txt);
            exercise_img = itemView.findViewById(R.id.exercise_img);
        }
    }
}
