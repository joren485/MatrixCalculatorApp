package com.asserttrue.matrixcalculator.view.stepViews;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.DatabaseHandler;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.view.MatrixView;

public class MatrixResultView extends LinearLayout {
    private final RelativeLayout matrixContainer;
    private final MatrixView matrixView;
    private final FloatingActionButton saveButton;
    private final DatabaseHandler dbHandler;

    public MatrixResultView(Context context, final Matrix matrix) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_matrix_result, this, true);

        matrixContainer = (RelativeLayout) findViewById(R.id.matrixContainer);
        matrixView = new MatrixView(context, matrix);
        matrixView.setGravity(Gravity.CENTER);
        matrixContainer.addView(matrixView);

        saveButton = (FloatingActionButton) findViewById(R.id.saveMatrixButton);
        dbHandler = new DatabaseHandler(getContext());
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText matrixName = new EditText(getContext());
                matrixName.setTextColor(Color.BLACK);
                new AlertDialog.Builder(getContext())
                        .setIcon(R.drawable.ic_selected)
                        .setMessage("Enter a name for the matrix")
                        .setView(matrixName)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHandler.createMatrix(matrix, matrixName.getText().toString());
                                Toast.makeText(getContext(), "Saved matrix.", Toast.LENGTH_SHORT).show();
                                saveButton.setVisibility(INVISIBLE);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    public void setMatrix(Matrix matrix) {
        matrixView.setMatrix(matrix);
    }
}
