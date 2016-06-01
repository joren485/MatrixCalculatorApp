package com.asserttrue.matrixcalculator.model.kernel;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.SingleMatrixStep;

import java.util.Locale;

public class KernelColumnElimStep extends SingleMatrixStep{
    private final int columnIndex;

    protected KernelColumnElimStep(Matrix matrix, int columnIndex) {
        super(matrix);
        this.columnIndex = columnIndex;
    }

    @Override
    protected String getExplanation() {
        return String.format(Locale.US, "Use the pivot row to eliminate the others; now column %1$d has zeroes everywhere below the diagonal.", columnIndex + 1);
    }
}
