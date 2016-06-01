package com.asserttrue.matrixcalculator.model.kernel;


import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.SingleMatrixStep;

public class KernelRREFStep extends SingleMatrixStep {

    public KernelRREFStep(Matrix matrix) {
        super(matrix);
    }

    @Override
    protected String getExplanation() {
        return "The matrix is now in Reduced Row Echelon Form." +
                "\n\nTake the columns without a zero on the diagonal, multiply them by -1 and the replace that zero with a one." +
                "The resulting columns are precisely the basis vectors of the kernel.";
    }
}
