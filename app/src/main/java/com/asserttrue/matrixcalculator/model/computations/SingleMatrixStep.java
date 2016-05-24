package com.asserttrue.matrixcalculator.model.computations;

import com.asserttrue.matrixcalculator.model.Matrix;

import java.util.Locale;

import javax.xml.transform.Result;

public class SingleMatrixStep implements Step{

    private Matrix matrix;
    private StepType type;
    private double multiplier;
    private int rowFrom;
    private int rowTo;
    private int currentColumn;

    public SingleMatrixStep(Matrix matrix, StepType type) {
        this.matrix = matrix;
        this.type = type;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public StepType getType() {
        return type;
    }

    public void setType(StepType type) {
        this.type = type;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public int getRowTo() {
        return rowTo;
    }

    public void setRowTo(int rowTo) {
        this.rowTo = rowTo;
    }

    public int getRowFrom() {
        return rowFrom;
    }

    public void setRowFrom(int rowFrom) {
        this.rowFrom = rowFrom;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }

    public String toString() {
        return matrix.toString();
    }

    @Override
    public int getLayoutType() {
        return type.equals(StepType.Result) ? Step.TYPE_INV_RESULT : Step.TYPE_JUST_MATRIX;
    }

    @Override
    public String getExplanation() {
        switch (type) {
            case RowReduceStep:
                return String.format(Locale.US, "Use the pivot row to eliminate the others; now column %1$d has zeroes everywhere except row %1$d.", currentColumn + 1);
            case RowDivideStep:
                return String.format(Locale.US, "Divide the pivot-row by %.2f, so that it has a leading 1.", multiplier);
            case PivotStep:
                return String.format(Locale.US, "Swap rows %2$d and %1$d, because row %1$d has a larger leading value.", rowFrom + 1, rowTo + 1);
            case SingularStep:
                return String.format(Locale.US, "Column %d has zeroes everywhere below the diagonal\nSo it is not inversible.", currentColumn + 1);
            case UpperTriangularStep:
                return "The left-hand matrix is the identity; The right-hand matrix should thus be the inverse.";
            default:
                return "";
        }
    }
}
