package com.asserttrue.matrixcalculator.view.computationsTab;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asserttrue.matrixcalculator.R;
import com.asserttrue.matrixcalculator.model.computations.Step;
import com.asserttrue.matrixcalculator.model.computations.determinant.DetScalarStep;
import com.asserttrue.matrixcalculator.view.MatrixView;

import java.util.Locale;


public class DetScalarView extends LinearLayout  {
    private final RelativeLayout matrixContainer;
    private final MatrixView matrixView;
    private final TextView scalar;
    private final TextView explanation;

    public DetScalarView(Context context, DetScalarStep step) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cardview_det_scalar_step, this, true);

        matrixContainer = (RelativeLayout) findViewById(R.id.matrixContainer);
        matrixView = new MatrixView(context, step.getMatrix());
        matrixView.setGravity(Gravity.CENTER);
        matrixContainer.addView(matrixView);
        scalar = (TextView) findViewById(R.id.scalar);
        scalar.setText(String.format(Locale.US, "%.2f", step.getScalar()));
        explanation = (TextView) findViewById(R.id.explanation);
        explanation.setText(step.getExplanation());
    }

    public void recycle(Step step) {
        if(step.getClass() != DetScalarStep.class) {
            throw new IllegalArgumentException("Using the wrong step class to fill DetScalarView.");
        }

        DetScalarStep detStep = (DetScalarStep) step;
        matrixView.setMatrix(detStep.getMatrix());
        scalar.setText(String.format(Locale.US, "%.2f", detStep.getScalar()));
        explanation.setText(((DetScalarStep) step).getExplanation());
    }
}
