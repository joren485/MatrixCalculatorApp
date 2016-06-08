package com.asserttrue.matrixcalculator.view.computationsTab;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asserttrue.matrixcalculator.R;

public class ComputationsFragment extends Fragment {

    private static final String[] COMPUTATIONS  =
            {"transpose", "sum", "product", "scalar mult", "determinant", "inverse", "kernel", "exponent", "rref"};

    public ComputationsFragment() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_computations, container, false);

        final LinearLayout cards = (LinearLayout) root.findViewById(R.id.computations_layout);

        for(int i = 0; i < cards.getChildCount(); i++) {
            final int index = i;

            cards.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ChooseMatrixActivity.class);

                    intent.putExtra("numMatrices", (index == 1 || index == 2) ? 2 : 1);
                    intent.putExtra("computation", COMPUTATIONS[index]);

                    startActivity(intent);
                }
            });

            cards.getChildAt(i).findViewById(R.id.more_info_button).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), FormulaInfoActivity.class);

                    intent.putExtra("computation", COMPUTATIONS[index]);
                    System.out.println(COMPUTATIONS[index]);
                    startActivity(intent);
                }
            });
        }

        return root;
    }
}
