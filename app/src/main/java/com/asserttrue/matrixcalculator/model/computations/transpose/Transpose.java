package com.asserttrue.matrixcalculator.model.computations.transpose;

import com.asserttrue.matrixcalculator.model.computations.Computation;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.computations.Step;
import com.asserttrue.matrixcalculator.model.computations.SingleMatrixStep;

import java.util.List;

public class Transpose extends Computation {

    private final Matrix matrix;

    public Transpose(Matrix A){
        matrix = A;
        execute();
    }

    protected void compute(){

        Matrix result = new Matrix(matrix.getWidth(), matrix.getHeight());


        // (A^tr)_ij := A_ji
        for (int y = 0; y < matrix.getWidth(); y++)
            for (int x = 0; x < matrix.getHeight(); x++)
                result.setPositionValue(x, y, matrix.getValueAt(y, x));

        steps.add(new SingleMatrixStep("", result));
    }

    public List<Step> getSteps(){
        return steps;
    };


    protected boolean validate(){
        return true;
    }
}
