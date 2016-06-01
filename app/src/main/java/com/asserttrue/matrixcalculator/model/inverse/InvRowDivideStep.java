package com.asserttrue.matrixcalculator.model.inverse;

import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;
import com.asserttrue.matrixcalculator.model.SingleMatrixStep;
import com.asserttrue.matrixcalculator.model.Step;
import com.asserttrue.matrixcalculator.view.stepViews.SingleMatrixView;

import java.util.Locale;

public class InvRowDivideStep extends SingleMatrixStep {
    private final int row;
    private final Rational scalar;

    public InvRowDivideStep(int row, Rational scalar, Matrix matrix) {
        super(matrix);
        this.row = row;
        this.scalar = scalar;
    }

    protected String getExplanation() {
        return String.format(Locale.US, "Divide row %d by %s, so that it has a leading 1.", row + 1, scalar.toString());
    }
}
