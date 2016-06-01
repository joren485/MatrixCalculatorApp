package com.asserttrue.matrixcalculator.model.determinant;

import com.asserttrue.matrixcalculator.model.DetScalarStep;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

import java.util.Locale;

public class DetRowSwapStep extends DetScalarStep{
    private final int rowFrom;
    private final int rowTo;

    public DetRowSwapStep(Matrix matrix, Rational scalar, int rowFrom, int rowTo) {
        super(matrix, scalar);
        this.rowFrom = rowFrom;
        this.rowTo = rowTo;
    }

    @Override
    protected String getExplanation() {
        return String.format(Locale.US, "Swap rows %2$d and %1$d, because row %1$d has a larger leading value.", rowFrom + 1, rowTo + 1);

    }
}
