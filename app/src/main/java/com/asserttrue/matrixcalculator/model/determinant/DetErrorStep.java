package com.asserttrue.matrixcalculator.model.determinant;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.SingleMatrixStep;

public class DetErrorStep extends SingleMatrixStep{

    public DetErrorStep(Matrix matrix) {
        super(matrix);
    }

    @Override
    protected String getExplanation() {
        return "The determinant is not defined on non-square matrices, although they are sometimes considered to have determinant 0.";
    }
}
