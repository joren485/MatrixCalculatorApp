package com.asserttrue.matrixcalculator.model.determinant;

import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Rational;
import com.asserttrue.matrixcalculator.model.Step;
import com.asserttrue.matrixcalculator.view.stepViews.DetResultView;

public class DetResultStep implements Step {
    private final Matrix matrix;
    private final Rational scalar;

    public DetResultStep(Matrix matrix, Rational scalar) {
        this.matrix = matrix;
        this.scalar = scalar;
    }

    @Override
    public View toView(Context context) {
        return new DetResultView(context, matrix, scalar);
    }

    @Override
    public void setViewContent(View view) {
        if(!(view instanceof DetResultView)) {
            throw new IllegalArgumentException("Wrong view passed to step");
        }

        DetResultView cardView = (DetResultView) view;
        cardView.setMatrix(matrix);
        cardView.setScalar(scalar);
    }

    @Override
    public int getLayoutType() {
        return DET_RESULT;
    }
}
