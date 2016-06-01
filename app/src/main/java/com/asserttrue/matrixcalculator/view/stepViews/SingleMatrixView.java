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

public class SingleMatrixView extends LinearLayout {
    private final RelativeLayout matrixContainer;
    private final MatrixView matrixView;
    private final TextView explanationView;
    private final Context context;

    public SingleMatrixView(Context context, Matrix matrix, String explanation) {
        super(context);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_single_matrix, this, true);

        matrixContainer = (RelativeLayout) findViewById(R.id.matrixContainer);
        matrixView = new MatrixView(context, matrix);
        matrixView.setGravity(Gravity.CENTER);
        matrixContainer.addView(matrixView);
        explanationView = (TextView) findViewById(R.id.explanation);
        explanationView.setText(explanation);
    }

    public void setMatrix(Matrix matrix) {
        matrixView.setMatrix(matrix);
    }

    public void setExplanation(String explanation) {
        explanationView.setText(explanation);
    }
}
