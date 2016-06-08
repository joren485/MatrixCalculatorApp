package com.asserttrue.matrixcalculator.model;


import com.asserttrue.matrixcalculator.view.stepViews.DetScalarStep;
import com.asserttrue.matrixcalculator.view.stepViews.DoubleMatrixStep;
import com.asserttrue.matrixcalculator.view.stepViews.SingleMatrixStep;
import com.asserttrue.matrixcalculator.view.stepViews.Step;
import com.asserttrue.matrixcalculator.view.stepViews.TextResultStep;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class containing static methods carrying out algorithms on matrices. Every important
 * moment in the computation is stored in a Step. A list of all these steps is returning.
 */
public abstract class Computations {

    public static List<Step> determinant(Matrix matrix) {
        // This computation requires the matrix to be mutated, and so a copy is required.
        final Matrix A = new Matrix(matrix);

        LinkedList<Step> steps = new LinkedList<>();

        if (! matrix.isSquareMatrix() ) {
            steps.add(new TextResultStep.DetErrorStep());
            return steps;
        }

        if (matrix.getAugmentedColumnIndex() != -1) {
            steps.add(new TextResultStep.AugErrorStep("Determinant"));
            return steps;
        }

        steps.add(new SingleMatrixStep.FirstStep(matrix, "determinant"));

        // Determinant of upper-triangular matrix with identity diagonal is 1.
        // We multiply this result by the determinant of the elementary matrices required to reduce A to that form.
        Rational determinant = new Rational(1);

        for(int column = 0; column < A.getNrColumns(); column++) {
            int pivotRow = column;

            for(int row = pivotRow + 1; row < A.getNrColumns(); row++) {
                if(A.getValueAt(column, row).abs().greater(A.getValueAt(column, pivotRow).abs())) {
                    pivotRow = row;
                }
            }

            if(A.getValueAt(column, pivotRow).getNumerator() == 0.0) {
                steps.add(new DetScalarStep.DetZeroStep(A, column));
                return steps;
            }

            if(column != pivotRow) {
                A.swapRows(column, pivotRow);
                determinant.timesIs(new Rational(-1));
                steps.add(new DetScalarStep.DetRowSwapStep(new Matrix(A), new Rational(determinant), column, pivotRow));
                pivotRow = column;

            }

            Rational pivotValue =  A.getValueAt(column, pivotRow);
            if(! pivotValue.equals(new Rational(1))) {
                A.multiplyRow(pivotRow, pivotValue.inverse());
                determinant.timesIs(pivotValue);

                steps.add(new DetScalarStep.DetRowDivideStep(new Matrix(A), new Rational(determinant), pivotValue, column));
            }

            boolean eliminatedRows = false;
            for(int row = pivotRow + 1; row < A.getNrRows(); row++) {
                if(! A.getValueAt(column, row).equals(new Rational(0))) {
                    eliminatedRows = true;
                    A.addRow(pivotRow, row, A.getValueAt(column, row).negative());
                }
            }
            if(eliminatedRows) {
                steps.add(new DetScalarStep.DetColumnElimStep(new Matrix(A), new Rational(determinant), column));
            }

            System.out.println(A);
        }

        steps.add(new DetScalarStep.DetUpperTriangularStep(new Matrix(A), new Rational(determinant)));

        steps.addFirst(new Step.DetResultStep(new Matrix(matrix), new Rational(determinant)));

        return steps;
    }

