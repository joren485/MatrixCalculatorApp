package com.asserttrue.matrixcalculator.model;


import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Step;
import com.asserttrue.matrixcalculator.view.stepViews.DetScalarView;

public abstract class DetScalarStep implements Step {
    protected final Matrix matrix;
    protected final Rational scalar;

    protected DetScalarStep(Matrix matrix, Rational scalar) {
        this.matrix = matrix;
        this.scalar = scalar;
    }

    @Override
    public View toView(Context context) {
        return new DetScalarView(context, matrix, getExplanation(), scalar);
    }

    @Override
    public void setViewContent(View view) {
        if( ! (view instanceof DetScalarView) ) {
            throw new IllegalArgumentException("Wrong view passed to step");
        }

        DetScalarView cardView = (DetScalarView) view;

        cardView.setMatrix(matrix);
        cardView.setScalar(scalar);
        cardView.setExplanation(getExplanation());
    }

    @Override
    public int getLayoutType() {
        return DET_MATRIX_SCALAR;
    }

    protected abstract String getExplanation();
}
