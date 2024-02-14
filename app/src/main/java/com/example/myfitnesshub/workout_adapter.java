package com.example.myfitnesshub;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class workout_adapter extends FirebaseRecyclerAdapter<workout_model, workout_adapter.myViewHolder> {

    public workout_adapter(@NonNull FirebaseRecyclerOptions<workout_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull workout_model model) {
        holder.name_txt.setText(model.getName());
        holder.duration_txt.setText(model.getDuration());

        holder.exercise_img.setVideoURI(Uri.parse(model.getImage_url()));
        holder.exercise_img.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                holder.exercise_img.start();
            }
        });

        holder.base_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.exercise_wrapper, new workout_detail_info(model.getName(), model.getImage_url(), model.getExercise_type(), model.getDuration(), model.getDescription()))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_workout_item, parent, false);
        return new workout_adapter.myViewHolder(view);
    }


    public class myViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout base_card;
        VideoView exercise_img;
        TextView name_txt, duration_txt;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name_txt = itemView.findViewById(R.id.name_txt);
            base_card = itemView.findViewById(R.id.base_card);
            duration_txt = itemView.findViewById(R.id.duration_txt);
            exercise_img = itemView.findViewById(R.id.exercise_img);
        }
    }
}
