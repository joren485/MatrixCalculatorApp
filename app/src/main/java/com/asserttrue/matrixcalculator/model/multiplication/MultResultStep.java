package com.asserttrue.matrixcalculator.model.multiplication;

import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Step;
import com.asserttrue.matrixcalculator.view.stepViews.MatrixResultView;

public class MultResultStep implements Step {
    private final Matrix matrix;

    public MultResultStep(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public View toView(Context context) {
        return new MatrixResultView(context, matrix);
    }

    @Override
    public void setViewContent(View view) {
        if( ! (view instanceof MatrixResultView) ) {
            throw new IllegalArgumentException("Wrong view passed to step");
        }

        MatrixResultView cardView = (MatrixResultView) view;
        cardView.setMatrix(matrix);
    }

    @Override
    public int getLayoutType() {
        return MATRIX_RESULT;
    }
}
