package com.asserttrue.matrixcalculator.model.inverse;

import android.content.Context;
import android.view.View;
import com.asserttrue.matrixcalculator.model.Matrix;
import com.asserttrue.matrixcalculator.model.SingleMatrixStep;
import com.asserttrue.matrixcalculator.model.Step;
import com.asserttrue.matrixcalculator.view.stepViews.SingleMatrixView;

import java.util.Locale;

public class InvRowSwapStep extends SingleMatrixStep {
    private final int newPivot;
    private final int oldPivot;

    public InvRowSwapStep(int newPivot, int oldPivot, Matrix matrix) {
        super(matrix);
        this.newPivot = newPivot;
        this.oldPivot = oldPivot;
    }

    protected String getExplanation() {
        return String.format(Locale.US, "Swap rows %2$d and %1$d, because row %1$d has a larger leading value.", newPivot + 1, oldPivot + 1);
    }
}
