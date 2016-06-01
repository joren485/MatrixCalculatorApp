package com.asserttrue.matrixcalculator.model.determinant;

import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.DetScalarStep;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;
import com.asserttrue.matrixcalculator.model.Step;
import com.asserttrue.matrixcalculator.view.stepViews.SingleMatrixView;

import java.util.Locale;

public class DetColumnElimStep extends DetScalarStep {
    private final int columnIndex;

    public DetColumnElimStep(Matrix matrix, Rational scalar, int columnIndex) {
        super(matrix, scalar);
        this.columnIndex = columnIndex;
    }

    protected String getExplanation() {
        return String.format(Locale.US, "Use the pivot row to eliminate the others; now column %1$d has zeroes everywhere below the diagonal.", columnIndex + 1);
    }
}
