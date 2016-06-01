package com.asserttrue.matrixcalculator.model.determinant;

import com.asserttrue.matrixcalculator.model.DetScalarStep;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

import java.util.Locale;

public class DetRowDivideStep extends DetScalarStep{
    private final Rational multiplier;
    private final int columnIndex;

    public DetRowDivideStep(Matrix matrix, Rational scalar, Rational divideBy, int columnIndex) {
        super(matrix, scalar);
        this.multiplier = divideBy;
        this.columnIndex = columnIndex;
    }

    @Override
    protected String getExplanation() {
        return String.format(Locale.US, "Divide the pivot-row by %s, so that it has a leading 1.", multiplier.toString());
    }
}
