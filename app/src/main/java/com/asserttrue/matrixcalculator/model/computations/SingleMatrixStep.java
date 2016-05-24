package com.asserttrue.matrixcalculator.model.computations;

import com.asserttrue.matrixcalculator.model.Matrix;

public class SingleMatrixStep implements Step{

    private final Matrix matrix;
    private final StepType type;
    private final double scalar;
    private final double multiplier;
    private final int rowFrom;
    private final int rowTo;
    private final int currentColumn;

    public SingleMatrixStep(Matrix matrix, StepType type, double scalar, double multiplier, int rowFrom, int rowTo, int currentColumn) {
        this.matrix = matrix;
        this.type = type;
        this.scalar = scalar;
        this.multiplier = multiplier;
        this.rowFrom = rowFrom;
        this.rowTo = rowTo;
        this.currentColumn = currentColumn;
    }

    public String toString() {
        return matrix.toString();
    }

    @Override
    public int getLayoutType() {
        return Step.TYPE_JUST_MATRIX;
    }

    @Override
    public String getExplanation() {
        return "";
    }
}
