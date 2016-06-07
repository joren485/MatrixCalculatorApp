package com.asserttrue.matrixcalculator.view.stepViews;

import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

import java.util.List;
import java.util.Locale;

public abstract class SingleMatrixStep implements Step {
    protected final Matrix matrix;

    protected SingleMatrixStep(Matrix matrix) {
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

    protected abstract String getExplanation();


    public static class DetErrorStep extends SingleMatrixStep{

        public DetErrorStep(Matrix matrix) {
            super(matrix);
        }

        @Override
        protected String getExplanation() {
            return "The determinant is not defined on non-square matrices, although they are sometimes considered to have determinant 0.";
        }
    }


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
                return "The matrix is now in Reduced Row Echelon Form." +
                        "\n\nTake the " + nrFreeColumns + " free columns and add -1 to them on the diagonal." +
                        "The resulting vectors form a basis for the kernel.";
            }
            else {
                return "The matrix is now in Reduced Row Echelon Form, but it has no free columns." +
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
}
