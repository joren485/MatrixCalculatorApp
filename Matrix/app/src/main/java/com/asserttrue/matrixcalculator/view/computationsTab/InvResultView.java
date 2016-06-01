package com.asserttrue.matrixcalculator.view.computationsTab;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.computations.DetScalarStep;
import com.asserttrue.matrixcalculator.model.computations.SingleMatrixStep;
import com.asserttrue.matrixcalculator.model.computations.Step;
import com.asserttrue.matrixcalculator.view.MatrixView;

import java.util.Locale;

public class InvResultView extends LinearLayout {
    private final RelativeLayout matrixContainer;
    private final MatrixView matrixView;

    public InvResultView(Context context, SingleMatrixStep step) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_inv_result, this, true);

        matrixContainer = (RelativeLayout) findViewById(R.id.matrixContainer);
        matrixView = new MatrixView(context, step.getMatrix());
        matrixView.setGravity(Gravity.CENTER);
        matrixContainer.addView(matrixView);
    }

    public void recycle(Step step) {
        SingleMatrixStep detStep = (SingleMatrixStep) step;
        matrixView.setMatrix(detStep.getMatrix());
    }
}
