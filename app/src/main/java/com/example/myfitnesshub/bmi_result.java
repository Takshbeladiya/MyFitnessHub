package com.example.myfitnesshub;

import static android.content.Intent.getIntent;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class bmi_result extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public bmi_result() {
        // Required empty public constructor
    }

    String heigh, weigh, age;
    String gender;

    //        heigh=intent.getStringExtra("height");
//        weigh=intent.getStringExtra("weight");

    public bmi_result(String typeofuser, String mintprogress, String weight2, String age2) {
        this.gender = typeofuser;
        this.heigh = mintprogress;
        this.weigh = weight2;
        this.age = age2;
    }

    public static bmi_result newInstance(String param1, String param2) {
        bmi_result fragment = new bmi_result();
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

    android.widget.Button mrecalculatebmi;

    TextView mbmidisplay,mbmicategory,mgender;
    Intent intent;
    ImageView mimageView;
    String mbmi;
    float intbmai;
    float intheight,intweight;
    RelativeLayout mbackground;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bmi_result, container,
                false);

        main_code();

        return view;
    }

    public void main_code(){
        androidx.appcompat.app.ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        androidx.appcompat.app.ActionBar actionBar1 = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar1 != null) {
            actionBar1.setTitle("Result");
        }
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#1E1D1D"));

        androidx.appcompat.app.ActionBar actionBar2 = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar2 != null) {
            actionBar2.setBackgroundDrawable(colorDrawable);
        }



        intent=getActivity().getIntent();
        mbmidisplay=view.findViewById(R.id.bmidisplay);
        mbmicategory=view.findViewById(R.id.bmicategory);
        mgender=view.findViewById(R.id.genderdisplay);
        mbackground=view.findViewById(R.id.contentlayout);
        mimageView=view.findViewById(R.id.imageview);
        mrecalculatebmi=view.findViewById(R.id.recalculatebmi);



        intheight=Float.parseFloat(heigh);
        intweight=Float.parseFloat((weigh));

        intheight=intheight/100;
        intbmai=intweight/(intheight*intheight);

        mbmi=Float.toString(intbmai);

        if(intbmai<16)
        {
            mbmicategory.setText("servere Thinness");
            mbackground.setBackgroundColor(Color.RED);
            mimageView.setImageResource(R.drawable.crosss);
        } else if (intbmai<16.9 && intbmai>16) {
            mbmicategory.setText("Moderate Thinness");
            mbackground.setBackgroundColor(Color.RED);
            mimageView.setImageResource(R.drawable.warning);
        }
        else if (intbmai<18.4 && intbmai>17) {
            mbmicategory.setText("Mild Thinness");
            mbackground.setBackgroundColor(Color.RED);
            mimageView.setImageResource(R.drawable.warning);
        }
        else if (intbmai<25 && intbmai>18.4) {
            mbmicategory.setText("normal");
            mbackground.setBackgroundColor(Color.YELLOW);
            mimageView.setImageResource(R.drawable.ok);
        }
        else if (intbmai<29.4 && intbmai>25) {
            mbmicategory.setText("overweight");
            mbackground.setBackgroundColor(Color.RED);
            mimageView.setImageResource(R.drawable.warning);
        }
        else
        {
            mbmicategory.setText("obese class 1");
            mbackground.setBackgroundColor(Color.RED);
            mimageView.setImageResource(R.drawable.warning);
        }

        mgender.setText(intent.getStringExtra("gender"));
        mbmidisplay.setText(mbmi);


        if (((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }

        mrecalculatebmi=view.findViewById(R.id.recalculatebmi);

        mrecalculatebmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity= (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.bmi_result_wrapper, new BMI_Fragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}