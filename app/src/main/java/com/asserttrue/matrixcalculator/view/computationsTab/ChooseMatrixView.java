package com.asserttrue.matrixcalculator.view.computationsTab;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.DatabaseHandler;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.view.MatrixView;

public class ChooseMatrixView extends LinearLayout {

    private MatrixView matrixView;
    private TextView selectedIndex;

    public ChooseMatrixView(Context context, Matrix matrix, String name) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.choose_matrix, this, true);

        RelativeLayout matrixContainer = (RelativeLayout) findViewById(R.id.matrixContainer);
        matrixView = new MatrixView(context, matrix);
        matrixView.setGravity(Gravity.CENTER);
        matrixContainer.addView(matrixView);

        TextView nameEquals = (TextView) findViewById(R.id.name_equals);
        nameEquals.setText((name.isEmpty() ? "temp" : name) + " = ");

        selectedIndex = (TextView) findViewById(R.id.selectedIndexTV);
    }

    public void setMatrix(Matrix matrix) {
        matrixView.setMatrix(matrix);
    }

    public Matrix getContentMatrix() {
        return matrixView.getContentMatrix();
    }

    public void setNumber(int i) {
        if (i > 0) {
            selectedIndex.setVisibility(VISIBLE);
            selectedIndex.setText(String.valueOf(i));
        } else {
            selectedIndex.setVisibility(GONE);
            selectedIndex.setText("");
        }
    }
}
