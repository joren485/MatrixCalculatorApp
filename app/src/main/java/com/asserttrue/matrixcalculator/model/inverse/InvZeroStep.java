package com.asserttrue.matrixcalculator.model.inverse;

import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.SingleMatrixStep;
import com.asserttrue.matrixcalculator.model.Step;
import com.asserttrue.matrixcalculator.view.stepViews.SingleMatrixView;

import java.util.Locale;

public class InvZeroStep extends SingleMatrixStep {
    private final int column;

    public InvZeroStep(Matrix matrix, int column) {
        super(matrix);
        this.column = column;
    }

    protected String getExplanation() {
        return String.format(Locale.US, "Column %d has zeroes on the diagonal and below. Thus the matrix is not invertible.", column + 1);
    }
}
