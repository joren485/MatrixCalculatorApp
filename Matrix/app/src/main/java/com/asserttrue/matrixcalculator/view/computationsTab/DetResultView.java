package com.asserttrue.matrixcalculator.view.computationsTab;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.computations.Step;
import com.asserttrue.matrixcalculator.model.computations.DetScalarStep;
import com.asserttrue.matrixcalculator.view.MatrixView;

import java.util.Locale;

public class DetResultView extends LinearLayout {
    private final RelativeLayout matrixContainer;
    private final MatrixView matrixView;
    private final TextView scalar;

    public DetResultView(Context context, DetScalarStep step) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cartview_det_result_step, this, true);

        matrixContainer = (RelativeLayout) findViewById(R.id.matrixContainer);
        matrixView = new MatrixView(context, step.getMatrix());
        matrixView.setGravity(Gravity.CENTER);
        matrixContainer.addView(matrixView);
        scalar = (TextView) findViewById(R.id.scalar);
        scalar.setText(step.getScalar().toString());
    }

    public void recycle(Step step) {
        DetScalarStep detStep = (DetScalarStep) step;
        matrixView.setMatrix(detStep.getMatrix());
        scalar.setText(detStep.getScalar().toString());
    }
}
