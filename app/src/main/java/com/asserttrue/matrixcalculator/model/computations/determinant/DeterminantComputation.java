package com.asserttrue.matrixcalculator.model.computations.determinant;

import com.asserttrue.matrixcalculator.model.computations.Computation;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.computations.Step;

import java.util.List;

import static com.asserttrue.matrixcalculator.model.computations.determinant.DetScalarStep.StepType;

public class DeterminantComputation extends Computation {

    private final Matrix matrix;

    public DeterminantComputation(Matrix A){
        matrix = A;
        execute();
    }

    public List<Step> getSteps() {
        return steps;
    }

    protected void compute(){
        rowReduction();
    }

    protected boolean validate(){
        return matrix.getHeight() == matrix.getWidth();
    }

    private void rowReduction() {
        // This computation requires the matrix to be mutated, and so a copy is required.
        final Matrix A = new Matrix(matrix);

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
                steps.add(new DetScalarStep(new Matrix(A), StepType.SingularStep, 0.0, column));
                return;
            }

            if(column != pivotRow) {
                A.swapRows(column, pivotRow);
                determinant = -determinant;
                steps.add(new DetScalarStep(new Matrix(A), StepType.PivotStep, determinant, pivotRow, column, column));
                pivotRow = column;

            }

            double pivotValue =  A.getValueAt(column, pivotRow);
            if(pivotValue != 1.0) {
                A.multiplyRow(pivotRow, 1.0 / pivotValue);
                determinant *= pivotValue;
                steps.add(new DetScalarStep(new Matrix(A), StepType.RowDivideStep, determinant, pivotValue, column));
            }

            boolean eliminatedRows = false;
            for(int row = pivotRow + 1; row < A.getHeight(); row++) {
                if(A.getValueAt(column, row)  != 0.0) {
                    eliminatedRows = true;
                    A.addRow(pivotRow, row, - A.getValueAt(column, row));
                }
            }

            if(eliminatedRows) {
                steps.add(new DetScalarStep(new Matrix(A), StepType.RowReduceStep, determinant, column));

            }
        }

        steps.add(new DetScalarStep(new Matrix(A), StepType.UpperTriangularStep, determinant, 0));

        steps.add(0, new DetScalarStep(new Matrix(A), StepType.Result, determinant, 0));
    }


    // Old version:
    /*
    private static double recursiveDeterminant(Matrix A){

        if(A.getWidth() == 1){
            return A.getValueAt(0,0);
        }

        if (A.getWidth() == 2){

            // AD - BC
            return A.getValueAt(0, 0) * A.getValueAt(1,1) - A.getValueAt(1, 0) * A.getValueAt(0, 1);
        }

        double tempResult = 0;

        for(int row = 0; row < A.getHeight(); row++){

            Matrix part = new Matrix(A.getWidth() - 1, A.getHeight() - 1);

            for(int x = 1; x < A.getWidth(); x++){
                for (int y = 0; y < A.getHeight(); y++){

                    if (y < row){
                        part.setPositionValue(x - 1, y, A.getValueAt(x, y));
                    }

                    if (y > row){
                        part.setPositionValue(x - 1, y - 1, A.getValueAt(x, y));
                    }

                }
            }

            tempResult += Math.pow(-1, row) * A.getValueAt(0, row) * recursiveDeterminant(part);

        }

        return tempResult;
    }
    */

}
