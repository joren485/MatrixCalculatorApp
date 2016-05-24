package com.asserttrue.matrixcalculator.view.storedMatricesTab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.view.LibraryMatrixView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoredMatricesFragment extends Fragment {
    private static StoredMatricesFragment ourInstance;

    public StoredMatricesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_storedmatrices, container, false);

        final Matrix m = new Matrix(4, 4);
        m.setPositionValue(2, 1, 9);
        m.setPositionValue(1, 1, 4);
        m.setPositionValue(0, 2, 7);
        m.setPositionValue(1, 0, 3);
        m.setPositionValue(0, 0, 5);
        m.setPositionValue(2, 2, 2);
        m.setPositionValue(3,3,3);

        double[][] d =
                {
                        {1, 2, 3, 4, 0},
                        {0, 3, 0, -1, 2},
                        {0, 0, 17, 3, 1},
                        {0, 0, 0, 5, 2},
                        {0, 0, 0, 0, 1}
                };


        final Matrix n = new Matrix(d);


        ((LinearLayout) root.findViewById(R.id.computations_list)).addView(new LibraryMatrixView(getContext(), m, "M"));
        ((LinearLayout) root.findViewById(R.id.computations_list)).addView(new LibraryMatrixView(getContext(), m, "N"));

        return root;
    }

    public static StoredMatricesFragment getInstance() {
        if(ourInstance == null) {
            ourInstance = new StoredMatricesFragment();
        }
        return ourInstance;
    }
}
