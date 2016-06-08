package com.asserttrue.matrixcalculator.view.storedMatricesTab;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.DatabaseHandler;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.view.LibraryMatrixView;
import com.asserttrue.matrixcalculator.view.MatrixView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoredMatricesFragment extends Fragment {

    private View root;
    private LinearLayout matrixList;
    private boolean hasShownToast = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_storedmatrices, container, false);

        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.addMatrixButton);

        matrixList = (LinearLayout) root.findViewById(R.id.computations_list);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditMatrixSingleton settings = EditMatrixSingleton.getInstance();
                settings.setVariables(new Matrix(2, 2), "", true, false, false);
                Intent i = new Intent(getContext(), EditMatrixActivity.class);
                startActivity(i);
            }
        });

        return root;
    }

    public void onResume() {
        super.onResume();

        DatabaseHandler hDB = new DatabaseHandler(getContext());

        matrixList.removeAllViews();

        for (Matrix m : hDB.readAllMatrices()){

            final LibraryMatrixView matrixView = new LibraryMatrixView(getContext(), m, m.getName());

            matrixView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditMatrixSingleton settings = EditMatrixSingleton.getInstance();
                    settings.setVariables(matrixView.getContentMatrix(), matrixView.getContentMatrix().getName(), true, false, true);
                    startActivity(new Intent(getContext(), EditMatrixActivity.class));
                }
            });

            matrixList.addView(matrixView);
        }
    }

    @Override
    public void setUserVisibleHint(boolean visibleToUser) {

        if (visibleToUser) {
            if (!hasShownToast && showToast()){
                Toast.makeText(getContext(), "Press on a collapsed matrix to expand it.", Toast.LENGTH_SHORT).show();
                hasShownToast = true;
            }
        }
    }

    public boolean showToast(){
        for (int i = 0; i < matrixList.getChildCount(); i++){

            LibraryMatrixView libraryMatrixView = (LibraryMatrixView) matrixList.getChildAt(i);

            if (libraryMatrixView.isInDotMode()){
                return true;
            }
        }

        return false;
    }

}
