package com.asserttrue.matrixcalculator.model.computations;

import com.asserttrue.matrixcalculator.model.computations.Step;
import com.asserttrue.matrixcalculator.model.Matrix;

public class SingleMatrixStep implements Step{

    private final String information;
    private final Matrix matrix;

    public SingleMatrixStep(String details, Matrix A){
        information = details;
        matrix = A;
    }

    public String toString() {
        return matrix.toString();
    }

    @Override
    public int getLayoutType() {
        return Step.TYPE_JUST_MATRIX;
    }

    @Override
    public String getExplanation() {
        return "";
    }
}
