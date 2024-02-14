package com.example.myfitnesshub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class shopping_cart extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public shopping_cart() {
        // Required empty public constructor
    }

    public static shopping_cart newInstance(String param1, String param2) {
        shopping_cart fragment = new shopping_cart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;

    RecyclerView recyclerView;

    TextView product_total, shopping_total;

    shopping_cart_adapter mainAdapter;

    Button shopping_payment_btn;

    int total = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_shopping_cart, container,
                false);

        product_total = view.findViewById(R.id.product_total);
        shopping_total = view.findViewById(R.id.shopping_total);

        // All exercise Recycle view
        recycle_view_data();

        //finding gst and total
        total_amount();

        return view;
    }

    public void total_amount(){
        FirebaseDatabase.getInstance().getReference().child("shopping_cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    String curr_name = data.child("user_name").getValue().toString();
                    if(curr_name.equals(GlobalVariable.name)){
                        String product = data.child("product").getValue().toString();
                        int quantity = Integer.parseInt(data.child("quantity").getValue().toString());

                        FirebaseDatabase.getInstance().getReference().child("shopping").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot data:snapshot.getChildren()){
                                    String product_title = data.child("title").getValue().toString();
                                    if(product_title.equals(product)){
                                        total = (Integer.parseInt(data.child("price").getValue().toString()) * quantity) + total;
                                        product_total.setText("Rs"+String.valueOf(total));
                                        int overall = total + 49 + 19;
                                        shopping_total.setText("Rs"+String.valueOf(overall));

//                                        shopping_payment_btn.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                // we are calling method to make payment.
//                                                makePayment(overall, upi, name, desc, transcId);
//                                            }
//                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//    public void makePayment(String amount, String upi, String name, String desc, String transactionId) {
//        // on below line we are calling an easy payment method and passing
//        // all parameters to it such as upi id,name, description and others.
//        final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
//                .with(this)
//                // on below line we are adding upi id.
//                .setPayeeVpa(upi)
//                // on below line we are setting name to which we are making payment.
//                .setPayeeName(name)
//                // on below line we are passing transaction id.
//                .setTransactionId(transactionId)
//                // on below line we are passing transaction ref id.
//                .setTransactionRefId(transactionId)
//                // on below line we are adding description to payment.
//                .setDescription(desc)
//                // on below line we are passing amount which is being paid.
//                .setAmount(amount)
//                // on below line we are calling a build method to build this ui.
//                .build();
//        // on below line we are calling a start
//        // payment method to start a payment.
//        easyUpiPayment.startPayment();
//        // on below line we are calling a set payment
//        // status listener method to call other payment methods.
//        easyUpiPayment.setPaymentStatusListener(this);
//    }
//
//    @Override
//    public void onTransactionCompleted(TransactionDetails transactionDetails) {
//        // on below line we are getting details about transaction when completed.
//        String transcDetails = transactionDetails.getStatus().toString() + "\n" + "Transaction ID : " + transactionDetails.getTransactionId();
//        transactionDetailsTV.setVisibility(View.VISIBLE);
//        // on below line we are setting details to our text view.
//        transactionDetailsTV.setText(transcDetails);
//    }
//
//    @Override
//    public void onTransactionSuccess() {
//        // this method is called when transaction is successful and we are displaying a toast message.
//        Toast.makeText(shopping_cart.this, "Transaction successfully completed..", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onTransactionSubmitted() {
//        // this method is called when transaction is done
//        // but it may be successful or failure.
//        Log.e("TAG", "TRANSACTION SUBMIT");
//    }
//
//    @Override
//    public void onTransactionFailed() {
//        // this method is called when transaction is failure.
//        Toast.makeText(this, "Failed to complete transaction", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onTransactionCancelled() {
//        // this method is called when transaction is cancelled.
//        Toast.makeText(this, "Transaction cancelled..", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onAppNotFound() {
//        // this method is called when the users device is not having any app installed for making payment.
//        Toast.makeText(this, "No app found for making transaction..", Toast.LENGTH_SHORT).show();
//    }


    public void recycle_view_data(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false));


        FirebaseRecyclerOptions<shopping_cart_model> options =
                new FirebaseRecyclerOptions.Builder<shopping_cart_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("shopping_cart"), shopping_cart_model.class)
                        .build();


        mainAdapter = new shopping_cart_adapter(options);
        recyclerView.setAdapter(mainAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}