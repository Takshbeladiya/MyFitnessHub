package com.example.myfitnesshub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        holder.title_txt.setText(model.getTitle());
        holder.description_txt.setText(model.getDescription());

        Glide.with(holder.card_img.getContext())
                .load(model.getUrl())
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.error_image)
                .into(holder.card_img);

        holder.base_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.exercise_wrapper, new exercise_workout_info(model.getTitle(), model.getDescription()))
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
        ImageView card_img;
        TextView title_txt, description_txt;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            card_img = (ImageView)itemView.findViewById(R.id.card_img);
            description_txt = itemView.findViewById(R.id.description_txt);
            title_txt = itemView.findViewById(R.id.title_txt);
            base_card = itemView.findViewById(R.id.base_card);
        }
    }
}
