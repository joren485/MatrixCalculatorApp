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
        m.setPositionValue(2, 1, 9);
        m.setPositionValue(1, 1, 4);
        m.setPositionValue(0, 2, 7);
        m.setPositionValue(1, 0, 3);
        m.setPositionValue(0, 0, 5);
        m.setPositionValue(2, 2, 2);
        m.setPositionValue(3,3,3);

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
