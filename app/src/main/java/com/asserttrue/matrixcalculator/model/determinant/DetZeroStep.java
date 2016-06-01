package com.asserttrue.matrixcalculator.model.determinant;


import com.asserttrue.matrixcalculator.model.DetScalarStep;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

import java.util.Locale;

public class DetZeroStep extends DetScalarStep {
    private final int columnIndex;

    public DetZeroStep(Matrix matrix, int columnIndex) {
        super(matrix, new Rational(0));
        this.columnIndex = columnIndex;
    }

    @Override
    protected String getExplanation() {
        return String.format(Locale.US, "Column %d has zeroes everywhere below the diagonal\nSo the matrix has determinant 0.", columnIndex + 1);
    }
}
