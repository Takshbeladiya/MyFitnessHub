package com.example.myfitnesshub;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class today_workout_adapter extends FirebaseRecyclerAdapter<today_workout_model, today_workout_adapter.myViewHolder> {

    public today_workout_adapter(@NonNull FirebaseRecyclerOptions<today_workout_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull today_workout_model model) {
        holder.name_txt.setText(model.getName());

        FirebaseDatabase.getInstance().getReference().child("exercise").child(model.getName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String duration = snapshot.child("duration").getValue().toString();
                String img_url = snapshot.child("image_url").getValue().toString();

                holder.duration_txt.setText(duration);

                holder.exercise_img.setVideoURI(Uri.parse(img_url));
                holder.exercise_img.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                        holder.exercise_img.start();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_workout_item, parent, false);
        return new today_workout_adapter.myViewHolder(view);
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
