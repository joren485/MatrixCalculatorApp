package com.asserttrue.matrixcalculator.model.inverse;

import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Step;

public class InvErrorStep implements Step{
    private final Matrix matrix;

    public InvErrorStep(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public View toView(Context context) {
        //TODO make a new cardview showing the user that their input was erroneous (needs square matrix).f
        throw new UnsupportedOperationException();
    }

    @Override
    public void setViewContent(View view) {
        //TODO make a new cardview showing the user that their input was erroneous (needs square matrix).f
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLayoutType() {
        return SINGLE_MATRIX;
    }
}
