package com.asserttrue.matrixcalculator.view.storedMatricesTab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asserttrue.matrixcalculator.R;

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
        /*
        FloatingActionButton addMatrixButton = (FloatingActionButton) root.findViewById(R.id.addMatrixButton);
        addMatrixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateMatrixActivity.class));
            }
        });
        */

        return root;
    }

    public static StoredMatricesFragment getInstance() {
        if(ourInstance == null) {
            ourInstance = new StoredMatricesFragment();
        }
        return ourInstance;
    }
}
