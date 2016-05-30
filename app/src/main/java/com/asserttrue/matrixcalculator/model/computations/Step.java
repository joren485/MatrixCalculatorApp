package com.asserttrue.matrixcalculator.model.computations;

public interface Step {
    int TYPE_DET_SCALAR = 0;
    int TYPE_DET_RESULT = 1;
    int TYPE_JUST_MATRIX = 2;
    int TYPE_INV_RESULT = 3;
    int NR_TYPES = 4;

    /**
     * Return an enum int indicating the kind of layout needed to display this step to the user.
     * The values of this int are defined in the StepAdapter class, and are used by instances of
     * of it.
     */
    int getLayoutType();

    String getExplanation();

    enum StepType {
        PivotStep, RowReduceStep, RowDivideStep, UpperTriangularStep, SingularStep, Result
    }
}
