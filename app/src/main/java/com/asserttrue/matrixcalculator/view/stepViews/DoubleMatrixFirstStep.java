package com.asserttrue.matrixcalculator.view.stepViews;

import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

import java.util.List;
import java.util.Locale;

/**
 * First step containing two matrices and text explaining the calculation.
 */
public class DoubleMatrixFirstStep implements Step {
    private final Matrix left;
    private final Matrix right;
    private final String computation;

    public DoubleMatrixFirstStep(Matrix left, Matrix right, String computation) {
        this.left = left;
        this.right = right;
        this.computation = computation;
    }

    @Override
    public View toView(Context context) {
        return new DoubleMatrixFirstView(context, left, right, getComputation());
    }

    @Override
    public void setViewContent(View view) {
        if( ! (view instanceof SingleMatrixFirstView) ) {
            throw new IllegalArgumentException("Wrong view passed to step");
        }

        DoubleMatrixFirstView cardView = (DoubleMatrixFirstView) view;

        cardView.setMatrices(left, right);
        cardView.setComputation(getComputation());
    }

    @Override
    public int getLayoutType() {
        return FIRST_DOUBLE_MATRIX;
    }

    protected String getComputation() {
        return computation;
    }
}
