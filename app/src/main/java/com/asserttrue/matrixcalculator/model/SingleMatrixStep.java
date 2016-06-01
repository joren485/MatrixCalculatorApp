package com.asserttrue.matrixcalculator.model;

import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.view.stepViews.SingleMatrixView;

public abstract class SingleMatrixStep implements Step {
    protected final Matrix matrix;

    protected SingleMatrixStep(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public View toView(Context context) {
        return new SingleMatrixView(context, matrix, getExplanation());
    }

    @Override
    public void setViewContent(View view) {
        if( ! (view instanceof SingleMatrixView) ) {
            throw new IllegalArgumentException("Wrong view passed to step");
        }

        SingleMatrixView cardView = (SingleMatrixView) view;

        cardView.setMatrix(matrix);
        cardView.setExplanation(getExplanation());
    }

    @Override
    public int getLayoutType() {
        return SINGLE_MATRIX;
    }

    protected abstract String getExplanation();
}
