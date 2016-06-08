package com.asserttrue.matrixcalculator.view.stepViews;


import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

import java.util.Locale;

/**
 * Step containing the matrix (of which the determinant is being computeted) multiplied by a scalar.
 */
public abstract class DetScalarStep implements Step {
    protected final Matrix matrix;
    protected final Rational scalar;

    protected DetScalarStep(Matrix matrix, Rational scalar) {
        this.matrix = matrix;
        this.scalar = scalar;
    }

    @Override
    public View toView(Context context) {
        return new DetScalarView(context, matrix, getExplanation(), scalar);
    }

    @Override
    public void setViewContent(View view) {
        if( ! (view instanceof DetScalarView) ) {
            throw new IllegalArgumentException("Wrong view passed to step");
        }

        DetScalarView cardView = (DetScalarView) view;

        cardView.setMatrix(matrix);
        cardView.setScalar(scalar);
        cardView.setExplanation(getExplanation());
    }

    @Override
    public int getLayoutType() {
        return DET_MATRIX_SCALAR;
    }

    /**
     * Get a string to show to the user as explanation. Varies per kind of computation.
     */
    protected abstract String getExplanation();

    /**
     * The following are implementations of this class, which share the same layout but have slightly
     * different explanations.
     */

    public static class DetColumnElimStep extends DetScalarStep {
        private final int columnIndex;

        public DetColumnElimStep(Matrix matrix, Rational scalar, int columnIndex) {
            super(matrix, scalar);
            this.columnIndex = columnIndex;
        }

        protected String getExplanation() {
            return String.format(Locale.US, "Use the pivot row to eliminate the others; now column %1$d has zeroes everywhere below the diagonal.", columnIndex + 1);
        }
    }

    public static class DetRowDivideStep extends DetScalarStep{
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

    public static class DetRowSwapStep extends DetScalarStep{
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

    public static class DetZeroStep extends DetScalarStep {
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

    public static class DetUpperTriangularStep extends DetScalarStep {

        public DetUpperTriangularStep(Matrix matrix, Rational scalar) {
            super(matrix, scalar);
        }

        @Override
        protected String getExplanation() {
            return "The matrix on the right is upper triangular, with unit diagonal, so its determinant is one." +
                    "\nThe final result is the scalar to the left, "+ scalar + ".";
        }
    }
}
