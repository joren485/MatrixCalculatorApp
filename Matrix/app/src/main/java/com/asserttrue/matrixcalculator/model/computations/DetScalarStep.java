package com.asserttrue.matrixcalculator.model.computations;

import com.asserttrue.matrixcalculator.model.Rational;
import com.asserttrue.matrixcalculator.model.computations.Step;
import com.asserttrue.matrixcalculator.model.Matrix;

import java.util.Locale;

/**
 * Step of a calculation where the in-between result contains a scalar times the determinant of
 * a matrix.
 */
public class DetScalarStep implements Step {

    private final Matrix matrix;
    private final StepType type;
    private final Rational scalar;
    private final Rational multiplier;
    private final int rowFrom, rowTo;
    private final int currentColumn;

    public DetScalarStep(Matrix matrix, StepType type, Rational scalar, Rational multiplier, int rowFrom, int rowTo, int currentColumn) {
        this.matrix = matrix;
        this.type = type;
        this.scalar = scalar;
        this.multiplier = multiplier;
        this.rowFrom = rowFrom;
        this.rowTo = rowTo;
        this.currentColumn = currentColumn;
    }

    public DetScalarStep(Matrix matrix, StepType type, Rational scalar, int currentColumn) {
        this(matrix, type, scalar,  new Rational(0), 0, 0, currentColumn);
    }

    public DetScalarStep(Matrix matrix, StepType type, Rational scalar, Rational multiplier, int currentColumn) {
        this(matrix, type, scalar, multiplier, 0, 0, currentColumn);
    }

    public DetScalarStep(Matrix matrix, StepType type, Rational scalar, int rowFrom, int rowTo, int currentColumn) {
        this(matrix, type, scalar, new Rational(0), rowFrom, rowTo, currentColumn);
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public int getLayoutType() {
        return type.equals(StepType.Result) ? Step.TYPE_DET_RESULT : Step.TYPE_DET_SCALAR;
    }

    public Rational getScalar() {
        return scalar;
    }

    public String getExplanation() {
        switch (type) {
            case RowReduceStep:
                return String.format(Locale.US, "Use the pivot row to eliminate the rows below it; now column %d has zeroes below the diagonal.", currentColumn + 1);
            case RowDivideStep:
                return String.format(Locale.US, "Divide the pivot-row by %s, so that it has a leading 1.", multiplier.toString());
            case PivotStep:
                return String.format(Locale.US, "Swap rows %2$d and %1$d, because row %1$d has a larger leading value.", rowFrom + 1, rowTo + 1);
            case SingularStep:
                return String.format(Locale.US, "Column %d has zeroes everywhere below the diagonal\nSo the matrix has determinant 0.", currentColumn + 1);
            case UpperTriangularStep:
                return "This matrix is upper triangular; it has determinant 1.";
            default:
                return "";
        }
    }
}
