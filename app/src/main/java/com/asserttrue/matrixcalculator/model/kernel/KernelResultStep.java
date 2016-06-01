package com.asserttrue.matrixcalculator.model.kernel;


import android.content.Context;
import android.view.View;

import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.Step;
import com.asserttrue.matrixcalculator.view.stepViews.VectorSpanView;

import java.util.List;

public class KernelResultStep implements Step {
    private final List<Matrix> vectors;

    public KernelResultStep(List<Matrix> vectors) {
        this.vectors = vectors;
    }

    @Override
    public View toView(Context context) {
        return new VectorSpanView(context, vectors);
    }

    @Override
    public void setViewContent(View view) {
        return;
    }

    @Override
    public int getLayoutType() {
        return VECTOR_SPAN;
    }
}
