package com.asserttrue.matrixcalculator.view.storedMatricesTab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;
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
        m.setValue(2, 1, new Rational(9));
        m.setValue(1, 1, new Rational(4));
        m.setValue(0, 2, new Rational(7));
        m.setValue(1, 0, new Rational(3));
        m.setValue(0, 0, new Rational(5));
        m.setValue(2, 2, new Rational(2));
        m.setValue(3, 3, new Rational(3));


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
