package com.asserttrue.matrixcalculator.model.computations;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;
import java.util.ArrayList;
import java.util.List;

public abstract class Computations {

    public static List<Step> determinant(Matrix matrix) {

        // This computation requires the matrix to be mutated, and so a copy is required.
        final Matrix A = new Matrix(matrix);

        List<Step> steps = new ArrayList<>();

        if (! matrix.isSquareMatrix() ) {
            steps.add(new ErrorStep("The determinant is only defined for square matrices."));
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
                steps.add(new DetScalarStep(new Matrix(A), Step.StepType.SingularStep, new Rational(0), column));
                return steps;
            }

            if(column != pivotRow) {
                A.swapRows(column, pivotRow);
                determinant.timesIs(new Rational(-1));
                steps.add(new DetScalarStep(new Matrix(A), Step.StepType.PivotStep, determinant, pivotRow, column, column));
                pivotRow = column;

            }

            Rational pivotValue =  A.getValueAt(column, pivotRow);
            if(! pivotValue.equals(new Rational(1))) {
                A.multiplyRow(pivotRow, pivotValue.inverse());
                determinant.timesIs(pivotValue);
                steps.add(new DetScalarStep(new Matrix(A), Step.StepType.RowDivideStep, determinant, pivotValue, column));
            }

            boolean eliminatedRows = false;
            for(int row = pivotRow + 1; row < A.getNrRows(); row++) {
                if(! A.getValueAt(column, row).equals(new Rational(0))) {
                    eliminatedRows = true;
                    A.addRow(pivotRow, row, A.getValueAt(column, row).negative());
                }
            }

            if(eliminatedRows) {
                steps.add(new DetScalarStep(new Matrix(A), Step.StepType.RowReduceStep, determinant, column));
            }

            System.out.println(A);
        }

        steps.add(new DetScalarStep(new Matrix(A), Step.StepType.UpperTriangularStep, determinant, 0));

        steps.add(0, new DetScalarStep(new Matrix(matrix), Step.StepType.Result, determinant, 0));

        return steps;
    }

    public static List<Step> inverse(Matrix matrix) {
        Matrix A = new Matrix(matrix, Matrix.identity(matrix.getNrColumns()));

        List<Step> steps = new ArrayList<>();

        if (! matrix.isSquareMatrix() ) {
            steps.add(new ErrorStep(""));
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
                steps.add(new SingleMatrixStep(new Matrix(A), Step.StepType.SingularStep));
                return steps;
            }


            if(column != pivotRow) {
                A.swapRows(column, pivotRow);
                SingleMatrixStep swapStep = new SingleMatrixStep(new Matrix(A), Step.StepType.PivotStep);
                swapStep.setRowFrom(pivotRow);
                swapStep.setRowTo(column);
                steps.add(swapStep);
                pivotRow = column;
            }


            Rational pivotValue =  A.getValueAt(column, pivotRow);

            if(! pivotValue.equals(new Rational(1))) {
                A.multiplyRow(pivotRow, pivotValue.inverse());

                SingleMatrixStep divideStep = new SingleMatrixStep (new Matrix(A), Step.StepType.RowDivideStep);
                divideStep.setMultiplier(new Rational (pivotValue));
                steps.add(divideStep);
            }

            boolean eliminatedRows = false;
            for(int row = 0; row < A.getNrRows(); row++) {
                if(! A.getValueAt(column, row).equals(new Rational(0)) && row != pivotRow ) {
                    eliminatedRows = true;
                    A.addRow(pivotRow, row, A.getValueAt(column, row).negative());
                }
            }

            if(eliminatedRows) {
                SingleMatrixStep reduceStep =  new SingleMatrixStep(new Matrix(A), Step.StepType.RowReduceStep);
                steps.add(reduceStep);
                reduceStep.setCurrentColumn(column);
            }

        }

        steps.add(new SingleMatrixStep(new Matrix(A), Step.StepType.UpperTriangularStep));
        steps.add(0, new SingleMatrixStep(A.getRightMatrix(), Step.StepType.Result));

        return steps;
    }

}

