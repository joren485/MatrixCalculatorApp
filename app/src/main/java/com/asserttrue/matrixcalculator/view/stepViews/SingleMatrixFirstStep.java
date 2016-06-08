package com.asserttrue.matrixcalculator.view.stepViews;

import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;

import java.util.List;
import java.util.Locale;

/**
 * First step containing just one matrix and text explaining the calculation.
 */
public class SingleMatrixFirstStep implements Step {
    private final Matrix matrix;
    private final String computation;

    public SingleMatrixFirstStep(Matrix matrix, String computation) {
        this.matrix = matrix;
        this.computation = computation;
    }

    @Override
    public View toView(Context context) {
        return new SingleMatrixFirstView(context, matrix, getComputation());
    }

    @Override
    public void setViewContent(View view) {
        if( ! (view instanceof SingleMatrixFirstView) ) {
            throw new IllegalArgumentException("Wrong view passed to step");
        }

        SingleMatrixFirstView cardView = (SingleMatrixFirstView) view;

        cardView.setMatrix(matrix);
        cardView.setComputation(getComputation());
    }

    @Override
    public int getLayoutType() {
        return FIRST_SINGLE_MATRIX;
    }

    protected String getComputation() {
        return computation;
    }
}