    public static List<Step> inverse(Matrix matrix) {

        LinkedList<Step> steps = new LinkedList<>();

        if (! matrix.isSquareMatrix() ) {
            steps.add(new TextResultStep.InvErrorStep());
            return steps;
        }

        if (matrix.getAugmentedColumnIndex() != -1) {
            steps.add(new TextResultStep.AugErrorStep("Inverse"));
            return steps;
        }

        steps.add(new SingleMatrixStep.FirstStep(matrix, "inverse"));

        Matrix A = new Matrix(matrix, Matrix.identity(matrix.getNrColumns()));

        steps.add(new SingleMatrixStep.InvStartStep(new Matrix(A)));

        for (int column = 0; column < A.getAugmentedColumnIndex(); column++) {
            int pivotRow = column;

            for (int row = pivotRow + 1; row < A.getNrRows(); row++) {
                if ( A.getValueAt(column, row).abs() .greater ( A.getValueAt(column, pivotRow).abs() )) {
                    pivotRow = row;
                }
            }

            if(A.getValueAt(column, pivotRow).equals(new Rational(0))) {
                steps.add(new SingleMatrixStep.InvZeroStep(new Matrix(A), column));
                return steps;
            }

            if(column != pivotRow) {
                A.swapRows(column, pivotRow);
                steps.add(new SingleMatrixStep.InvRowSwapStep(pivotRow, column, new Matrix (A)));
                pivotRow = column;
            }


            Rational pivotValue =  A.getValueAt(column, pivotRow);

            if(! pivotValue.equals(new Rational(1))) {
                A.multiplyRow(pivotRow, pivotValue.inverse());
                steps.add(new SingleMatrixStep.RowDivideStep(column, new Rational(pivotValue), new Matrix(A)));
            }

            boolean eliminatedRows = false;
            for(int row = 0; row < A.getNrRows(); row++) {
                if(! A.getValueAt(column, row).equals(new Rational(0)) && row != pivotRow ) {
                    eliminatedRows = true;
                    A.addRow(pivotRow, row, A.getValueAt(column, row).negative());
                }
            }

            if(eliminatedRows) {
                steps.add(new SingleMatrixStep.ColumnEliminateStep(column, new Matrix(A)));
            }

        }

        steps.add(new SingleMatrixStep.InvIdentityStep(new Matrix(A)));
        steps.addFirst(new Step.ResultStep(new Matrix(A).getRightMatrix()));

        return steps;
    }

    /**
     * Compute the kernel of the matrix by bringing it into RRE form and observing the (number of)
     * free columns.
     *
     * @param matrix the matrix to find the kernel of.
     * @return a list with every significant step that was needed to achieve the result.
     */
    public static List<Step> kernel(Matrix matrix) {
        Matrix A = new Matrix(matrix);
        LinkedList<Step> steps = new LinkedList<>();

        steps.add(new SingleMatrixStep.FirstStep(matrix, "kernel"));

        List<Matrix> kernelBasis = new ArrayList<>();
        List<Integer> pivotColumns = new ArrayList<>();

        int rank = 0; //the number of non-free columns so far.

        for (int column = 0; column < A.getNrColumns() && rank < A.getNrRows(); column++) {
            int pivotRow = rank;

            for (int row = rank; row < A.getNrRows(); row++) {
                if ( A.getValueAt(column, row).abs() .greater (A.getValueAt(column, pivotRow).abs() )) {
                    pivotRow = row;
                }
            }

            if(A.getValueAt(column, pivotRow).equals(new Rational(0))) {
                Matrix vector = new Matrix(1, A.getNrColumns());
                Matrix columnVector = A.getColumnVector(column);

                for(int i = 0; i < rank; i++) {
                    vector.setValue(0, pivotColumns.get(i), columnVector.getValueAt(0, i));
                }

                vector.setValue(0, column, new Rational(-1));
                kernelBasis.add(vector);

                continue;
            }

            if(rank != pivotRow) {
                A.swapRows(rank, pivotRow);
                steps.add(new SingleMatrixStep.InvRowSwapStep(pivotRow, rank, new Matrix (A)));
                pivotRow = rank;
            }

            Rational pivotValue =  A.getValueAt(column, pivotRow);

            if(! pivotValue.equals(new Rational(1))) {
                A.multiplyRow(pivotRow, pivotValue.inverse());
                steps.add(new SingleMatrixStep.RowDivideStep(pivotRow, pivotValue, new Matrix(A)));
            }

            boolean eliminatedRows = false;
            for(int row = 0; row < A.getNrRows(); row++) {
                if(! A.getValueAt(column, row).equals(new Rational(0)) && row != pivotRow ) {
                    eliminatedRows = true;
                    A.addRow(pivotRow, row, A.getValueAt(column, row).negative());
                }
            }

            if(eliminatedRows) {
                steps.add(new SingleMatrixStep.ColumnEliminateStep(column, new Matrix(A)));
            }

            pivotColumns.add(column);
            rank++;
        }

        steps.add(new SingleMatrixStep.KernelRREFStep(new Matrix(A), kernelBasis.size()));

        steps.addFirst(new SingleMatrixStep.KernelResultStep(kernelBasis));

        return steps;
    }

