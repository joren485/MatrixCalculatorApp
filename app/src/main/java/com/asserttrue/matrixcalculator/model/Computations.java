package com.asserttrue.matrixcalculator.model;

import com.asserttrue.matrixcalculator.model.determinant.DetColumnElimStep;
import com.asserttrue.matrixcalculator.model.determinant.DetErrorStep;
import com.asserttrue.matrixcalculator.model.determinant.DetResultStep;
import com.asserttrue.matrixcalculator.model.determinant.DetRowDivideStep;
import com.asserttrue.matrixcalculator.model.determinant.DetRowSwapStep;
import com.asserttrue.matrixcalculator.model.determinant.DetUpperTriangularStep;
import com.asserttrue.matrixcalculator.model.determinant.DetZeroStep;
import com.asserttrue.matrixcalculator.model.inverse.InvColumnElimStep;
import com.asserttrue.matrixcalculator.model.inverse.InvIdentityStep;
import com.asserttrue.matrixcalculator.model.inverse.InvErrorStep;
import com.asserttrue.matrixcalculator.model.inverse.InvResultStep;
import com.asserttrue.matrixcalculator.model.inverse.InvRowDivideStep;
import com.asserttrue.matrixcalculator.model.inverse.InvRowSwapStep;
import com.asserttrue.matrixcalculator.model.inverse.InvZeroStep;
import com.asserttrue.matrixcalculator.model.kernel.KernelRREFStep;
import com.asserttrue.matrixcalculator.model.kernel.KernelResultStep;
import com.asserttrue.matrixcalculator.model.multiplication.MultCellStep;
import com.asserttrue.matrixcalculator.model.multiplication.MultResultStep;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Computations {

    public static List<Step> determinant(Matrix matrix) {
        // This computation requires the matrix to be mutated, and so a copy is required.
        final Matrix A = new Matrix(matrix);

        List<Step> steps = new ArrayList<>();

        if (! matrix.isSquareMatrix() ) {
            steps.add(new DetErrorStep(matrix));
            return steps;
        }

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
                steps.add(new DetZeroStep(A, column));
                return steps;
            }

            if(column != pivotRow) {
                A.swapRows(column, pivotRow);
                determinant.timesIs(new Rational(-1));
                steps.add(new DetRowSwapStep(new Matrix(A), new Rational(determinant), column, pivotRow));
                pivotRow = column;

            }

            Rational pivotValue =  A.getValueAt(column, pivotRow);
            if(! pivotValue.equals(new Rational(1))) {
                A.multiplyRow(pivotRow, pivotValue.inverse());
                determinant.timesIs(pivotValue);

                steps.add(new DetRowDivideStep(new Matrix(A), new Rational(determinant), pivotValue, column));
            }

            boolean eliminatedRows = false;
            for(int row = pivotRow + 1; row < A.getNrRows(); row++) {
                if(! A.getValueAt(column, row).equals(new Rational(0))) {
                    eliminatedRows = true;
                    A.addRow(pivotRow, row, A.getValueAt(column, row).negative());
                }
            }
            if(eliminatedRows) {
                steps.add(new DetColumnElimStep(new Matrix(A), new Rational(determinant), column));
            }

            System.out.println(A);
        }

        steps.add(new DetUpperTriangularStep(new Matrix(A), new Rational(determinant)));

        steps.add(0, new DetResultStep(new Matrix(matrix), new Rational(determinant)));

        return steps;
    }

    public static List<Step> inverse(Matrix matrix) {
        Matrix A = new Matrix(matrix, Matrix.identity(matrix.getNrColumns()));

        List<Step> steps = new ArrayList<>();

        if (! matrix.isSquareMatrix() ) {
            steps.add(new InvErrorStep(A));
            return steps;
        }

        for (int column = 0; column < A.getAugmentedColumnIndex(); column++) {
            int pivotRow = column;

            for (int row = pivotRow + 1; row < A.getNrRows(); row++) {
                if ( A.getValueAt(column, row).abs() .greater ( A.getValueAt(column, pivotRow).abs() )) {
                    pivotRow = row;
                }
            }

            if(A.getValueAt(column, pivotRow).equals(new Rational(0))) {
                steps.add(new InvZeroStep(new Matrix(A), column));
                return steps;
            }

            if(column != pivotRow) {
                A.swapRows(column, pivotRow);
                steps.add(new InvRowSwapStep(pivotRow, column, new Matrix (A)));
                pivotRow = column;
            }


            Rational pivotValue =  A.getValueAt(column, pivotRow);

            if(! pivotValue.equals(new Rational(1))) {
                A.multiplyRow(pivotRow, pivotValue.inverse());
                steps.add(new InvRowDivideStep(column, new Rational(pivotValue), new Matrix(A)));
            }

            boolean eliminatedRows = false;
            for(int row = 0; row < A.getNrRows(); row++) {
                if(! A.getValueAt(column, row).equals(new Rational(0)) && row != pivotRow ) {
                    eliminatedRows = true;
                    A.addRow(pivotRow, row, A.getValueAt(column, row).negative());
                }
            }

            if(eliminatedRows) {
                steps.add(new InvColumnElimStep(column, new Matrix(A)));
            }

        }

        steps.add(new InvIdentityStep(new Matrix(A)));
        steps.add(0, new InvResultStep(new Matrix(A)));

        return steps;
    }

    public static List<Step> kernel(Matrix matrix) {
        Matrix A = new Matrix(matrix);
        List<Step> steps = new ArrayList<>();

        if (! matrix.isSquareMatrix() ) {
            steps.add(new InvErrorStep(A));
            return steps;
        }

        for (int column = 0; column < A.getNrColumns(); column++) {
            int pivotRow = column;

            for (int row = pivotRow + 1; row < A.getNrRows(); row++) {
                if ( A.getValueAt(column, row).abs() .greater ( A.getValueAt(column, pivotRow).abs() )) {
                    pivotRow = row;
                }
            }

            if(A.getValueAt(column, pivotRow).equals(new Rational(0))) {
                continue;
            }

            if(column != pivotRow) {
                A.swapRows(column, pivotRow);
                steps.add(new InvRowSwapStep(pivotRow, column, new Matrix (A)));
                pivotRow = column;
            }

            Rational pivotValue =  A.getValueAt(column, pivotRow);

            if(! pivotValue.equals(new Rational(1))) {
                A.multiplyRow(pivotRow, pivotValue.inverse());
                steps.add(new InvRowDivideStep(column, pivotValue, new Matrix(A)));
            }

            boolean eliminatedRows = false;
            for(int row = 0; row < A.getNrRows(); row++) {
                if(! A.getValueAt(column, row).equals(new Rational(0)) && row != pivotRow ) {
                    eliminatedRows = true;
                    A.addRow(pivotRow, row, A.getValueAt(column, row).negative());
                }
            }

            if(eliminatedRows) {
                steps.add(new InvColumnElimStep(column, new Matrix(A)));
            }

        }

        steps.add(new KernelRREFStep(new Matrix(A)));
        List<Matrix> vectors = new ArrayList<>();

        for(int i = 0; i < A.getNrColumns(); i++) {

            if( A.getValueAt(i , i).equals(new Rational(0)) ) {
                final Matrix vector = A.getColumnVector(i);
                vector.timesIs(new Rational(-1));
                vector.setValue(0, i, new Rational(1));
                vectors.add(vector);
            }

        }

        steps.add(0, new KernelResultStep(vectors));

        return steps;
    }

    public static List<Step> product(Matrix left, Matrix right) {

        if(left.getNrColumns() != right.getNrRows()) {
            throw new UnsupportedOperationException("Still todo!");
            //TODO errorstep
        }

        final List<Step> steps = new LinkedList<>();
        final Matrix product = new Matrix(right.getNrColumns(), left.getNrRows());

        for(int row = 0;  row < left.getNrRows(); row++) {
            for(int column = 0; column < right.getNrColumns(); column++) {
                final ArrayList<Rational> leftTerms = new ArrayList<>(), rightTerms = new ArrayList<>();
                final Rational result = new Rational(0);

                for(int term = 0; term < left.getNrColumns(); term++) {
                    final Rational leftTerm = left.getValueAt(column, term);
                    final Rational rightTerm = right.getValueAt(term, row);

                    leftTerms.add(new Rational(leftTerm));
                    rightTerms.add(new Rational(rightTerm));

                    result.plusIs(leftTerm.times(rightTerm));
                }

                product.setValue(column, row, new Rational(result));

                steps.add(new MultCellStep(new Matrix(product), result, leftTerms, rightTerms, row, column));
            }
        }

        steps.add(0, new MultResultStep(new Matrix(product)));
        return steps;
    }
}

