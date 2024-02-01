package com.example.myfitnesshub;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class blog_menu_adapter extends FirebaseRecyclerAdapter<blog_model, blog_menu_adapter.myViewHolder> {

    public blog_menu_adapter(@NonNull FirebaseRecyclerOptions<blog_model> options) {
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


        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(view.getContext(), holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.insert_update_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.update_icon:
                                Intent intent = new Intent(view.getContext(), add_blog_post.class);
                                intent.putExtra("intent_from", "blog_update");
                                intent.putExtra("title", model.getTitle());
                                intent.putExtra("image_url", model.getImage_url());
                                view.getContext().startActivity(intent);
//                                Toast.makeText(view.getContext(), "Edit", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.delete_icon:
                                FirebaseDatabase.getInstance().getReference().child("blog").child(model.getTitle()).removeValue();
                                Toast.makeText(view.getContext(), "delete", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }

                });
                //displaying the popup
                popup.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_post_menu_item, parent, false);
        return new blog_menu_adapter.myViewHolder(view);
    }


    public class myViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout base_card;
        ImageView blog_image;
        TextView blog_title, blog_from, buttonViewOption;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            blog_image = (ImageView)itemView.findViewById(R.id.blog_image);
            base_card = itemView.findViewById(R.id.base_card);
            blog_title = itemView.findViewById(R.id.blog_title);
            blog_from = itemView.findViewById(R.id.blog_from);
            buttonViewOption = itemView.findViewById(R.id.textViewOptions);
        }
    }
}
