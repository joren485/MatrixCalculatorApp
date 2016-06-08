package com.asserttrue.matrixcalculator.view.stepViews;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.view.MatrixView;

public class SingleMatrixFirstView extends LinearLayout {
    private final MatrixView matrixView;
    private final TextView compDescriptionView;

    public SingleMatrixFirstView(Context context, Matrix matrix, String computation) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_single_matrix_first, this, true);

        RelativeLayout matrixContainer = (RelativeLayout) findViewById(R.id.matrixContainer);
        matrixView = new MatrixView(context, matrix);
        matrixView.setGravity(Gravity.CENTER);
        matrixContainer.addView(matrixView);
        compDescriptionView = (TextView) findViewById(R.id.computation_desc);
        compDescriptionView.setText("Calculating " + computation + " of matrix:");
    }

    public void setMatrix(Matrix matrix) {
        matrixView.setMatrix(matrix);
    }

    public void setComputation(String computation) {
        compDescriptionView.setText("Calculating " + computation + " of matrix:");
    }
}
