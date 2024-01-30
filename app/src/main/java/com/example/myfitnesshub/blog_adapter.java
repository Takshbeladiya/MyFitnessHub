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

public class blog_adapter extends FirebaseRecyclerAdapter<blog_model, blog_adapter.myViewHolder> {

    public blog_adapter(@NonNull FirebaseRecyclerOptions<blog_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull blog_model model) {
        holder.blog_title.setText(model.getTitle());
        holder.blog_from.setText("Blog from " + model.getUser_name());
        Glide.with(holder.blog_image.getContext())
                .load(model.getImage_url())
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.error_image)
                .into(holder.blog_image);

        holder.base_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.blog_wrapper, new blog_post_info(model.getTitle(), model.getImage_url(), model.getUser_name()))
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_post_item, parent, false);
        return new blog_adapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout base_card;
        ImageView blog_image;
        TextView blog_title, blog_from;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            blog_image = (ImageView)itemView.findViewById(R.id.blog_image);
            base_card = itemView.findViewById(R.id.base_card);
            blog_title = itemView.findViewById(R.id.blog_title);
            blog_from = itemView.findViewById(R.id.blog_from);
        }
    }
}
