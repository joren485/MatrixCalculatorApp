package com.asserttrue.matrixcalculator.model.inverse;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.SingleMatrixStep;

import java.util.Locale;

public class InvColumnElimStep extends SingleMatrixStep {
    private final int columnIndex;

    public InvColumnElimStep(int columnIndex, Matrix matrix) {
        super(matrix);
        this.columnIndex = columnIndex;
    }

    protected String getExplanation() {
        return String.format(Locale.US, "Use the pivot row to eliminate the others; now column %1$d has zeroes everywhere except row %1$d.", columnIndex + 1);
    }
}
