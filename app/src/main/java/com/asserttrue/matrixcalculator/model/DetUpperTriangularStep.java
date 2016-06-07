package com.asserttrue.matrixcalculator.model;

import com.asserttrue.matrixcalculator.model.DetScalarStep;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;
import com.asserttrue.matrixcalculator.model.SingleMatrixStep;

public class DetUpperTriangularStep extends DetScalarStep {

    public DetUpperTriangularStep(Matrix matrix, Rational scalar) {
        super(matrix, scalar);
    }

    @Override
    protected String getExplanation() {
        return "This matrix is upper triangular; it has determinant 1.";
    }
}
