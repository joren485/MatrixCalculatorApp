package com.asserttrue.matrixcalculator.view.computationsTab;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;
import com.asserttrue.matrixcalculator.model.computations.Computations;

public class ComputationsFragment extends Fragment {
    private static ComputationsFragment ourInstance;

    public ComputationsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_computations, container, false);

        final LinearLayout cards = (LinearLayout) root.findViewById(R.id.computations_layout);

        final Matrix m = new Matrix(4, 4);
        m.setValue(2, 1, new Rational(9));
        m.setValue(1, 1, new Rational(4));
        m.setValue(0, 2, new Rational(7));
        m.setValue(1, 0, new Rational(3));
        m.setValue(0, 0, new Rational(5));
        m.setValue(2, 2, new Rational(2));
        m.setValue(3, 3, new Rational(3));


        cards.getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ComputationActivity.class);
                CurrentComputation.getInstance().setSteps(Computations.inverse(m));
                startActivity(intent);
            }
        });


       cards.getChildAt(0).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getContext(), ComputationActivity.class);
               CurrentComputation.getInstance().setSteps(Computations.determinant(m));
               startActivity(intent);
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
