package com.asserttrue.matrixcalculator.view.stepViews;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;
import com.asserttrue.matrixcalculator.view.MatrixView;

public class DetResultView extends LinearLayout {
    private final MatrixView matrixView;
    private final TextView scalarView;

    public DetResultView(Context context, Matrix matrix, Rational scalar) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_det_result_step, this, true);

        RelativeLayout matrixContainer = (RelativeLayout) findViewById(R.id.matrixContainer);
        matrixView = new MatrixView(context, matrix);
        matrixView.setGravity(Gravity.CENTER);
        matrixContainer.addView(matrixView);
        scalarView = (TextView) findViewById(R.id.scalar);
        scalarView.setText(scalar.toString());
    }

    public void setMatrix(Matrix matrix) {
        matrixView.setMatrix(matrix);
    }

    public void setScalar(Rational scalar) {
        scalarView.setText(scalar.toString());
    }
}
