package com.example.myfitnesshub;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class diet_menu_adapter extends FirebaseRecyclerAdapter<diet_model, diet_menu_adapter.myViewHolder> {

    public diet_menu_adapter(@NonNull FirebaseRecyclerOptions<diet_model> options) {
        super(options);
    }

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
                                Intent intent = new Intent(view.getContext(), add_diet_food.class);
                                intent.putExtra("intent_from", "diet_update");
                                intent.putExtra("title", model.getTitle());
//                                intent.putExtra("image_url", model.getImage_url());
                                view.getContext().startActivity(intent);
//                                Toast.makeText(view.getContext(), "Edit", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.delete_icon:
                                FirebaseDatabase.getInstance().getReference().child("diet").child(model.getTitle()).removeValue();
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_viewpager_menu_item, parent, false);
        return new diet_menu_adapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout base_card;
        ImageView diet_image;
        TextView diet_title, diet_timer, diet_calories, buttonViewOption;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            diet_image = (ImageView)itemView.findViewById(R.id.diet_image);
            base_card = itemView.findViewById(R.id.base_card);
            diet_title = itemView.findViewById(R.id.diet_title);
            diet_timer = itemView.findViewById(R.id.diet_timer);
            diet_calories = itemView.findViewById(R.id.diet_calories);
            buttonViewOption = itemView.findViewById(R.id.textViewOptions);
        }
    }

}
