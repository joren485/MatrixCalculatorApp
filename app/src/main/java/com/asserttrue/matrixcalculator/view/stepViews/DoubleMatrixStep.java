package com.asserttrue.matrixcalculator.view.stepViews;

import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;

/**
 * Step containing two matrices and text explaining the calculation.
 */
public class DoubleMatrixStep implements Step {
    private final Matrix left;
    private final Matrix right;
    private final String computation;

    public DoubleMatrixStep(Matrix left, Matrix right, String computation) {
        this.left = left;
        this.right = right;
        this.computation = computation;
    }

    @Override
    public View toView(Context context) {
        return new DoubleMatrixView(context, left, right, getComputation());
    }

    @Override
    public void setViewContent(View view) {
        if( ! (view instanceof DoubleMatrixView) ) {
            throw new IllegalArgumentException("Wrong view passed to step");
        }

        DoubleMatrixView cardView = (DoubleMatrixView) view;

        cardView.setMatrices(left, right);
        cardView.setComputation(getComputation());
    }

    @Override
    public int getLayoutType() {
        return DOUBLE_MATRIX;
    }

    protected String getComputation() {
        return computation;
    }
}
