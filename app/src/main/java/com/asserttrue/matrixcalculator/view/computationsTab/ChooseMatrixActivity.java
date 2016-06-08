package com.asserttrue.matrixcalculator.view.computationsTab;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.Computations;
import com.asserttrue.matrixcalculator.model.DatabaseHandler;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;
import com.asserttrue.matrixcalculator.view.MathTextView;
import com.asserttrue.matrixcalculator.view.MathTitleTextView;
import com.asserttrue.matrixcalculator.view.storedMatricesTab.EditMatrixActivity;
import com.asserttrue.matrixcalculator.view.storedMatricesTab.EditMatrixSingleton;

import java.util.ArrayList;

public class ChooseMatrixActivity extends AppCompatActivity {

    private LinearLayout matrixList;
    private FloatingActionButton startComputationButton;
    private ChooseMatrixView[] selectedList;

    private int requiredMatrices;
    private String computationName;

    private int currentSelectionIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_matrix);

        requiredMatrices = getIntent().getIntExtra("numMatrices", 1);
        computationName = getIntent().getStringExtra("computation");
        selectedList = new ChooseMatrixView[requiredMatrices];

        matrixList = (LinearLayout) findViewById(R.id.matrixList);
        startComputationButton = (FloatingActionButton) findViewById(R.id.startComputationButton);
        FloatingActionButton addMatrixButton = (FloatingActionButton) findViewById(R.id.addMatrixButton);
        MathTextView text = (MathTextView) findViewById(R.id.computation_text);
        TextView numMatrices = (TextView) findViewById(R.id.numberOfSelectionTV);
        numMatrices.setText("Please select " + requiredMatrices + (requiredMatrices == 1 ? " matrix." : " matrices."));
        switch (computationName) {
            case "kernel":
                text.setText("Kernel Span");
                break;
            case "product":
                text.setText("Matrix Multiplication");
                break;
            case "inverse":
                text.setText("Matrix Inverse");
                break;
            case "determinant":
                text.setText("Determinant");
                break;
            case "sum":
                text.setText("Matrix Addition");
                break;
            case "exponent":
                text.setText("Matrix Exponentiation");
                break;
            case "scalarMult":
                text.setText("Scalar Multiplication");
                break;
            case "rref":
                text.setText("Row Echelon Form");
                break;
            default:
                text.setVisibility(View.GONE);
        }

        DatabaseHandler hDB = new DatabaseHandler(this);

        for (Matrix m : hDB.getAllMatrices()){
            final ChooseMatrixView matrixView = new ChooseMatrixView(this, m, m.getName(), requiredMatrices);
            matrixList.addView(matrixView);

            matrixView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    currentSelectionIndex = (currentSelectionIndex + 1) % requiredMatrices;

                    if (matrixView.isSelected(currentSelectionIndex)) {
                        matrixView.setIndex(currentSelectionIndex, false);
                        selectedList[currentSelectionIndex] = null;
                    } else {
                        if (selectedList[currentSelectionIndex] != null)
                            selectedList[currentSelectionIndex].setIndex(currentSelectionIndex, false);
                        matrixView.setIndex(currentSelectionIndex, true);
                        selectedList[currentSelectionIndex] = matrixView;
                    }

                    startComputationButton.setVisibility(listComplete() ? View.VISIBLE : View.GONE);
                }
            });
        }

        addMatrixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMatrix();
            }
        });

        startComputationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startComputation();
            }
        });

        EditMatrixSingleton settings = EditMatrixSingleton.getInstance();
        settings.setVariables(null, null, false, false, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EditMatrixSingleton settings = EditMatrixSingleton.getInstance();
        if (settings.isResult) {
            final ChooseMatrixView matrixView = new ChooseMatrixView(this, settings.editMatrix, settings.editMatrix.getName(), requiredMatrices);
            matrixView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    currentSelectionIndex = (currentSelectionIndex + 1) % requiredMatrices;

                    if (matrixView.isSelected(currentSelectionIndex)) {
                        matrixView.setIndex(currentSelectionIndex, false);
                        selectedList[currentSelectionIndex] = null;
                    } else {
                        if (selectedList[currentSelectionIndex] != null)
                            selectedList[currentSelectionIndex].setIndex(currentSelectionIndex, false);
                        matrixView.setIndex(currentSelectionIndex, true);
                        selectedList[currentSelectionIndex] = matrixView;
                    }

                    startComputationButton.setVisibility(listComplete() ? View.VISIBLE : View.GONE);
                }
            });
            matrixList.addView(matrixView);
        }
        settings.setVariables(null, null, false, false, false);
    }

    private boolean listComplete() {
        for (ChooseMatrixView m : selectedList)
            if (m == null)
                return false;

        return true;
    }

    private void addMatrix() {
        EditMatrixSingleton settings = EditMatrixSingleton.getInstance();
        settings.setVariables(new Matrix(2, 2), "", false, false, false);
        Intent i = new Intent(this, EditMatrixActivity.class);
        startActivity(i);
    }

    private void startComputation() {
        CurrentComputation curComp = CurrentComputation.getInstance();
        switch(computationName) {
            case "kernel":
                curComp.setSteps(Computations.kernel(selectedList[0].getContentMatrix()));
                break;
            case "product":
                curComp.setSteps(Computations.product(selectedList[0].getContentMatrix(), selectedList[1].getContentMatrix()));
                break;
            case "inverse":
                curComp.setSteps(Computations.inverse(selectedList[0].getContentMatrix()));
                break;
            case "determinant":
                curComp.setSteps(Computations.determinant(selectedList[0].getContentMatrix()));
                break;
            case "sum":
                curComp.setSteps(Computations.addition(selectedList[0].getContentMatrix(), selectedList[1].getContentMatrix()));
                break;
            case "exponent":
                curComp.setSteps(Computations.exponentiation(selectedList[0].getContentMatrix(), 5));
                break;
            case "scalarMult":
                curComp.setSteps(Computations.scalarMultiplication(selectedList[0].getContentMatrix(), new Rational(5)));
                break;
            case "rref":
                curComp.setSteps(Computations.rowEchelonForm(selectedList[0].getContentMatrix()));
                break;
            default:
                return;
        }
        Intent intent = new Intent(this, ComputationActivity.class);
        startActivity(intent);
    }
}
