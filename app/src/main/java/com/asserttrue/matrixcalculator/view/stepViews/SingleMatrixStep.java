package com.asserttrue.matrixcalculator.view.stepViews;

import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

import java.util.List;
import java.util.Locale;

/**
 * Step containing just a matrix and text explaining the step.
 */
public abstract class SingleMatrixStep implements Step {
    protected final Matrix matrix;

    private SingleMatrixStep(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public View toView(Context context) {
        return new SingleMatrixView(context, matrix, getExplanation());
    }

    @Override
    public void setViewContent(View view) {
        if( ! (view instanceof SingleMatrixView) ) {
            throw new IllegalArgumentException("Wrong view passed to step");
        }

        SingleMatrixView cardView = (SingleMatrixView) view;

        cardView.setMatrix(matrix);
        cardView.setExplanation(getExplanation());
    }

    @Override
    public int getLayoutType() {
        return SINGLE_MATRIX;
    }
    /**
     * Get a string to show to the user as explanation. Varies per kind of computation.
     */
    protected abstract String getExplanation();

    /**
     * The following classes are implementations which share the same layout but have slightly
     * different explanations.
     */

    public static class ColumnEliminateStep extends SingleMatrixStep {
        private final int columnIndex;
        private final int rowIndex;

        public ColumnEliminateStep(int columnIndex, Matrix matrix) {
            this(matrix, columnIndex, columnIndex);
        }

        public ColumnEliminateStep(Matrix matrix, int columnIndex, int rowIndex) {
            super(matrix);
            this.columnIndex = columnIndex;
            this.rowIndex = rowIndex;
        }

        protected String getExplanation() {
            return String.format(Locale.US, "Use the pivot row to eliminate the others. Now column %1$d has zeroes everywhere except row %1$d.", columnIndex + 1);
        }
    }

    public static class InvIdentityStep extends SingleMatrixStep {

        public InvIdentityStep(Matrix matrix) {
            super(matrix);
        }

        protected String getExplanation() {
            return "The left-hand matrix is the identity, so the right-hand matrix is our inverse.";
        }
    }

    public static class RowDivideStep extends SingleMatrixStep {
        private final int row;
        private final Rational scalar;

        public RowDivideStep(int row, Rational scalar, Matrix matrix) {
            super(matrix);
            this.row = row;
            this.scalar = scalar;
        }

        protected String getExplanation() {
            return String.format(Locale.US, "Divide row %d by %s, so that it has a leading 1.", row + 1, scalar.toString());
        }
    }

    public static class InvRowSwapStep extends SingleMatrixStep {
        private final int newPivot;
        private final int oldPivot;

        public InvRowSwapStep(int newPivot, int oldPivot, Matrix matrix) {
            super(matrix);
            this.newPivot = newPivot;
            this.oldPivot = oldPivot;
        }

        protected String getExplanation() {
            return String.format(Locale.US, "Swap rows %2$d and %1$d, because row %1$d has a larger leading value.", newPivot + 1, oldPivot + 1);
        }
    }

    public static class InvStartStep extends SingleMatrixStep {

        public InvStartStep(Matrix matrix) {
            super(matrix);
        }

        @Override
        protected String getExplanation() {
            return "Conjugate the matrix with the identity on the right. As we turn the left-hand " +
                    "matrix into the identity, the right hand should become its inverse.";
        }
    }

    public static class InvZeroStep extends SingleMatrixStep {
        private final int column;

        public InvZeroStep(Matrix matrix, int column) {
            super(matrix);
            this.column = column;
        }

        protected String getExplanation() {
            return String.format(Locale.US, "Column %d has zeroes on the diagonal and below. Thus the matrix is not invertible.", column + 1);
        }
    }

    public static class KernelColumnElimStep extends SingleMatrixStep{
        private final int columnIndex;

        protected KernelColumnElimStep(Matrix matrix, int columnIndex) {
            super(matrix);
            this.columnIndex = columnIndex;
        }

        @Override
        protected String getExplanation() {
            return String.format(Locale.US, "Use the pivot row to eliminate the others, so that column %1$d has zeroes everywhere below the diagonal.", columnIndex + 1);
        }
    }

    public static class KernelResultStep implements Step {
        private final List<Matrix> vectors;

        public KernelResultStep(List<Matrix> vectors) {
            this.vectors = vectors;
        }

        @Override
        public View toView(Context context) {
            return new VectorSpanView(context, vectors);
        }

        @Override
        public void setViewContent(View view) {

        }

        @Override
        public int getLayoutType() {
            return VECTOR_SPAN;
        }
    }

    public static class KernelRREFStep extends SingleMatrixStep {
        private final int nrFreeColumns;

        public KernelRREFStep(Matrix matrix, int nrFreeColumns) {
            super(matrix);
            this.nrFreeColumns = nrFreeColumns;
        }

        @Override
        protected String getExplanation() {
            if(nrFreeColumns >= 1) {
                return "This matrix is in Reduced Row Echelon Form." +
                        "\n\nTake the " + nrFreeColumns + " free columns and add -1 to them on the diagonal." +
                        "The resulting vectors form a basis for the kernel.";
            }
            else {
                return "This matrix is in Reduced Row Echelon Form, but it has no free columns." +
                        " So only the zero vector is its kernel.";
            }

        }
    }

    public static class AddCellStep extends SingleMatrixStep {
        final int column;
        final int row;
        final Rational left;
        final Rational right;
        final Rational result;


        public AddCellStep(Matrix matrix, int column, int row, Rational left, Rational right, Rational result) {
            super(matrix);
            this.column = column;
            this.row = row;
            this.left = left;
            this.right = right;
            this.result = result;
        }

        @Override
        protected String getExplanation() {
            return "Calculate cell (" + (row + 1) + ", " + (column + 1) + ");\n" + left + " + " +  right + " = " + result;
        }
    }

    public static class MultCellStep extends SingleMatrixStep {
        private final List<Rational> leftTerms;
        private final List<Rational> rightTerms;
        private final Rational result;
        private final int row;
        private final int column;

        public MultCellStep(Matrix matrix, Rational result, List<Rational> leftTerms1, List<Rational> rightTerms, int row, int column) {
            super(matrix);
            this.result = result;
            this.leftTerms = leftTerms1;
            this.rightTerms = rightTerms;
            this.row = row;
            this.column = column;
        }

        @Override
        protected String getExplanation() {
            StringBuilder sb = new StringBuilder();

            sb.append("Calculate cell (").append(row + 1).append(", ").append(column + 1).append(");\n");

            sb.append(leftTerms.get(0).toString()).append(" * ").append(rightTerms.get(0).toString());

            for(int i = 1; i < leftTerms.size(); i++) {
                sb.append(" + ").append(leftTerms.get(i).toString()).append(" * ").append(rightTerms.get(i).toString());
            }

            sb.append(" = ").append(result.toString());

            return sb.toString();
        }
    }

    public static class ScalarMultCellStep extends SingleMatrixStep {
        private final int row;
        private final int column;
        private final Rational scalar;
        private final Rational cellValue;
        private final Rational result;

        public ScalarMultCellStep(Matrix matrix, int row, int column, Rational scalar, Rational cellValue, Rational result) {
            super(matrix);
            this.row = row;
            this.column = column;
            this.scalar = scalar;
            this.cellValue = cellValue;
            this.result = result;
        }

        @Override
        protected String getExplanation() {
            return "Calculate cell (" + (row + 1) + ", " + (column + 1) + ");\n" + scalar + " * " +  cellValue + " = " + result;
        }
    }

    public static class ExpStep extends SingleMatrixStep {
        private final int exponent;
        private final Matrix matrix;

        public ExpStep( Matrix matrix, int exponent) {
            super(matrix);
            this.exponent = exponent;
            this.matrix = matrix;
        }

        @Override
        protected String getExplanation() {
            return "Find the power " + exponent + " of the matrix.";
        }
    }

    public static class TransCreateStep extends SingleMatrixStep {

        public TransCreateStep(Matrix matrix) {
            super(matrix);
        }

        @Override
        protected String getExplanation() {
            return "Create a new matrix with " + matrix.getNrColumns() + " columns and " + matrix.getNrRows() + " rows," +
                    " as the original matrix has " + matrix.getNrRows() + " columns and " + matrix.getNrColumns() + " rows.";
        }
    }

    public static class TransCellStep extends SingleMatrixStep {

        private final int row, column;
        private final Rational result;

        public TransCellStep(Matrix matrix, int row, int column, Rational result) {
            super(matrix);
            this.row = row;
            this.column = column;
            this.result = result;
        }

        @Override
        public String getExplanation() {
            return "Set cell (" + (row + 1) + ", " + (column + 1) + "), which was " + result + " in the original matrix.";
        }
    }

    public static class FirstStep extends SingleMatrixStep {

        private final String computation;

        public FirstStep(Matrix matrix, String computation) {
            super(matrix);
            this.computation = computation;
        }

        @Override
        public String getExplanation() {
            return "Calculating " + computation + " of this matrix.";
        }
    }
}