    public static List<Step> product(Matrix left, Matrix right) {

        final LinkedList<Step> steps = new LinkedList<>();

        if(left.getNrColumns() != right.getNrRows()) {
            steps.add(new TextResultStep.MultErrorStep(left.getNrColumns(), right.getNrRows()));
            return steps;
        }

        if (left.getAugmentedColumnIndex() != -1 || right.getAugmentedColumnIndex() != -1) {
            steps.add(new TextResultStep.AugErrorStep("Matrix multiplication"));
            return steps;
        }

        steps.add(new DoubleMatrixStep(left, right, "product"));

        final Matrix product = new Matrix(right.getNrColumns(), left.getNrRows());

        for(int row = 0;  row < left.getNrRows(); row++) {
            for(int column = 0; column < right.getNrColumns(); column++) {

                final ArrayList<Rational> leftTerms = new ArrayList<>(), rightTerms = new ArrayList<>();
                final Rational result = new Rational(0);

                for(int term = 0; term < left.getNrColumns(); term++) {

                    final Rational leftTerm = left.getValueAt(term, row);
                    final Rational rightTerm = right.getValueAt(column, term);

                    leftTerms.add(new Rational(leftTerm));
                    rightTerms.add(new Rational(rightTerm));

                    result.plusIs(leftTerm.times(rightTerm));
                }

                product.setValue(column, row, new Rational(result));

                steps.add(new SingleMatrixStep.MultCellStep(new Matrix(product), result, leftTerms, rightTerms, row, column));
            }
        }

        steps.addFirst(new Step.MultResultStep(new Matrix(product)));
        return steps;
    }

    public static List<Step> addition(Matrix left, Matrix right) {

        final LinkedList<Step> steps = new LinkedList<>();


        if(left.getNrColumns() != right.getNrColumns() || left.getNrRows() != right.getNrRows()) {
            steps.add(new TextResultStep.AddErrorStep());
            return steps;
        }

        if (left.getAugmentedColumnIndex() != right.getAugmentedColumnIndex()) {
            steps.add(new TextResultStep.AugAddErrorStep());
            return steps;
        }

        steps.add(new DoubleMatrixStep(left, right, "sum"));

        final Matrix sum = new Matrix(right.getNrColumns(), right.getNrRows());

        for(int row = 0; row < right.getNrRows(); row++) {
            for(int column  = 0; column < right.getNrColumns(); column++) {
                final Rational leftValueAt = left.getValueAt(column, row);
                final Rational rightValueAt = right.getValueAt(column, row);
                final Rational result = leftValueAt.plus(rightValueAt);

                sum.setValue(column, row, result);
                steps.add(new SingleMatrixStep.AddCellStep(new Matrix(sum), column, row, leftValueAt, rightValueAt, result));
            }
        }

        steps.addFirst(new Step.MultResultStep(sum));
        return steps;
    }

    public static List<Step> exponentiation(Matrix original, int n) {
        LinkedList<Step> steps = new LinkedList<>();

        if(! original.isSquareMatrix() ) {
            steps.add(new TextResultStep.ExpErrorStep());
            return steps;
        }

        if (original.getAugmentedColumnIndex() != -1) {
            steps.add(new TextResultStep.AugErrorStep("Exponentiation"));
            return steps;
        }

        Matrix matrix = Matrix.identity(original.getNrColumns());
        steps.add(new SingleMatrixStep.FirstStep(original, "power " + n));

        if(n == 0) {
            steps.add(new SingleMatrixStep.ExpStep(matrix, 0));
        }

        for(int exp = 1; exp <= n; exp++) {
            matrix = Matrix.times(matrix, original);
            steps.add(new SingleMatrixStep.ExpStep(matrix, exp));
        }

        steps.addFirst(new Step.ResultStep(matrix));
        return steps;
    }

