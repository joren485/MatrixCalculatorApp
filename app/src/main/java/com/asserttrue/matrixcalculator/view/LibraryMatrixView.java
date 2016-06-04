package com.asserttrue.matrixcalculator.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.DatabaseHandler;
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

        FloatingActionButton removeButton = (FloatingActionButton) findViewById(R.id.removeMatrixButton);
        removeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getContext())
                        .setIcon(R.drawable.ic_alert_orange_24dp)
                        .setTitle("Removing Matrix")
                        .setMessage("Are you sure you want to remove matrix \"" + getContentMatrix().getName() + "\"?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                remove();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    private void remove() {
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        dbHandler.deleteMatrix(getContentMatrix().getName());
        ((LinearLayout)getParent()).removeView(this);
    }

    public void setMatrix(Matrix matrix) {
        matrixView.setMatrix(matrix);
    }

    public Matrix getContentMatrix() {
        return matrixView.getContentMatrix();
    }

}
