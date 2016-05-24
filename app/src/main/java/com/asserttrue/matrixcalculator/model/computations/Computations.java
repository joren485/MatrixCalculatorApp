package com.asserttrue.matrixcalculator.model.computations;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.computations.determinant.DetScalarStep;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class Computations {

    public static List<Step> determinant(Matrix matrix) {

        // This computation requires the matrix to be mutated, and so a copy is required.
        final Matrix A = new Matrix(matrix);

        List<Step> steps = new ArrayList<>();

        if (! matrix.isSquareMatrix() ) {
            steps.add(new ErrorStep("The determinant is only defined for square matrices."));
        }

        // Determinant of upper-triangular matrix with identity diagonal is 1.
        // We multiply this result by the determinant of the elementary matrices required to reduce A to that form.
        double determinant = 1;

        for(int column = 0; column < A.getWidth(); column++) {
            int pivotRow = column;

            for(int row = pivotRow + 1; row < A.getWidth(); row++) {
                if(Math.abs(A.getValueAt(column, row)) > Math.abs(A.getValueAt(column, pivotRow))) {
                    pivotRow = row;
                }
            }

            if(A.getValueAt(column, pivotRow) == 0.0) {
                steps.add(new DetScalarStep(new Matrix(A), Step.StepType.SingularStep, 0.0, column));
                return steps;
            }

            if(column != pivotRow) {
                A.swapRows(column, pivotRow);
                determinant = -determinant;
                steps.add(new DetScalarStep(new Matrix(A), Step.StepType.PivotStep, determinant, pivotRow, column, column));
                pivotRow = column;

            }

            double pivotValue =  A.getValueAt(column, pivotRow);
            if(pivotValue != 1.0) {
                A.multiplyRow(pivotRow, 1.0 / pivotValue);
                determinant *= pivotValue;
                steps.add(new DetScalarStep(new Matrix(A), Step.StepType.RowDivideStep, determinant, pivotValue, column));
            }

            boolean eliminatedRows = false;
            for(int row = pivotRow + 1; row < A.getHeight(); row++) {
                if(A.getValueAt(column, row)  != 0.0) {
                    eliminatedRows = true;
                    A.addRow(pivotRow, row, - A.getValueAt(column, row));
                }
            }

            if(eliminatedRows) {
                steps.add(new DetScalarStep(new Matrix(A), Step.StepType.RowReduceStep, determinant, column));

            }
        }

        steps.add(new DetScalarStep(new Matrix(A), Step.StepType.UpperTriangularStep, determinant, 0));
        steps.add(0, new DetScalarStep(new Matrix(matrix), Step.StepType.Result, determinant, 0));

        return steps;
    }

    public List<Step> inverse(Matrix matrix) {
        Matrix augmented = new Matrix(matrix, Matrix.identity(matrix.getWidth()));

        Matrix A = new Matrix(augmented);

        for (int column = 0; column < A.getAugmentedColumnIndex(); column++) {
            int pivotRow = column;

            for (int row = pivotRow + 1; row < A.getWidth(); row++) {
                if (Math.abs(A.getValueAt(column, row)) > Math.abs(A.getValueAt(column, pivotRow))) {
                    pivotRow = row;
                }
            }
        }

        return null;
    }
}

