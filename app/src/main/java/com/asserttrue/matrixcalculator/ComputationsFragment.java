package com.asserttrue.matrixcalculator;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ComputationsFragment extends Fragment {
    private static ComputationsFragment ourInstance;


    public ComputationsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_computations, container, false);

        final LinearLayout cards = (LinearLayout) root.findViewById(R.id.computations_layout);
        cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                switch(cards.indexOfChild(v)) {
                    // TO DO
                    // START COMPUTATION ACTIVITY HERE

                    default: break;
                }
            }
        });

        return root;
    }

    public static ComputationsFragment getInstance() {
        if(ourInstance == null) {
            ourInstance = new ComputationsFragment();
        }
        return ourInstance;
    }
}
