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
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class shopping_cart_adapter extends FirebaseRecyclerAdapter<shopping_cart_model, shopping_cart_adapter.myViewHolder> {

    public shopping_cart_adapter(@NonNull FirebaseRecyclerOptions<shopping_cart_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull shopping_cart_adapter.myViewHolder holder, int position, @NonNull shopping_cart_model model) {
        holder.product_name.setText(model.getProduct());
        holder.shopping_quantity.setText(String.valueOf(model.getQuantity()));

        FirebaseDatabase.getInstance().getReference().child("shopping").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    String product_title = data.child("title").getValue().toString();
                    if(product_title.equals(model.getProduct())){

                        // setting product image
                        Glide.with(holder.product_img.getContext())
                            .load(data.child("url").getValue().toString())
                            .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                            .error(R.drawable.error_image)
                            .into(holder.product_img);

                        // getting product price

                        int price = Integer.parseInt(data.child("price").getValue().toString());
                        holder.shopping_total.setText("Rs "+String.valueOf(price));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                activity.getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(, new shopping_product_info())
                                        .addToBackStack(null)
                                        .commit();

                                Toast.makeText(view.getContext(), "update", Toast.LENGTH_SHORT).show();
                                return true;

                            case R.id.delete_icon:
                                FirebaseDatabase.getInstance().getReference().child("shopping_cart").child(model.getUser_name()+"_"+model.getProduct()).removeValue();
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
    public shopping_cart_adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_cart_card_item, parent, false);
        return new shopping_cart_adapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout base_card;
        ImageView product_img;
        TextView product_name, shopping_quantity, shopping_total, buttonViewOption;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            product_img = (ImageView)itemView.findViewById(R.id.product_img);
            base_card = itemView.findViewById(R.id.base_card);
            product_name = itemView.findViewById(R.id.product_name);
            shopping_quantity = itemView.findViewById(R.id.shopping_quantity);
            shopping_total = itemView.findViewById(R.id.shopping_total);
            buttonViewOption = itemView.findViewById(R.id.textViewOptions);
        }
    }
}
