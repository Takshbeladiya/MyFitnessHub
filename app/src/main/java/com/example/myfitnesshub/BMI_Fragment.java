package com.example.myfitnesshub;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class BMI_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BMI_Fragment() {
        // Required empty public constructor
    }

    public static BMI_Fragment newInstance(String param1, String param2) {
        BMI_Fragment fragment = new BMI_Fragment();
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

    android.widget.Button mcalculator;

    TextView mcurrentheight;
    TextView mcurrentage,mcurrentweight;
    ImageView mincrementtage,mincrementweight,mdecrementweight,mdecrementage;
    SeekBar mseekbarforheight;
    RelativeLayout mmale,mfemale;

    int intweight=55;
    int intage=24;
    int currentprogress;
    String mintprogress="170";
    String typeofuser="0";
    String weight2="55";
    String age2="24";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_b_m_i_, container,
                false);


        // initiliaztion of variable
        mcalculator=view.findViewById(R.id.calculatebmi);
        mcurrentage=view.findViewById(R.id.currentage);
        mcurrentweight=view.findViewById(R.id.currentweight);
        mcurrentheight=view.findViewById(R.id.currentheight);
        mincrementtage=view.findViewById(R.id.incrementage);
        mdecrementage=view.findViewById(R.id.decrementage);
        mincrementweight=view.findViewById(R.id.incrementweight);
        mdecrementweight=view.findViewById(R.id.decrementweight);
        mseekbarforheight=view.findViewById(R.id.seekbarforheight);
        mmale=view.findViewById(R.id.male);
        mfemale=view.findViewById(R.id.female);

        main_logic();
        return view;
    }

    public void main_logic(){
        mmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mmale.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.malefemalefocues));
                mfemale.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.malefemalenotfocues));
                typeofuser="Male";
            }
        });

        mfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mfemale.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.malefemalefocues));
                mmale.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.malefemalenotfocues));
                typeofuser="Female";
            }
        });

        mseekbarforheight.setMax(300);
        mseekbarforheight.setProgress(170);
        mseekbarforheight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentprogress=progress;
                mintprogress=String.valueOf(currentprogress);
                mcurrentheight.setText(mintprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mincrementtage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intage=intage+1;
                age2=String.valueOf(intage);
                mcurrentage.setText(age2);
            }
        });

        mdecrementage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intage=intage-1;
                age2=String.valueOf(intage);
                mcurrentage.setText(age2);
            }
        });

        mincrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intweight=intweight+1;
                weight2=String.valueOf(intweight);
                mcurrentweight.setText(weight2);
            }
        });

        mdecrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intweight=intweight-1;
                weight2=String.valueOf(intweight);
                mcurrentweight.setText(weight2);
            }
        });

        mcalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(typeofuser.equals("0"))
                {
                    Toast.makeText(getContext(), "Select your gender first", Toast.LENGTH_SHORT).show();
                } else if (mintprogress.equals("0")) {
                    Toast.makeText(getContext(), "Select your height first", Toast.LENGTH_SHORT).show();
                } else if (intage==0 || intage<0) {
                    Toast.makeText(getContext(), "Age is incorrenct", Toast.LENGTH_SHORT).show();
                } else if (intweight==0 || intweight<0) {
                    Toast.makeText(getContext(), "Weight is Incorrect", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AppCompatActivity activity= (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.bmi_wrapper, new bmi_result(typeofuser, mintprogress, weight2, age2))
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }
    }