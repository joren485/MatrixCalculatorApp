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

public class DoubleMatrixFirstView extends LinearLayout {
    private final MatrixView matrixViewLeft;
    private final MatrixView matrixViewRight;
    private final TextView compDescriptionView;

    public DoubleMatrixFirstView(Context context, Matrix left, Matrix right, String computation) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_mult_matrix_first, this, true);

        RelativeLayout matrixContainerLeft = (RelativeLayout) findViewById(R.id.matrixContainerLeft);
        matrixViewLeft = new MatrixView(context, left);
        matrixViewLeft.setGravity(Gravity.CENTER);
        matrixContainerLeft.addView(matrixViewLeft);

        RelativeLayout matrixContainerRight = (RelativeLayout) findViewById(R.id.matrixContainerRight);
        matrixViewRight = new MatrixView(context, left);
        matrixViewRight.setGravity(Gravity.CENTER);
        matrixContainerRight.addView(matrixViewRight);

        compDescriptionView = (TextView) findViewById(R.id.computation_desc);
        compDescriptionView.setText("Calculating " + computation + " of matrices:");
    }

    public void setMatrices(Matrix left, Matrix right) {
        matrixViewLeft.setMatrix(left);
        matrixViewRight.setMatrix(right);
    }

    public void setComputation(String computation) {
        compDescriptionView.setText("Calculating " + computation + " of matrices:");
    }
}
