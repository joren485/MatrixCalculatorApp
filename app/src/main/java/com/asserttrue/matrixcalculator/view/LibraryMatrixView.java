package com.asserttrue.matrixcalculator.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Matrix;

public class LibraryMatrixView extends LinearLayout {

    final MatrixView matrixView;

    public LibraryMatrixView(Context context, Matrix matrix, String name) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.preview_matrix, this, true);

        RelativeLayout matrixContainer = (RelativeLayout) findViewById(R.id.matrixContainer);
        matrixView = new MatrixView(context, matrix);
        matrixView.setGravity(Gravity.CENTER);
        matrixContainer.addView(matrixView);

        TextView nameEquals = (TextView) findViewById(R.id.name_equals);
        nameEquals.setText(name + " = ");
    }

    public void setMatrix(Matrix matrix) {
        matrixView.setMatrix(matrix);
    }
}
