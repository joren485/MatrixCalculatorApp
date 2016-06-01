package com.asserttrue.matrixcalculator.model.inverse;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.SingleMatrixStep;

public class InvIdentityStep extends SingleMatrixStep {

    public InvIdentityStep(Matrix matrix) {
        super(matrix);
    }

    protected String getExplanation() {
        return "The left-hand matrix is the identity; The right-hand matrix should thus be the inverse.";
    }
}