    public static List<Step> scalarMultiplication(Matrix matrix, Rational scalar) {
        LinkedList<Step> steps = new LinkedList<>();
        Matrix result = new Matrix(matrix.getNrColumns(), matrix.getNrRows());

        steps.add(new SingleMatrixStep.FirstScalarStep(matrix, scalar));

        for(int row = 0; row < matrix.getNrRows(); row++) {
            for(int column = 0; column < matrix.getNrColumns(); column++) {
                final Rational value = matrix.getValueAt(column, row);
                final Rational newValue = value.times(scalar);

                result.setValue(column, row, newValue);

                steps.add(new SingleMatrixStep.ScalarMultCellStep(new Matrix(result), row, column, new Rational(scalar), value, newValue));
            }
        }

        steps.addFirst(new Step.ResultStep(new Matrix(result)));

        return steps;
    }

    public static List<Step> rowEchelonForm(Matrix matrix) {
        Matrix A = new Matrix(matrix);
        final LinkedList<Step> steps = new LinkedList<>();

        steps.add(new SingleMatrixStep.FirstStep(matrix, "row echelon form"));

        if(A.getAugmentedColumnIndex() == -1) {
            // We'll draw the augmented line on the second to right column by default. This is the convention.
            A.setAugmentedColumnIndex(A.getNrColumns() - 1);
        }

        int rank = 0;

        for (int column = 0; column < /*A.getNrColumns()*/ A.getAugmentedColumnIndex() && rank < A.getNrRows(); column++) {
            int pivotRow = rank;

            for (int row = rank; row < A.getNrRows(); row++) {
                if ( A.getValueAt(column, row).abs() .greater (A.getValueAt(column, pivotRow).abs() )) {
                    pivotRow = row;
                }
            }

            if(A.getValueAt(column, pivotRow).equals(new Rational(0))) {
                continue;
            }

            if(rank != pivotRow) {
                A.swapRows(rank, pivotRow);
                steps.add(new SingleMatrixStep.InvRowSwapStep(pivotRow, rank, new Matrix (A)));
                pivotRow = rank;
            }

            Rational pivotValue =  A.getValueAt(column, pivotRow);

            if(! pivotValue.equals(new Rational(1))) {
                A.multiplyRow(pivotRow, pivotValue.inverse());
                steps.add(new SingleMatrixStep.RowDivideStep(pivotRow, pivotValue, new Matrix(A)));
            }

            boolean eliminatedRows = false;
            for(int row = 0; row < A.getNrRows(); row++) {
                if(! A.getValueAt(column, row).equals(new Rational(0)) && row != pivotRow ) {
                    eliminatedRows = true;
                    A.addRow(pivotRow, row, A.getValueAt(column, row).negative());
                }
            }

            if(eliminatedRows) {
                steps.add(new SingleMatrixStep.ColumnEliminateStep(column, new Matrix(A)));
            }

            rank++;
        }

        steps.addFirst(new Step.ResultStep(A));

        return steps;
    }

    public static List<Step> transpose(Matrix original) {
        final LinkedList<Step> steps = new LinkedList<>();
        Matrix transpose = new Matrix(original.getNrRows(), original.getNrColumns());

        if (original.getAugmentedColumnIndex() != -1) {
            steps.add(new TextResultStep.AugErrorStep("Transpose"));
            return steps;
        }

        steps.add(new SingleMatrixStep.FirstStep(original, "transpose"));

        steps.add(new SingleMatrixStep.TransCreateStep(new Matrix(transpose)));

        for (int row = 0; row < transpose.getNrRows(); row++) {
            for (int column = 0; column < transpose.getNrColumns(); column++) {
                Rational r = original.getValueAt(row, column);
                transpose.setValue(column, row, r);
                steps.add(new SingleMatrixStep.TransCellStep(new Matrix(transpose), row, column, r));
            }
        }

        steps.addFirst(new Step.ResultStep(transpose));
        return steps;
    }
}

