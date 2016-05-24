package com.asserttrue.matrixcalculator.model.computations;

import com.asserttrue.matrixcalculator.model.Matrix;

import java.util.ArrayList;
import java.util.List;

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

        for(int column = 0; column < A.getNrColumns(); column++) {
            int pivotRow = column;

            for(int row = pivotRow + 1; row < A.getNrColumns(); row++) {
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
            for(int row = pivotRow + 1; row < A.getNrRows(); row++) {
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

    public static List<Step> inverse(Matrix matrix) {
        Matrix A = new Matrix(matrix, Matrix.identity(matrix.getNrColumns()));

        List<Step> steps = new ArrayList<>();

        for (int column = 0; column < A.getAugmentedColumnIndex(); column++) {
            int pivotRow = column;

            for (int row = pivotRow + 1; row < A.getNrRows(); row++) {
                if (Math.abs(A.getValueAt(column, row)) > Math.abs(A.getValueAt(column, pivotRow))) {
                    pivotRow = row;
                }
            }

            if(A.getValueAt(column, pivotRow) == 0.0) {
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

            double pivotValue =  A.getValueAt(column, pivotRow);
            if(pivotValue != 1.0) {
                A.multiplyRow(pivotRow, 1.0 / pivotValue);
                SingleMatrixStep divideStep = new SingleMatrixStep (new Matrix(A), Step.StepType.RowDivideStep);
                divideStep.setMultiplier(pivotValue);
                steps.add(divideStep);
            }

            boolean eliminatedRows = false;
            for(int row = 0; row < A.getNrRows(); row++) {
                if(A.getValueAt(column, row)  != 0.0 && row != pivotRow ) {
                    eliminatedRows = true;
                    A.addRow(pivotRow, row, - A.getValueAt(column, row));
                }
            }

            if(eliminatedRows) {
                SingleMatrixStep reduceStep =  new SingleMatrixStep(new Matrix(A), Step.StepType.RowReduceStep);
                steps.add(reduceStep);
                reduceStep.setCurrentColumn(column);
            }
        }

        steps.add(new SingleMatrixStep(new Matrix(A), Step.StepType.UpperTriangularStep));
        //TODO make a nice card view for displaying eventual result.
        steps.add(0, new SingleMatrixStep(A.getRightMatrix(), Step.StepType.Result));

        return steps;
    }
}

